package com.xiaoka.cloud.stock.client.business.core.rongyi.parser;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.client.business.core.HtmlParser;
import com.xiaoka.cloud.stock.client.business.core.rongyi.dto.RongyiStockDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * URL:/HzryERP/sales01.jsp
 *
 * @author zhouze
 * @date 2018/1/18
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RongyiStockParser implements HtmlParser<String> {

	private static final String DIV_ID = "KsdivDbGirdText1";

	private static final String TR_CSS_QUERY = "tr";


	@Override
	public List<RongyiStockDto> parse(String content) {
		Document doc     = Jsoup.parse(content);
		Element  element = doc.getElementById(DIV_ID);
		if (null == element) {
			return Collections.emptyList();
		}
		List<Element> elements = element.select(TR_CSS_QUERY);
		if (CollectionUtils.isEmpty(elements)) {
			return Collections.emptyList();
		}
		List<RongyiStockDto> stocks = Lists.newArrayList();
		elements.forEach(ele -> {
			Elements       eleTds  = ele.select("td");
			RongyiStockDto ryStock = buildRongyiStockBy(eleTds);
			if(null != ryStock) {
				stocks.add(ryStock);
			}
		});
		return stocks;
	}

	private RongyiStockDto buildRongyiStockBy(Elements tds) {
		if (CollectionUtils.isEmpty(tds) || tds.size() <20) {
			return null;
		}
		return new RongyiStockDto.Builder()
				.cPartId(indexText(tds, 0))
				.code1(indexText(tds, 1))
				.name(indexText(tds, 2))
				.carModel(indexText(tds, 3))
				.originPlace(indexText(tds, 4))
				.standard(indexText(tds, 5))
				.unit(indexText(tds, 6))
				.brand(indexText(tds, 7))
				.depot(indexText(tds, 8))
				.balanceCount(indexText(tds, 9))
				.costPrice(indexText(tds, 16))
				.tradePrice(indexText(tds, 17))
				.insurancePrice(indexText(tds, 18))
				.repairFactoryPrice(indexText(tds, 19))
				.repairStationPrice(indexText(tds, 20))
				.build();
	}


	private static String indexText(Elements tds, int idx) {
		try {
			return tds.get(idx).text();
		}catch (IndexOutOfBoundsException ignored){
			return null;
		}
	}
}
