package client;

import com.alibaba.fastjson.JSON;
import constant.NormalApiConstants;
import input.SupplierSyncInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import output.ResponseObject;
import util.HttpUtil;
import util.SignUtil;

/**
 * 合作方库存同步客户端
 *
 * @author zhouze
 * @date 2017/11/10
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CooperateStockSyncClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(CooperateStockSyncClient.class);

	/**
	 * 初始化配件商库存数据
	 *
	 * @param supplierSyncInput
	 * @return
	 */
	public ResponseObject initData(final SupplierSyncInput supplierSyncInput) throws Exception {
		String url = "/openApi/sync/initData";

		//当前时间戳
		final Long vt = System.currentTimeMillis();

		//拼装并生成签名
		final String sign = assembleSignStr(supplierSyncInput, url, vt);

		//组装请求地址并生成请求内容
		final String requestUrl  = buildRequestUrl(NormalApiConstants.HOST.concat(url), vt, sign);
		final String requestBody = JSON.toJSONString(supplierSyncInput);

		try {
			String responseText = HttpUtil.doPost(requestUrl, requestBody);
			LOGGER.info("初始化配件商库存数据,请求地址{},请求体为:{},返回:{}", requestUrl, requestBody, responseText);
			return JSON.parseObject(responseText, ResponseObject.class);
		} catch (Exception e) {
			LOGGER.error("初始化配件商库存数据,HTTP错误,POST,url:{},请求体:{}", requestUrl, requestBody, e);
			throw e;
		}
	}

	/**
	 * 增量调整配件商库存数据
	 *
	 * @param supplierSyncInput
	 * @return
	 */
	public ResponseObject bulkData(final SupplierSyncInput supplierSyncInput) throws Exception {
		String url = "/openApi/sync/bulkData";

		final Long vt = System.currentTimeMillis();

		//拼装并生成签名
		final String sign = assembleSignStr(supplierSyncInput, url, vt);

		//组装请求地址并生成请求内容
		final String requestUri  = buildRequestUrl(NormalApiConstants.HOST.concat(url), vt, sign);
		final String requestBody = JSON.toJSONString(supplierSyncInput);

		try {
			String responseText = HttpUtil.doPost(requestUri, requestBody);
			LOGGER.info("增量调整配件商库存数据,请求地址{},请求体为:{},返回:{}", url, requestBody, responseText);
			return JSON.parseObject(responseText, ResponseObject.class);
		} catch (Exception e) {
			LOGGER.error("增量调整配件商库存数据,HTTP错误,POST,url:{},请求体:{}", url, requestBody, e);
			throw e;
		}
	}

	/**
	 * 组装并生成URL
	 *
	 * @param url
	 * @param vt
	 * @param sign
	 * @return
	 */
	private String buildRequestUrl(String url, Long vt, String sign) {
		return url
				.concat("?").concat("appId=").concat(NormalApiConstants.APP_ID)
				.concat("&").concat("vt=").concat(String.valueOf(vt))
				.concat("&").concat("sign=").concat(sign);
	}

	/**
	 * 组装并生成签名
	 *
	 * @param data
	 * @param url
	 * @param vt
	 * @return
	 */
	private <T> String assembleSignStr(T data, String url, Long vt) {
		String unSign = url.concat(NormalApiConstants.APP_ID)
				.concat(NormalApiConstants.SECURITY_KEY)
				.concat(String.valueOf(vt))
				.concat(JSON.toJSONString(data));
		return SignUtil.generateSign(unSign);
	}


}
