package com.xiaoka.cloud.stock.soa.impl.crawl;

import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataProcessService;
import com.xiaoka.cloud.stock.soa.api.crawl.ZeroSevenDataGatherSoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhouze
 * @date 2017/12/20
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("zeroSevenDataGatherSoaService")
public class ZeroSevenDataGatherSoaServiceImpl implements ZeroSevenDataGatherSoaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroSevenDataGatherSoaServiceImpl.class);

	@Resource
	ZeroDataProcessService zeroDataProcessService;

	@Override
	public void processAllImgUploadUpCloud() {
		LOGGER.info("上传图片至又拍云,准备处理...");
		zeroDataProcessService.uploadAllImgToUpyun();
	}
}
