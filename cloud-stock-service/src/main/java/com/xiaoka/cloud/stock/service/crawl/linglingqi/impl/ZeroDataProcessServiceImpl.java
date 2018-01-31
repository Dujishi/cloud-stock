package com.xiaoka.cloud.stock.service.crawl.linglingqi.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.dto.ReplaceImgUrlDto;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupImgEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroSubGroupImgService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataProcessService;
import com.xiaoka.ddyc.tool.storage.StorageService4UpYun;
import com.xiaoka.freework.help.page.PageList;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.http.HttpExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author zhouze
 * @date 2017/12/20
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroDataProcessServiceImpl implements ZeroDataProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroDataProcessServiceImpl.class);

	@Resource
	private ZeroSubGroupImgService zeroSubGroupImgService;
	@Resource
	private StorageService4UpYun   xkStorageService;

	@Override
	public void uploadAllImgToUpyun() {
		int page     = 1;
		int pageSize = 50;
		do {
			//分页查询
			PageList pageList = new PageList();
			pageList.setPage(page);
			pageList.setPageSize(pageSize);
			PageList pageResult = zeroSubGroupImgService.selectPageByCondition(pageList);
			if (null == pageResult || pageResult.getTotalSize() <= 0) {
				return;
			}
			List<ZeroSubGroupImgEntity> subGroupImgs = pageResult.getData();
			if (CollectionUtils.isEmpty(subGroupImgs)) {
				return;
			}
			replaceImgUrlForUpyun(subGroupImgs);
			page++;
		} while (true);
	}

	private void replaceImgUrlForUpyun(List<ZeroSubGroupImgEntity> subGroupImgs) {
		List<ReplaceImgUrlDto> replaceUrls = Lists.newArrayList();
		//上传又拍云
		subGroupImgs.forEach(p -> {
			if (StringUtils.isNotBlank(p.getUpImgUrl())) {
				return;
			}
			byte[] picBytes;
			String url = p.getImgUrl();
			String path = String.valueOf("/cloud_stock/collect/zero/")
					.concat(String.valueOf(p.getGroupId()))
					.concat("_")
					.concat(String.valueOf(p.getSubGroupId()))
					.concat("_")
					.concat(StringUtils.isBlank(p.getItId()) ? "" : p.getItId())
					.concat("/")
					.concat(String.valueOf(System.currentTimeMillis()))
					.concat(String.valueOf(new Random().nextInt(9999) + 10000))
					.concat(".jpeg");
			try {
				picBytes = Utils.get(HttpExecutor.class).get(url).executeAsByte();
				if (xkStorageService.writeFile(path, picBytes)) {
					LOGGER.info("Successfully write 007 to upyun pic=={} to upyun with path={} !!!", url, path);
				} else {
					LOGGER.error("write EPC pic=={} to upyun with path={} failed", url, path);
					return;
				}
			} catch (Exception e) {
				LOGGER.error("get source:007 pic:{} failed", url);
				return;
			}
			String fullPath = xkStorageService.getBindAddress().concat(path);

			//build replace list
			ReplaceImgUrlDto replaceImgUrl = new ReplaceImgUrlDto();
			replaceImgUrl.setOldUrl(p.getImgUrl());
			replaceImgUrl.setNewUrl(fullPath);
			replaceUrls.add(replaceImgUrl);
		});
		if (CollectionUtils.isNotEmpty(replaceUrls)) {
			//根据原图片地址修改成新图片地址
			LOGGER.info("pre - 修改图片地址,===> {}", JSON.toJSONString(replaceUrls));
			zeroSubGroupImgService.updateImgUrls(replaceUrls);
		}
	}
}
