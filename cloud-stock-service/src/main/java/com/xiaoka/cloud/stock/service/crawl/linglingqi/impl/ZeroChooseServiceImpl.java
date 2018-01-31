package com.xiaoka.cloud.stock.service.crawl.linglingqi.impl;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.*;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.*;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.freework.utils.lock.ClusterLock;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Do something
 *
 * @author gancao 2017/12/20 14:35
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroChooseServiceImpl implements ZeroChooseService {

	private static final String MODEL_KEY = "/cloud-stock/zero/car";
	private static final Logger logger = LoggerFactory.getLogger(ZeroChooseServiceImpl.class);
	@Resource
	private ZeroCarModelChooseRepo zeroCarModelChooseRepo;
	@Resource
	private ZeroCarSubGroupChooseRepo zeroCarSubGroupChooseRepo;
	@Resource
	private ZeroCarGroupChooseRepo zeroCarGroupChooseRepo;
	@Resource
	private ZeroAccountRepo zeroAccountRepo;
	@Resource
	private ZeroCarModelAccountRepo zeroCarModelAccountRepo;

	@Override
	public int accountAmount() {
		return zeroAccountRepo.accountAmount();
	}

	@Override
	public ZeroAccountEntity getZeroAccountEntityById(Integer id) {
		ZeroAccountEntity param = new ZeroAccountEntity();
		param.setId(id);
		param.setIsValid(1);
		List<ZeroAccountEntity> accountEntityList = zeroAccountRepo.select(param);
		if (CollectionUtils.isNotEmpty(accountEntityList)){
			return accountEntityList.get(0);
		}
		return null;
	}

	@Override
	public ZeroAccountEntity getZeroAccountEntityBySupplierId(Integer supplierId) {
		ZeroAccountEntity param = new ZeroAccountEntity();
		param.setSupplierId(supplierId);
		param.setType(1);
		param.setIsValid(1);
		List<ZeroAccountEntity> accountEntityList = zeroAccountRepo.select(param);
		if (CollectionUtils.isNotEmpty(accountEntityList)){
			return accountEntityList.get(0);
		}
		return null;
	}

	@Override
	public List<ZeroAccountEntity> getZeroProxyAccountEntityList() {
		return zeroAccountRepo.selectProxyAccount();
	}

	@Override
	public ZeroAccountEntity getZeroAccountEntityByIp(String ip) {
		ZeroAccountEntity param = new ZeroAccountEntity();
		param.setIsValid(1);
		param.setRealIp(ip);
		List<ZeroAccountEntity> accountEntityList = zeroAccountRepo.select(param);
		if (CollectionUtils.isNotEmpty(accountEntityList)){
			return accountEntityList.get(0);
		}else {
			//ip未分配帐号，取还未分配ip的帐号
			accountEntityList = zeroAccountRepo.selectNoIp();
			if (CollectionUtils.isNotEmpty(accountEntityList)){
				//取第一个未分配的帐号进行分配
				ZeroAccountEntity checkAccount = accountEntityList.get(0);
				checkAccount.setRealIp(ip);
				zeroAccountRepo.updateBySelective(checkAccount);
				return checkAccount;
			}
		}
		return null;
	}

	@Override
	public void updateErrorIp(Integer id) {
		zeroAccountRepo.updateErrorIp(id);
	}

	@Override
	public void updateZeroAccountEntity(ZeroAccountEntity entity) {
		zeroAccountRepo.updateBySelective(entity);
	}

	@Override
	public void insert(ZeroCarModelChooseEntity entity) {
		if (Objects.nonNull(entity)) {
			ZeroCarModelChooseEntity chooseEntity = this.getZeroCarModelChooseEntityByAuth(entity.getAuth());
			if (Objects.isNull(chooseEntity)){
				zeroCarModelChooseRepo.insert(entity);
			}
		}
	}

	@Override
	public String getAccessPhone(ZeroCarModelAccountEntity entity) {
		List<ZeroCarModelAccountEntity> carModelAccountEntityList = zeroCarModelAccountRepo.select(entity);
		String key = Jackson.mobile().writeValueAsString(entity);
		int keyValue = key.hashCode();
		ClusterLock.Locker locker = Utils.get(ClusterLock.class).transiantLock(MODEL_KEY, keyValue + "");
		try {
			if (locker.acquire(5000, TimeUnit.SECONDS)) {
				return locker.execute(() -> getPhone(carModelAccountEntityList, entity));
			}
		} catch (Exception e) {
			logger.error("选择处理帐号异常", e);
		}
		return null;
	}

	@Override
	public void batchInsertZeroCarGroup(List<ZeroCarGroupChooseEntity> entityList) {
		if (CollectionUtils.isNotEmpty(entityList)) {
			List<String> authList = entityList.stream().map(ZeroCarGroupChooseEntity :: getAuth).collect(Collectors.toList());
			List<ZeroCarGroupChooseEntity> existList = zeroCarGroupChooseRepo.batchSelectByAuth(authList);
			List<ZeroCarGroupChooseEntity> insertList = Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(existList)){
				//取出不存在的
				Map<String, ZeroCarGroupChooseEntity> map = existList.stream().collect(Collectors.toMap(ZeroCarGroupChooseEntity :: getAuth, p -> p));
				entityList.forEach(entity -> {
					ZeroCarGroupChooseEntity chooseEntity = map.get(entity.getAuth());
					if (Objects.isNull(chooseEntity)){
						insertList.add(entity);
					}
				});
			}else {
				insertList.addAll(entityList);
			}
			if (CollectionUtils.isNotEmpty(insertList)){
				zeroCarGroupChooseRepo.batchInsert(insertList);
			}
		}
	}

	@Override
	public void updateZeroCarGroup(ZeroCarGroupChooseEntity entity) {
		zeroCarGroupChooseRepo.updateBySelective(entity);
	}

	@Override
	public void batchInsertZeroCarSubGroup(List<ZeroCarSubGroupChooseEntity> entityList) {
		if (CollectionUtils.isNotEmpty(entityList)) {
			List<String> authList = entityList.stream().map(ZeroCarSubGroupChooseEntity :: getAuth).collect(Collectors.toList());
			List<ZeroCarSubGroupChooseEntity> existList = zeroCarSubGroupChooseRepo.batchSelectByAuth(authList);
			List<ZeroCarSubGroupChooseEntity> insertList = Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(existList)){
				//取出不存在的
				Map<String, ZeroCarSubGroupChooseEntity> map = existList.stream().collect(Collectors.toMap(ZeroCarSubGroupChooseEntity :: getAuth, p -> p));
				entityList.forEach(entity -> {
					ZeroCarSubGroupChooseEntity chooseEntity = map.get(entity.getAuth());
					if (Objects.isNull(chooseEntity)){
						//不存在插入
						insertList.add(entity);
					}
				});
			}else {
				insertList.addAll(entityList);
			}
			if (CollectionUtils.isNotEmpty(insertList)){
				zeroCarSubGroupChooseRepo.batchInsert(insertList);
			}
		}
	}

	@Override
	public void updateZeroCarSubGroup(ZeroCarSubGroupChooseEntity entity) {
		zeroCarSubGroupChooseRepo.updateBySelective(entity);
	}

	@Override
	public List<ZeroCarModelChooseEntity> getZeroCarModelChooseEntityList(ZeroCarModelChooseEntity param) {
		return zeroCarModelChooseRepo.select(param);
	}

	@Override
	public ZeroCarModelChooseEntity getZeroCarModelChooseEntityByAuth(String auth) {
		if (StringUtils.isNotBlank(auth)) {
			ZeroCarModelChooseEntity param = new ZeroCarModelChooseEntity();
			param.setAuth(auth);
			param.setIsValid(1);
			List<ZeroCarModelChooseEntity> zeroCarModelChooseEntityList = zeroCarModelChooseRepo.select(param);
			if (CollectionUtils.isNotEmpty(zeroCarModelChooseEntityList)) {
				return zeroCarModelChooseEntityList.get(0);
			}
		}
		return null;
	}

	@Override
	public ZeroCarModelChooseEntity getZeroCarModelChooseEntityByCId(Integer cId) {
		if (Objects.nonNull(cId)) {
			ZeroCarModelChooseEntity param = new ZeroCarModelChooseEntity();
			param.setId(cId);
			param.setIsValid(1);
			List<ZeroCarModelChooseEntity> zeroCarModelChooseEntityList = zeroCarModelChooseRepo.select(param);
			if (CollectionUtils.isNotEmpty(zeroCarModelChooseEntityList)) {
				return zeroCarModelChooseEntityList.get(0);
			}
		}
		return null;
	}

	@Override
	public void updateZeroCarModelChooseEntity(ZeroCarModelChooseEntity entity) {
		zeroCarModelChooseRepo.updateBySelective(entity);
	}

	@Override
	public List<ZeroCarGroupChooseEntity> getZeroCarGroupChooseEntityList(ZeroCarGroupChooseEntity param) {
		return zeroCarGroupChooseRepo.select(param);
	}

	@Override
	public ZeroCarGroupChooseEntity getZeroCarGroupChooseEntityByAuth(String auth) {
		if (StringUtils.isNotBlank(auth)) {
			ZeroCarGroupChooseEntity param = new ZeroCarGroupChooseEntity();
			param.setAuth(auth);
			param.setIsValid(1);
			List<ZeroCarGroupChooseEntity> zeroCarModelChooseEntityList = zeroCarGroupChooseRepo.select(param);
			if (CollectionUtils.isNotEmpty(zeroCarModelChooseEntityList)) {
				return zeroCarModelChooseEntityList.get(0);
			}
		}
		return null;
	}

	@Override
	public ZeroCarSubGroupChooseEntity getZeroCarSubGroupChooseEntityByAuth(String auth) {
		if (StringUtils.isNotBlank(auth)) {
			ZeroCarSubGroupChooseEntity param = new ZeroCarSubGroupChooseEntity();
			param.setAuth(auth);
			param.setIsValid(1);
			List<ZeroCarSubGroupChooseEntity> zeroCarSubGroupChooseEntityList = zeroCarSubGroupChooseRepo.select(param);
			if (CollectionUtils.isNotEmpty(zeroCarSubGroupChooseEntityList)) {
				return zeroCarSubGroupChooseEntityList.get(0);
			}
		}
		return null;
	}

	@Override
	public boolean isComplete(Integer cId) {
		int chooseAmount = zeroCarSubGroupChooseRepo.getSubGroupChooseCountByCId(cId);
		int amount = zeroCarSubGroupChooseRepo.getSubGroupCountByCId(cId);
		//零件组的完成度超过90%即视为该车型已完成
		logger.info("爬取的零件组数量:{},保存的零件组数量:{}", chooseAmount, amount);
		return chooseAmount > 0 && amount > 0 && amount > chooseAmount * 0.8;
	}

	@Override
	public int countSubGroup(Integer cId) {
		return zeroCarSubGroupChooseRepo.getSubGroupCountByCId(cId);
	}

	@Override
	public void cleanCrawlData() {
		zeroCarModelChooseRepo.deleteData();
		zeroCarGroupChooseRepo.deleteData();
		zeroCarSubGroupChooseRepo.deleteData();
	}

	private String getPhone(List<ZeroCarModelAccountEntity> carModelAccountEntityList, ZeroCarModelAccountEntity entity) {
		boolean isExist = false;
		if (CollectionUtils.isNotEmpty(carModelAccountEntityList)) {
			ZeroCarModelAccountEntity checkEntity = carModelAccountEntityList.get(0);
			//该车型已经有存在的关联帐号，判断帐号是否有效
			ZeroAccountEntity param = new ZeroAccountEntity();
			param.setIsValid(1);
			param.setPhone(checkEntity.getPhone());
			List<ZeroAccountEntity> zeroAccountEntityList = zeroAccountRepo.select(param);
			if (CollectionUtils.isNotEmpty(zeroAccountEntityList)) {
				entity.setPhone(zeroAccountEntityList.get(0).getPhone());
				isExist = true;
			} else {
				//将原先关联的手机号设置为无效
				checkEntity.setIsValid(0);
				zeroCarModelAccountRepo.updateBySelective(checkEntity);
			}
		}
		if (!isExist) {
			//从帐号随机选择一个帐号作为下一链路的帐号
			List<ZeroAccountEntity> zeroAccountEntityList = this.getZeroProxyAccountEntityList();
			if (CollectionUtils.isNotEmpty(zeroAccountEntityList)) {
				ZeroAccountEntity checkEntity = zeroAccountEntityList.get(new Random().nextInt(zeroAccountEntityList.size()));
				entity.setPhone(checkEntity.getPhone());
				zeroCarModelAccountRepo.insert(entity);
			}
		}
		return entity.getPhone();
	}

}

