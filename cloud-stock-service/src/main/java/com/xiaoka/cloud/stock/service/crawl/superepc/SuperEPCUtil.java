package com.xiaoka.cloud.stock.service.crawl.superepc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by sabo on 18/11/2017.
 */
public class SuperEPCUtil {

	public static Integer integerValueOf(String val, String field, Logger logger) {
		try {
			return StringUtils.isBlank(val) ? null : Integer.valueOf(val.trim());
		} catch (Exception e) {
			logger.error("!!!{} convert [{}] to Integer error!!!", field, val);
		}
		return null;
	}

	public static BigDecimal bigDecimalValueOf(String val) {
		return StringUtils.isBlank(val) ? null : new BigDecimal(val.trim());
	}

	public static <T> List<T> distinctList(List<T> list, Function<? super T, ?>... keyExtractors) {
		return list
				.stream()
				.filter(distinctByKeys(keyExtractors))
				.collect(Collectors.toList());
	}

	private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {

		final Map<String, Boolean> seen = new HashMap<>();

		return t -> {
			final String keys = Arrays.stream(keyExtractors)
			                           .map(ke -> String.valueOf(ke.apply(t)))
			                           .reduce((a,b)->a.concat("_").concat(b)).get();
			return seen.putIfAbsent(keys, Boolean.TRUE) == null;
		};

	}

}
