package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.service.core.env.EnvEnum;
import com.xiaoka.cloud.stock.service.core.util.DateUtils;
import com.xiaoka.cloud.stock.service.crawl.core.log.constant.EpcLogTypeEnum;
import com.xiaoka.cloud.stock.service.epc.EpcAggregateService;
import com.xiaoka.freework.cache.annotation.ServiceCache;
import com.xiaoka.freework.container.context.ContainerContext;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Do something
 *
 * @author gancao 2018/1/15 15:45
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class EpcAggregateServiceImpl implements EpcAggregateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EpcAggregateServiceImpl.class);
	private static final String BASE_TYPE = "crawl";
	private static final Joiner JOINER = Joiner.on(".").skipNulls();
	private static final String AGG_SUPER_AMOUNT = "agg_super_amount";
	private static final String AGG_ZERO_AMOUNT = "agg_zero_amount";
	private static final String AGG_ZERO_URL_AMOUNT = "agg_zero_url_amount";
	//SuperEpc应用内统计缓存的时间为30s
	private static final int CACHE_TIMER = 30;

	@Resource
	private Client client;

	@Override
	@ServiceCache(expire = CACHE_TIMER)
	public long aggregateSuperEpcByToday() {
		long amount = 0;
		try {
			Map<String, Long> result = this.getSuperEpcTodayAggregationResult();
			if (Objects.nonNull(result) && !result.isEmpty()) {
				for (String key : result.keySet()) {
					amount = amount + result.get(key);
				}
			}
		}catch (Exception e){
			LOGGER.error("统计正时今日接口调用量出错", e);
		}
		return amount;
	}

	@Override
	public Map<String, Long> aggregateDetailSuperEpcByToday() {
		return this.getSuperEpcTodayAggregationResult();
	}

	@Override
	public Map<Integer, Long> aggregateZeroEpcByToday() {
		return this.getZeroEpcTodayAggregationResult();
	}

	private Map<String, Long> getSuperEpcTodayAggregationResult() {
		SearchRequestBuilder requestBuilder = this.getSearchRequestBuilder();
		if (Objects.isNull(requestBuilder)) {
			return null;
		}
		BoolQueryBuilder queryBuilder = boolQuery();
		queryBuilder.must(termQuery("requestType", EpcLogTypeEnum.正时.getType()))
		            .must(rangeQuery("logTime").gte(DateUtils.parse(DateUtils.getStartTimeOfToday(), DateUtils.DATE_FORMAT)));
		requestBuilder.addAggregation(this.getSuperAggregationBuilder()).setQuery(queryBuilder);

		LOGGER.info("统计正时本周的接口调用量语句:{}", requestBuilder.toString());
		SearchResponse searchResponse = requestBuilder.execute().actionGet();
		Aggregations aggregations = searchResponse.getAggregations();

		Terms terms = aggregations.get(AGG_SUPER_AMOUNT);
		return this.resolveSuperAggregation(terms);
	}

	private Map<Integer, Long> getZeroEpcTodayAggregationResult() {
		SearchRequestBuilder requestBuilder = this.getSearchRequestBuilder();
		if (Objects.isNull(requestBuilder)) {
			return null;
		}
		BoolQueryBuilder queryBuilder = boolQuery();
		queryBuilder.must(termQuery("requestType", EpcLogTypeEnum.零零汽.getType()))
		            .must(rangeQuery("logTime").gte(DateUtils.parse(DateUtils.getStartTimeOfToday(), DateUtils.DATE_FORMAT)));
		requestBuilder.addAggregation(this.getZeroAggregationBuilder()).setQuery(queryBuilder);

		LOGGER.info("统计零零汽本周的接口调用量语句:{}", requestBuilder.toString());
		SearchResponse searchResponse = requestBuilder.execute().actionGet();
		Aggregations aggregations = searchResponse.getAggregations();

		Terms terms = aggregations.get(AGG_ZERO_AMOUNT);
		return this.resolveZeroAggregation(terms);
	}

	private AggregationBuilder getSuperAggregationBuilder() {
		//按日统计数量
		return AggregationBuilders.terms(AGG_SUPER_AMOUNT).field("requestUrl.keyword").size(50);
	}

	private AggregationBuilder getZeroAggregationBuilder() {
		//统计供应商下的各个请求数量
		AggregationBuilder aggregationBuilder = AggregationBuilders.terms(AGG_ZERO_AMOUNT).field("supplierId").size(50);
		/*AggregationBuilder subAggregationBuilder = AggregationBuilders.terms(AGG_ZERO_URL_AMOUNT).field("requestUrl").size(50);
		aggregationBuilder.subAggregation(subAggregationBuilder);*/
		return aggregationBuilder;
	}

	private SearchRequestBuilder getSearchRequestBuilder() {
		Date date = new Date();
		String env = ContainerContext.get().getAppEnv();
		String index;
		String type;
		if (StringUtils.isNotBlank(env)) {
			if (env.equals(EnvEnum.PRE.getEnv()) || env.equals(EnvEnum.PROD.getEnv())) {
				//生产环境索引每周重建
				type = EnvEnum.PROD.getEnv().concat(".").concat(BASE_TYPE);
				index = type.concat("-").concat(this.getProIndex());
			} else {
				//测试环境索引每日重建
				type = EnvEnum.INT.getEnv().concat(".").concat(BASE_TYPE);
				index = type.concat("-").concat(DateUtils.parse(date, DateUtils.DATE_FORMAT_NEW));
			}
			if (StringUtils.isNotBlank(index)) {
				return client.prepareSearch(index).setTypes(type);
			}
		}
		return null;
	}

	private Map<String, Long> resolveSuperAggregation(Terms terms) {
		if (Objects.isNull(terms)) {
			return null;
		}
		Map<String, Long> result = Maps.newHashMap();
		terms.getBuckets().forEach(bucket -> {
			String url = bucket.getKeyAsString();
			if (url.contains("adaptation/getPicInfoForAssembly")) {
				//正时定制接口，一次相当与两次
				result.put(bucket.getKeyAsString(), bucket.getDocCount() * 2);
			} else {
				result.put(bucket.getKeyAsString(), bucket.getDocCount());
			}
		});
		return result;
	}

	private Map<Integer, Long> resolveZeroAggregation(Terms terms) {
		if (Objects.isNull(terms)) {
			return null;
		}
		Map<Integer, Long> result = Maps.newHashMap();
		terms.getBuckets().forEach(bucket -> result.put(bucket.getKeyAsNumber().intValue(), bucket.getDocCount()));
		return result;
	}

	private String getProIndex() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		List<String> data = Lists.newArrayList();
		data.add(year + "");
		data.add(month > 9 ? month + "" : "0" + month);
		data.add(week > 9 ? week + "" : "0" + week);
		return JOINER.join(data);
	}

}
