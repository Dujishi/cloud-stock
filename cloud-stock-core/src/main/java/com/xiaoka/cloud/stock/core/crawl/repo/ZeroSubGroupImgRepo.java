package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.dto.ReplaceImgUrlDto;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupImgEntity;
import com.xiaoka.freework.help.page.PageList;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * ZeroSubGroupImgRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroSubGroupImgRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroSubGroupImgEntity> select(ZeroSubGroupImgEntity param) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroSubGroupImgEntity param) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroSubGroupImgEntity param) {
		commonDao.mapper(ZeroSubGroupImgEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroSubGroupImgEntity param) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroSubGroupImgEntity> selectByConditions(List<ZeroSubGroupImgEntity> subGroupImgs) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(MASTER).sql("selectByConditions").session()
				.selectList(subGroupImgs);
	}

	public Integer insertList(List<ZeroSubGroupImgEntity> insertEntities) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertEntities);
	}

	public Integer selectSumCount(PageList pageList) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(SLAVE).sql("selectSumCount").session()
				.selectOne(pageList);
	}

	public List<ZeroSubGroupImgEntity> selectPage(PageList pageList) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(SLAVE).sql("selectPage").session()
				.selectList(pageList);
	}

	public Integer updateImgUrls(List<ReplaceImgUrlDto> list) {
		return commonDao.mapper(ZeroSubGroupImgEntity.class).source(MASTER).sql("updateImgUrls").session()
				.update(list);
	}
}
