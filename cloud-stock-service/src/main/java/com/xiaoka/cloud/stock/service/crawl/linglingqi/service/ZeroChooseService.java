package com.xiaoka.cloud.stock.service.crawl.linglingqi.service;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.*;

import java.util.List;

/**
 * 数据爬取统一DB服务
 *
 * @author gancao 2017/12/20 14:35
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ZeroChooseService {

	int accountAmount();

	ZeroAccountEntity getZeroAccountEntityById(Integer id);

	ZeroAccountEntity getZeroAccountEntityBySupplierId(Integer supplierId);

	List<ZeroAccountEntity> getZeroProxyAccountEntityList();

	ZeroAccountEntity getZeroAccountEntityByIp(String ip);

	void updateErrorIp(Integer id);

	void updateZeroAccountEntity(ZeroAccountEntity entity);

	void insert(ZeroCarModelChooseEntity entity);

	String getAccessPhone(ZeroCarModelAccountEntity entity);

	void batchInsertZeroCarGroup(List<ZeroCarGroupChooseEntity> entityList);

	void updateZeroCarGroup(ZeroCarGroupChooseEntity entity);

	void batchInsertZeroCarSubGroup(List<ZeroCarSubGroupChooseEntity> entityList);

	void updateZeroCarSubGroup(ZeroCarSubGroupChooseEntity entity);

	List<ZeroCarModelChooseEntity> getZeroCarModelChooseEntityList(ZeroCarModelChooseEntity param);

	ZeroCarModelChooseEntity getZeroCarModelChooseEntityByAuth(String auth);

	ZeroCarModelChooseEntity getZeroCarModelChooseEntityByCId(Integer cId);

	void updateZeroCarModelChooseEntity(ZeroCarModelChooseEntity entity);

	List<ZeroCarGroupChooseEntity> getZeroCarGroupChooseEntityList(ZeroCarGroupChooseEntity param);

	ZeroCarGroupChooseEntity getZeroCarGroupChooseEntityByAuth(String auth);

	ZeroCarSubGroupChooseEntity getZeroCarSubGroupChooseEntityByAuth(String auth);

	void cleanCrawlData();

	boolean isComplete(Integer cId);

	int countSubGroup(Integer cId);


}
