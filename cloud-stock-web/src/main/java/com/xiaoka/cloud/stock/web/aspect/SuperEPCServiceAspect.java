package com.xiaoka.cloud.stock.web.aspect;

import com.xiaoka.cloud.stock.core.epc.repo.entity.EpcRequestLogEntity;
import com.xiaoka.cloud.stock.service.crawl.core.log.EpcLogUtil;
import com.xiaoka.cloud.stock.service.crawl.core.log.constant.EpcLogTypeEnum;
import com.xiaoka.cloud.stock.service.crawl.core.log.dto.EpcLogDto;
import com.xiaoka.cloud.stock.service.epc.EpcAggregateService;
import com.xiaoka.cloud.stock.service.epc.EpcRedisService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import com.xiaoka.freework.container.spring.config.ConfigNotify;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Do something
 *
 * @author gancao 2017/11/16 18:18
 * @see [相关类/方法]
 * @since [版本号]
 */
@Aspect
@Component
public class SuperEPCServiceAspect {

	private static Logger log = LoggerFactory.getLogger(SuperEPCServiceAspect.class);
	private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Resource
	private EpcRedisService epcRedisService;
	@Resource
	private EpcAggregateService epcAggregateService;
	@ConfigNotify
	@Value("superEpc.max.count")
	private String maxCount;

	@Around("within(com.xiaoka.cloud.stock.service.wrapper.superepc.*Service) || "
			+ "execution(* com.xiaoka.cloud.stock.service.epc.SuperEpcService.*(..))")
	private Object cacheProcess(ProceedingJoinPoint jp) throws Throwable {
		String className = jp.getTarget().getClass().getName();
		if ("com.xiaoka.cloud.stock.service.epc.impl.SuperEpcServiceImpl".equals(className)) {
			//前台应用调用EPC接口处理,判断是否走EPC接口
			if (epcRedisService.isFilterEpc()) {
				return null;
			} else {
				return jp.proceed();
			}
		} else {
			long amount = epcAggregateService.aggregateSuperEpcByToday();
			if (StringUtils.isNotBlank(maxCount)){
				log.info("正时今日请求数量:{}", amount);
				try {
					long compareAmount = Long.parseLong(maxCount);
					if (amount == compareAmount || amount > compareAmount){
						//请求数量已经达到最大值返回不请求接口
						log.error("正时今日请求已达上线");
						return null;
					}
				}catch (Exception e){
					log.error("判断正时调用量出错", e);
				}
			}
			//EPC接口调用请求记录
			long start = System.currentTimeMillis();//当前时间
			int status = 1;
			Object object;
			try {
				object = jp.proceed();
				this.requestLog(jp, start, status);
			} catch (Exception e) {//异常不拦截
				status = 0;
				this.requestLog(jp, start, status);
				throw e;
			}
			return object;
		}
	}

	private void requestLog(ProceedingJoinPoint jp, long start, int status) {
		String methodName = jp.getSignature().getName();
		EpcLogDto epcLogDto = new EpcLogDto();
		epcLogDto.setStatus(status+"");
		epcLogDto.setType(EpcLogTypeEnum.正时.getType());
		epcLogDto.setArgs(Jackson.base().writeValueAsString(jp.getArgs()));
		epcLogDto.setExecTime(System.currentTimeMillis() - start);
		epcLogDto.setUrl(this.getSuperEpcUrl(methodName));
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		if (Objects.nonNull(cloudSupplierUserDto)){
			epcLogDto.setUserId(cloudSupplierUserDto.getId());//供应商用户id
			epcLogDto.setSupplierId(cloudSupplierUserDto.getSupplierId());//供应商id
		}
		executorService.execute(() -> EpcLogUtil.logTrace(epcLogDto));
	}

	private EpcRequestLogEntity buildEpcRequestLogEntity(String service, String method, Object[] args, long start, int status) {
		long cost = System.currentTimeMillis() - start;
		log.info("服务类:{},方法:{},参数:{},耗时:{}ms", service, method, Jackson.mobile().writeValueAsString(args), cost);
		EpcRequestLogEntity entity = new EpcRequestLogEntity();
		entity.setService(service);
		entity.setMethod(method);
		entity.setArgs(Jackson.mobile().writeValueAsString(args));
		entity.setCost((int) cost);
		entity.setStatus(status);
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		return entity;
	}

	/**
	 * 因为正时包装层使用框架HttpExecutor方法拿不到Connection相关属性所以只能暂时以判断来做
	 * @param method
	 * @return
	 */
	private String getSuperEpcUrl(String method){
		if ("getAssemblyCatalogue".equals(method)){
			return SuperEPCconstant.URL_3_1;
		}else if ("getPicInfo".equals(method)){
			return SuperEPCconstant.URL_3_2;
		}else if ("getPicInfoForAssembly".equals(method)){
			return SuperEPCconstant.URL_3_2_1;
		}else if ("getPartsInfo".equals(method)){
			return SuperEPCconstant.URL_3_3;
		}else if ("getAllSeries".equals(method)){
			return SuperEPCconstant.URL_1_2;
		}else if ("getCarModelByVin".equals(method)){
			return SuperEPCconstant.URL_1_3;
		}else if ("getCarModelInfoByTid".equals(method)){
			return SuperEPCconstant.URL_1_4;
		}else if ("getAllCarModel".equals(method)){
			return SuperEPCconstant.URL_2_1;
		}else if ("getTimerAccemblyList".equals(method)){
			return SuperEPCconstant.URL_2_2;
		}else if ("getTimerStandardParts".equals(method)){
			return SuperEPCconstant.URL_2_3;
		}else if ("getAdapterModels".equals(method)){
			return SuperEPCconstant.URL_3_6;
		}else if ("getReplaceParts".equals(method)){
			return SuperEPCconstant.URL_3_9;
		}else if ("judgePartCode".equals(method)){
			return SuperEPCconstant.URL_4_1;
		}else if ("getOEPrice".equals(method)){
			return SuperEPCconstant.URL_3_7;
		}else if ("getTid".equals(method)){
			return SuperEPCconstant.URL_1_1;
		}
		return null;
	}

}
