package com.xiaoka.cloud.stock.service.wrapper.zero;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.manger.ZeroAccountManagerService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.wrapper.zero.resp.*;
import com.xiaoka.freework.utils.encrypt.Base64;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2018/1/8 11:55
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroEPCWrapperService {

	private static final Logger logger = LoggerFactory.getLogger(ZeroEPCWrapperService.class);

	@Resource
	private ZeroAccountManagerService zeroAccountManagerService;

	/**
	 * 车架号搜索
	 *
	 * @param vin
	 * @return
	 */
	public VinSearchZeroResp getVinSearchZeroResp(String vin,  CloudSupplierUserDto userDto) {
		new Thread(() -> zeroAccountManagerService.execute("https://www.007vin.com/parse/vins?vin=" + vin, false, false, userDto)).run();//同时启一个线程调/parse/vins接口
		String result = zeroAccountManagerService.execute("https://www.007vin.com/ppyvin/searchvins?vin=" + vin + "&brands=all", true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			VinSearchZeroResp vinSearchZeroResp = Jackson.base().readValue(result, new TypeReference<VinSearchZeroResp>() {
			});
			if (Objects.nonNull(vinSearchZeroResp)) {
				return vinSearchZeroResp;
			}
		}
		return null;
	}

	/**
	 * 车型主组信息搜索
	 *
	 * @param vin
	 * @param brand
	 * @return
	 */
	public List<ZeroGroupResp> getZeroGroupResp(String vin, String brand,  CloudSupplierUserDto userDto) {
		//用户身份
		String userResult = zeroAccountManagerService.execute("https://www.007vin.com/userhabits", true, true, userDto);
		if (StringUtils.isBlank(userResult)) {
			return null;
		}
		BaseZeroResp<ZeroUserHabitsResp> zeroResp = Jackson.base().readValue(userResult, new TypeReference<BaseZeroResp<ZeroUserHabitsResp>>() {
		});
		if (Objects.isNull(zeroResp) || !"1".equals(zeroResp.getData().getVinFilter())) {
			//不是付费用户
			return null;
		}
		String result = zeroAccountManagerService.execute("https://www.007vin.com/ppyvin/vingroup?code=" + brand + "&vin=" + vin, true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			BaseZeroResp<List<ZeroGroupResp>> baseZeroResp = Jackson.base().readValue(result, new TypeReference<BaseZeroResp<List<ZeroGroupResp>>>() {
			});
			if (Objects.nonNull(baseZeroResp)) {
				return baseZeroResp.getData();
			}
		}
		return null;
	}

	/**
	 * 车型零件组
	 *
	 * @param uri
	 * @return
	 */
	public List<ZeroSubGroupResp> getZeroSubGroupResp(String uri,  CloudSupplierUserDto userDto) {
		uri = this.decodeUrl(uri);
		if (StringUtils.isBlank(uri)){
			return null;
		}
		uri = new String(Base64.decode(uri));
		String result = zeroAccountManagerService.execute("https://www.007vin.com/ppyvin/subgroup?" + uri + "&filter=1", true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			BaseZeroResp<List<ZeroSubGroupResp>> baseZeroResp = Jackson.base()
			                                                           .readValue(result, new TypeReference<BaseZeroResp<List<ZeroSubGroupResp>>>() {
			                                                           });
			if (Objects.nonNull(baseZeroResp)) {
				return baseZeroResp.getData();
			}
		}
		return null;
	}

	/**
	 * 打点图片详情
	 *
	 * @param uri
	 * @return
	 */
	public ZeroSubGroupImageResp getZeroSubGroupImageResp(String uri,  CloudSupplierUserDto userDto) {
		uri = this.decodeUrl(uri);
		if (StringUtils.isBlank(uri)){
			return null;
		}
		uri = new String(Base64.decode(uri));
		String result = zeroAccountManagerService.execute("https://www.007vin.com/ppycars/subimgs?" + uri, true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			BaseZeroResp<ZeroSubGroupImageResp> baseZeroResp = Jackson.base()
			                                                          .readValue(result, new TypeReference<BaseZeroResp<ZeroSubGroupImageResp>>() {
			                                                          });
			if (Objects.nonNull(baseZeroResp)) {
				return baseZeroResp.getData();
			}
		}
		return null;
	}

	/**
	 * 零件组配件列表
	 *
	 * @param uri
	 * @return
	 */
	public ZeroSubGroupPartResp getZeroSubGroupPartResp(String uri,  CloudSupplierUserDto userDto) {
		uri = this.decodeUrl(uri);
		if (StringUtils.isBlank(uri)){
			return null;
		}
		uri = new String(Base64.decode(uri));
		String result = zeroAccountManagerService.execute("https://www.007vin.com/ppyvin/parts?" + uri, true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			ZeroSubGroupPartResp zeroSubGroupPartResp = Jackson.base().readValue(result, new TypeReference<ZeroSubGroupPartResp>() {
			});
			if (Objects.nonNull(zeroSubGroupPartResp)) {
				return zeroSubGroupPartResp;
			}
		}
		return null;
	}

	/**
	 * 配件详情
	 *
	 * @param brand
	 * @param partCode
	 * @return
	 */
	public ZeroPartSearchResp getZeroPartSearchResp(String brand, String partCode,  CloudSupplierUserDto userDto) {
		String url = String.format("https://www.007vin.com/ppys/partssearchs?brand=%s&part=%s", brand, partCode);
		String result = zeroAccountManagerService.execute(url, true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			ZeroPartSearchResp zeroPartSearchResp = Jackson.base().readValue(result, new TypeReference<ZeroPartSearchResp>() {
			});
			if (Objects.nonNull(zeroPartSearchResp)) {
				return zeroPartSearchResp;
			}
		}
		return null;
	}

	/**
	 * 配件价格
	 *
	 * @param brand
	 * @param partCode
	 * @return
	 */
	public ZeroPartPriceResp getZeroPartPriceResp(String brand, String partCode,  CloudSupplierUserDto userDto) {
		String url = String.format("https://www.007vin.com/ppys/partprices?brand=%s&part=%s", brand, partCode);
		String result = zeroAccountManagerService.execute(url, true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			BaseZeroResp<ZeroPartPriceResp> baseZeroResp = Jackson.base().readValue(result, new TypeReference<BaseZeroResp<ZeroPartPriceResp>>() {
			});
			if (Objects.nonNull(baseZeroResp)) {
				return baseZeroResp.getData();
			}
		}
		return null;
	}

	/**
	 * 配件适用车型
	 *
	 * @param brand
	 * @param partCode
	 * @return
	 */
	public List<List<ZeroPartCarResp>> getZeroPartCarResp(String brand, String partCode,  CloudSupplierUserDto userDto) {
		String url = String.format("https://www.007vin.com/ppys/partcars?brand=%s&part=%s", brand, partCode);
		String result = zeroAccountManagerService.execute(url, true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			BaseZeroResp<List<List<ZeroPartCarResp>>> baseZeroResp = Jackson.base().readValue(result,
					new TypeReference<BaseZeroResp<List<List<ZeroPartCarResp>>>>() {
					});
			if (Objects.nonNull(baseZeroResp)) {
				return baseZeroResp.getData();
			}
		}
		return null;
	}

	public BaseZeroResp<List<List<PartSearchResp>>> getBrandName(String partCode,  CloudSupplierUserDto userDto) {
		String url = String.format("https://www.007vin.com/parts/search?parts=%s&brand=", partCode);
		String result = zeroAccountManagerService.execute(url, true, true, userDto);
		if (StringUtils.isNotBlank(result)) {
			BaseZeroResp<List<List<PartSearchResp>>> baseZeroResp = Jackson.base().readValue(result,
					new TypeReference<BaseZeroResp<List<List<PartSearchResp>>>>() {
					});
			if (Objects.nonNull(baseZeroResp)) {
				return baseZeroResp;
			}
		}
		return null;
	}

	private String decodeUrl(String uri){
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("uri:{}decode error", uri);
			return null;
		}
		return uri;
	}

}
