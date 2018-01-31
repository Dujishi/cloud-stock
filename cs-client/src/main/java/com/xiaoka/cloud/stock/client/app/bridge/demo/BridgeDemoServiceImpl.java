package com.xiaoka.cloud.stock.client.app.bridge.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaoka.freework.help.api.ApiAsserts;

@Service("bridgeDemoService")
public class BridgeDemoServiceImpl implements BridgeDemoService {
	
	@Override
	public String sayHello(String name) {
		return StringUtils.join("Hi ", name, ", I'm JS Bridge!");
	}

	@Override
	public Map<String, BridgeDemoDto> complex(BridgeDemoDto dto, List<BridgeDemoDto> dtos, Map<String, BridgeDemoDto> dtoMap) {
		ApiAsserts.notNull(dto, "PARAM_NULL",  "DTO数据为空");
		if(dtos != null) {
			dtos.forEach(d -> System.out.println(d.toString()));
		}
		if(dtoMap != null) {
			dtoMap.entrySet().forEach(c -> {
				System.out.println(StringUtils.join(c.getKey(), " <----> ", c.getValue().toString()));
			});
		}
		
		Map<String, BridgeDemoDto> rsMap = new HashMap<>();
		dto.setName(StringUtils.join("Hi ", dto.getName()));
		dto.setAge(dto.getAge() + 100);
		rsMap.put("NEW Object", dto);
		return rsMap;
	}
}
