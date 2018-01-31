package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;
import java.util.Map;

import com.xiaoka.cloud.stock.core.crawl.dto.ReplaceImgUrlDto;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupImgEntity;
import com.xiaoka.freework.help.page.PageList;

/**
 * for car brand
 * ZeroSubGroupImgService
 *
 * @author zhouze
 */
public interface ZeroSubGroupImgService {
	List<ZeroSubGroupImgEntity> getListByAnd(ZeroSubGroupImgEntity zeroSubGroupImg);

	int delete(ZeroSubGroupImgEntity zeroSubGroupImg);

	void insert(ZeroSubGroupImgEntity zeroSubGroupImg);

	int updateBySelective(ZeroSubGroupImgEntity zeroSubGroupImg);

	/**
	 * 根据groupId、subGroupId、imgUrl查询列表
	 *
	 * @param subGroupImgs
	 * @return
	 */
	List<ZeroSubGroupImgEntity> selectByConditions(List<ZeroSubGroupImgEntity> subGroupImgs);

	/**
	 * 插入列表
	 *
	 * @param insertEntities
	 */
	void insertList(List<ZeroSubGroupImgEntity> insertEntities);

	PageList<ZeroSubGroupImgEntity> selectPageByCondition(PageList pageList);

	/**
	 * 修改图片url
	 * key:原来的图url
	 * value：新图url
	 *
	 * @param list
	 */
	void updateImgUrls(List<ReplaceImgUrlDto> list);
}
