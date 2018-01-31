package com.xiaoka.cloud.stock.service.wrapper.superepc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.xiaoka.freework.utils.json.Jackson;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by sabo on 16/11/2017.
 */
public class SuperEPCJackson extends Jackson.Base{

	private static volatile SuperEPCJackson superEPCJackson;

	private SuperEPCJackson() {
		super();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

	public static SuperEPCJackson singleton() {
		if (superEPCJackson == null) {
			synchronized (SuperEPCJackson.class) {
				if (superEPCJackson == null) {
					superEPCJackson = new SuperEPCJackson();
				}
			}
		}
		return superEPCJackson;
	}

}
