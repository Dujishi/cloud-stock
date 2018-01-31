package com.xiaoka.cloud.stock.service.crawl.core.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.CarModelChooseResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 零零汽 处理
 * https://www.007vin.com/
 *
 * @author zhouze
 * @date 2017/12/11
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class BrandProcessor implements PageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrandProcessor.class);

	private static final List<Integer> RANDOM_SLEEP_TIME = Lists.newArrayList(1500, 1800, 2000, 2200, 2500);
	private static final String        CHARSET_UTF8      = "UTF-8";

	private ZeroAccountEntity entity;

	private Header[] headers;

	public BrandProcessor init(ZeroAccountEntity entity, Header[] _headers) {
		this.entity = entity;
		headers = _headers;
		return this;
	}

	@Override
	public void process(Page page) {
		LOGGER.info("当前时间:{},正在处理请求:{}",format(new Date(), "yyyy-MM-dd HH:mm:ss"),  page.getUrl().get());
		String url = page.getUrl().get();
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)){
			LOGGER.info("请求url:{}未获取到数据,登录失效", url);
			return;
		}
		ZeroResp<List<CarModelChooseResp>> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<CarModelChooseResp>>>() {
		});
		//得到选择的内容
		List<CarModelChooseResp> carModelChooseRespList = zeroResp.getData();
		if (CollectionUtils.isEmpty(carModelChooseRespList)) {
			LOGGER.info("未获取到数据:{}", zeroResp.getMsg());
			return;
		}
		//this.write("," + entity.getIp()+":"+entity.getPort());
	}

	@Override
	public Site getSite() {
		Site site = Site.me().setRetryTimes(1)
				.setSleepTime(1000)
				.setCharset(CHARSET_UTF8);
		if (ArrayUtils.isNotEmpty(headers)) {
			for (Header header : headers) {
				if (null == header || ArrayUtils.isEmpty(header.getElements())) {
					continue;
				}
				HeaderElement[] headerElements = header.getElements();
				if (Objects.equals(header.getName(), "Set-Cookie")) {
					HeaderElement element = headerElements[0];
					site.addCookie(element.getName(), element.getValue());
				}
			}
		}
		return site;
	}

	private String format(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	private void write(String content) {
		try {
			File file = new File("D:\\work\\ip.txt");
			OutputStream outPut = new FileOutputStream(file, true);
			outPut.write(content.getBytes());
			outPut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
