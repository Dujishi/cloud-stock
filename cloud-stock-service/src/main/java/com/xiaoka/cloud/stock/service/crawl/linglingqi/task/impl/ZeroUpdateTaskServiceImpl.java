package com.xiaoka.cloud.stock.service.crawl.linglingqi.task.impl;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarGroupChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.core.processor.ZeroSevenProcessor;
import com.xiaoka.cloud.stock.service.crawl.core.util.ZeroSevenLoginUtil;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.ZeroUpdateTaskService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.util.IPGetUtil;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.REQUEST_URL;

/**
 * Do something
 *
 * @author gancao 2018/1/2 14:22
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
//@EnableScheduling
public class ZeroUpdateTaskServiceImpl implements ZeroUpdateTaskService {

	private static final Logger logger = LoggerFactory.getLogger(ZeroUpdateTaskServiceImpl.class);

	@Resource
	private ZeroChooseService zeroChooseService;
	@Resource
	private ZeroSevenProcessor zeroSevenProcessor;

	//@Scheduled(fixedRate = 1000 * 60 * 60)   //每1小时执行一次
	@Override
	public void update() {

		String ip = IPGetUtil.getV4IP();
		if (StringUtils.isBlank(ip)) {
			return;
		}
		ZeroAccountEntity accountEntity = zeroChooseService.getZeroAccountEntityByIp(ip);
		if (Objects.isNull(accountEntity)) {
			logger.info("帐号已被分配完毕");
			return;
		}
		List<ZeroCarGroupChooseEntity> groupChooseEntityList = this.getNoSuccessGroup();
		if (CollectionUtils.isNotEmpty(groupChooseEntityList)){
			Header[] headers = ZeroSevenLoginUtil.singleInstance().getHeaders(false, accountEntity);
			if (ArrayUtils.isEmpty(headers) || (ArrayUtils.isNotEmpty(headers))){
				//帐号付费信息是否有效,帐号无效结束循环
				logger.error("当前帐号:{}登录失败或尚未付费", accountEntity.getPhone());
				return;
			}
			Map<Integer, List<ZeroCarGroupChooseEntity>> map = groupChooseEntityList.stream().collect(Collectors.groupingBy(ZeroCarGroupChooseEntity::getcId));
			map.forEach((k, v) -> {
				ZeroCarModelChooseEntity chooseEntity = zeroChooseService.getZeroCarModelChooseEntityByCId(k);
				List<String> urlList = v.stream().map(p -> REQUEST_URL + "/ppycars/subgroup?" + p.getUri()).collect(Collectors.toList());
				executeCarModelGroupTask(accountEntity, chooseEntity, headers, urlList);
			});
		}

	}

	private void executeCarModelGroupTask(ZeroAccountEntity entity, ZeroCarModelChooseEntity chooseEntity, Header[] headers, List<String> urlList) {
		if (Objects.nonNull(headers)) {
			logger.info("登录成功开始执行数据抓取:抓取帐号{}", Jackson.mobile().writeValueAsString(entity));
			logger.info("开始爬取车型:{}", Jackson.mobile().writeValueAsString(chooseEntity));
			zeroSevenProcessor.init(entity, headers, chooseEntity);
			HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
			if (StringUtils.isNotBlank(entity.getIp()) && Objects.nonNull(entity.getPort())) {
				httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(entity.getIp(), entity.getPort())));
			}
			Spider spider = Spider.create(zeroSevenProcessor);
			spider.setDownloader(httpClientDownloader);
			urlList.forEach(spider :: addUrl);
			spider.thread(1);
			spider.run();
		} else {
			logger.error("帐号:{},请求header头为空", Jackson.mobile().writeValueAsString(entity));
		}
	}

	private List<ZeroCarGroupChooseEntity> getNoSuccessGroup(){
		ZeroCarGroupChooseEntity param = new ZeroCarGroupChooseEntity();
		param.setSuccessStatus(0);
		param.setIsValid(1);
		return zeroChooseService.getZeroCarGroupChooseEntityList(param);
	}
}
