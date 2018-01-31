package com.xiaoka.cloud.stock.service.crawl.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.service.core.util.HttpUtil;
import com.xiaoka.cloud.stock.service.crawl.core.processor.ZeroSevenProcessor;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.BaseZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto.AccountMsgDto;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 单虚拟机下的单例
 * todo 改成集群内Headers一致
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSevenLoginUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroSevenProcessor.class);

	private static final String ACCOUNT = "18768192703";
	private static final String PASS = "192703";

	private static volatile ZeroSevenLoginUtil instance;

	public static ZeroSevenLoginUtil singleInstance() {
		if (null == instance) {
			synchronized (ZeroSevenLoginUtil.class) {
				if (null == instance) {
					instance = new ZeroSevenLoginUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 获取登陆的头信息
	 *
	 * @param needLogin
	 * @return
	 */
	public Header[] getHeaders(boolean needLogin, ZeroAccountEntity entity) {
		int retryTimes = 0;
		do {
			try {
				return doLogin(entity);
			}catch (IOException e) {
				LOGGER.error("登录失败",e);
				LOGGER.error("帐号:{}登录零零汽发生错误,开始重新第{}次", entity.getPhone(), retryTimes + 1);
			}catch (Exception e){
				LOGGER.error("登录失败",e);
				return null;
			}
		}while (++retryTimes < 1);//登录重试1次
		return null;
	}

	/**
	 * 登录零零汽
	 * 不再使用WebDriver，使用Http接口直接操作
	 * url:https://www.007vin.com/login?username=15381108998&password=108998
	 */
	private synchronized Header[] doLogin(ZeroAccountEntity entity) throws IOException {
		String url = String.format("https://www.007vin.com/login?username=%s&password=%s", entity.getPhone(), entity.getPassword());
		Map<String, String> headersMap = Maps.newHashMap();
		headersMap.put("Origin", "https://www.007vin.com");
		headersMap.put("Host", "www.007vin.com");
		headersMap.put("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
		headersMap.put("Cookie", "language=\"languageCode=zh-CN\"");
		headersMap.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		headersMap.put("Accept-Encoding", "gzip, deflate, br");
		headersMap.put("X-Requested-With", "XMLHttpRequest");
		CloseableHttpResponse response = HttpUtil.doProxyPostResponse(url, null, headersMap, entity.getIp(), entity.getPort());
		if (Objects.nonNull(response)){
			Header[] headers = response.getAllHeaders();
			return headers;
		}
		return null;
	}

	/**
	 * 判断帐号是否有效
	 * @param accountMsgDto
	 * @return
	 */
	public static boolean checkLogin(AccountMsgDto accountMsgDto) {
		try {
			String result = HttpUtil.doRespString("https://www.007vin.com/brandbase", accountMsgDto, null);
			if (StringUtils.isNotBlank(result)) {
				BaseZeroResp zeroResp = Jackson.base().readValue(result, new TypeReference<BaseZeroResp>() {
				});
				if (Objects.nonNull(zeroResp) && Objects.equals(1, zeroResp.getCode())) {
					//帐号信息有效
					return true;
				}else {
					LOGGER.info("帐号校验结果:{}", Objects.nonNull(zeroResp) ? zeroResp.getMsg() : "失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean checkValid(AccountMsgDto accountMsgDto){
		try {
			String result = HttpUtil.doRespString("https://www.007vin.com/brandbase", accountMsgDto, null);
			if (StringUtils.isNotBlank(result)){//返回有结果即成功
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		ZeroAccountEntity entity = new ZeroAccountEntity();
		entity.setPhone("15088715024");
		entity.setPassword("715024");
		entity.setIp("121.232.147.44");
		entity.setPort(9000);
		try {
			new ZeroSevenLoginUtil().doLogin(entity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取登录cookie的Map，没有则返回空
	 *
	 * @return
	 */
	public Map<String, String> getCookiesMap() {
		Map<String, String> cookiesMap = Maps.newHashMap();
		return cookiesMap;
	}

	private ZeroSevenLoginUtil() {
	}

	public static Map<String, String> getCookiesMap(Header[] headers) {
		Map<String, String> cookiesMap = Maps.newHashMap();
		if (ArrayUtils.isNotEmpty(headers)) {
			for (Header header : headers) {
				if (null == header || ArrayUtils.isEmpty(header.getElements())) {
					continue;
				}
				HeaderElement[] headerElements = header.getElements();
				if (Objects.equals(header.getName(), "Set-Cookie")) {
					HeaderElement element = headerElements[0];
					cookiesMap.put(element.getName(), element.getValue());
				}
			}
		}
		return cookiesMap;
	}

}
