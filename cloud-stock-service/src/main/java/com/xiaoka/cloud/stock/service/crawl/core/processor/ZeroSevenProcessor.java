package com.xiaoka.cloud.stock.service.crawl.core.processor;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.handler.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * 零零汽 处理
 * https://www.007vin.com/
 *
 * @author zhouze
 * @date 2017/12/11
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSevenProcessor implements PageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroSevenProcessor.class);

	private final String        CHARSET_UTF8      = "UTF-8";

	@Resource
	private BrandProcessHandler brandProcessHandler;
	@Resource
	private CarModelChooseProcessHandler carModelChooseProcessHandler;
	@Resource
	private CarModelCheckProcessHandler carModelCheckProcessHandler;
	@Resource
	private CarModelGroupProcessHandler carModelGroupProcessHandler;
	@Resource
	private CarModelSubGroupProcessHandler carModelSubGroupProcessHandler;
	@Resource
	private CarModelPartProcessHandler carModelPartProcessHandler;
	@Resource
	private CarModelSubImageProcessHandler carModelSubImageProcessHandler;
	@Resource
	private PartSearchProcessHandler partSearchProcessHandler;
	@Resource
	private PartCarProcessHandler partCarProcessHandler;
	@Resource
	private PartPriceProcessHandler partPriceProcessHandler;

	private Header[] headers;
	private ZeroAccountEntity account;
	private ZeroCarModelChooseEntity chooseEntity;

	public void init(ZeroAccountEntity entity, Header[] _headers, ZeroCarModelChooseEntity chooseEntity) {
		this.headers = _headers;
		this.account = entity;
		this.chooseEntity = chooseEntity;
	}

	@Override
	public void process(Page page) {
		LOGGER.info("当前帐号:{},当前时间:{},正在处理请求:{}",account.getPhone(), format(new Date(), "yyyy-MM-dd HH:mm:ss"),  page.getUrl().get());
		ProcessHandler processHandler;

		if (page.getUrl().get().contains("/brandbase")){//品牌信息
			processHandler = brandProcessHandler;
		}else if (page.getUrl().get().contains("/ppycars/carsinfos")){//车型信息
			processHandler = carModelCheckProcessHandler;
		}else if (page.getUrl().get().contains("/ppycars/group")){//车型主组
			processHandler =carModelGroupProcessHandler;
		}else if (page.getUrl().get().contains("/ppycars/subgroup")){//车型子组
			processHandler = carModelSubGroupProcessHandler;
		}else if (page.getUrl().get().contains("/ppycars/subimgs")){//零件组图片
			processHandler = carModelSubImageProcessHandler;
		}else if (page.getUrl().get().contains("/ppycars/parts")){//车型配件
			processHandler = carModelPartProcessHandler;
		}else if (page.getUrl().get().contains("/ppys/partcars")){//配件适用车型
			processHandler =partCarProcessHandler;
		}else if (page.getUrl().get().contains("/ppys/partprices")){//配件价格
			processHandler = partPriceProcessHandler;
		}else if (page.getUrl().get().contains("/ppys/partssearchs")){//配件详情
			processHandler = partSearchProcessHandler;
		}else {//车型选择
			processHandler = carModelChooseProcessHandler;
		}
		if (Objects.nonNull(processHandler)){
			try {
				processHandler.handler(page, account.getPhone(), chooseEntity);
			}catch (Exception e){
				LOGGER.error("数据爬取处理异常", e);
			}
		}
	}

	@Override
	public Site getSite() {
		int sleepTime = new Random().nextInt(3000) + 5000;//设置随机的休眠时间5s-8s
		LOGGER.info("请求的随机休眠时间为:{}", sleepTime);
		Site site = Site.me().setRetryTimes(5)
				.setSleepTime(sleepTime)
				.setTimeOut(30000)//007部分接口存在超时的可能(大于默认的超时5S)
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

}
