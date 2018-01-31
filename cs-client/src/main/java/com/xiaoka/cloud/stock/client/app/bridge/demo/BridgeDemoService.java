package com.xiaoka.cloud.stock.client.app.bridge.demo;

import java.util.List;
import java.util.Map;

public interface BridgeDemoService {

	String sayHello(String name);

	Map<String, BridgeDemoDto> complex(BridgeDemoDto dto, List<BridgeDemoDto> dtos, Map<String, BridgeDemoDto> dtoMap);

}
