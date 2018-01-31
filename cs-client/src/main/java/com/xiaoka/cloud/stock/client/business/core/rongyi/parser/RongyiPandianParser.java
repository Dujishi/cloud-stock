package com.xiaoka.cloud.stock.client.business.core.rongyi.parser;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.client.business.core.HtmlParser;
import com.xiaoka.cloud.stock.client.business.core.rongyi.dto.RongyiPandianDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 配件商荣意的库存接口解析器
 * url：http://60.191.43.19:8090/HzryERP/nStkMnt01B.jsp?
 * KsPid=06020432329774237835
 * &KsCustom=
 * &KsAppVerson=2016%2F
 * &usergrp=06
 * &wh_loc=&bin=&row=&wt=&stk=&vnd_id=&item1_code=&ref_type=&ref_date1=&ref_date2=&item_brand=&spec=&item2_code=&item_code=
 * &src_id=3W0805155AH
 * &item_desc=&xtype=&prod=
 * &KsTime=2018010134443
 * &KsSetSrcMode=Replace
 *
 * @author zhouze
 * @date 2018/1/9
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class RongyiPandianParser implements HtmlParser<String> {

	private static final String DIV_ID = "KsdivDbPageGridText1";

	private static final String TR_CSS_QUERY = "tbody tr";

	@Override
	public List<RongyiPandianDto> parse(String content) {
		Document doc     = Jsoup.parse(content);
		Element  element = doc.getElementById(DIV_ID);
		if (null == element) {
			return Collections.emptyList();
		}
		List<Element> elements = element.select(TR_CSS_QUERY);
		if (CollectionUtils.isEmpty(elements)) {
			return Collections.emptyList();
		}
		List<RongyiPandianDto> stocks = Lists.newArrayList();
		elements.forEach(ele -> {
			Elements         eleTds  = ele.select("td");
			RongyiPandianDto ryStock = buildRongyiStockBy(eleTds);
			stocks.add(ryStock);
		});
		return stocks;
	}

	public static RongyiPandianDto buildRongyiStockBy(Elements tds) {
		if (CollectionUtils.isEmpty(tds)) {
			return null;
		}
		return new RongyiPandianDto.Builder()
				.cPartId(indexText(tds, 1))
				.cOeNo(indexText(tds, 2))
				.balanceCount(indexText(tds, 11))
				.brand(indexText(tds, 8))
				.carModel(indexText(tds, 4))
				.code1(indexText(tds, 17))
				.code2(indexText(tds, 18))
				.depot(indexText(tds, 9))
				.name(indexText(tds, 3))
				.originPlace(indexText(tds, 5))
				.price(indexText(tds, 12))
				.standard(indexText(tds, 6))
				.unit(indexText(tds, 7))
				.build();
	}

	private static String indexText(Elements tds, int idx) {
		return tds.get(idx).text();
	}

}
