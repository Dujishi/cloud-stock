package client;

import com.alibaba.fastjson.JSON;
import input.SupplierStockPartSyncInput;
import input.SupplierSyncInput;
import org.junit.Test;
import output.ResponseObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/11
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CooperateStockSyncClientFixture {

	@Test
	public void initDataTest() throws Exception {
		//todo test
		SupplierSyncInput supplierSyncInput = new SupplierSyncInput();
		supplierSyncInput.setSupplierId("1000");

		List<SupplierStockPartSyncInput> inputs                      = new ArrayList<SupplierStockPartSyncInput>();
		SupplierStockPartSyncInput       supplierStockPartSyncInput1 = new SupplierStockPartSyncInput();
		supplierStockPartSyncInput1.setOeNo("oeNo");
		supplierStockPartSyncInput1.setTradePrice(BigDecimal.valueOf(120));
		supplierStockPartSyncInput1.setDepotId("2");
		supplierStockPartSyncInput1.setDepotAddress("XXs");
		supplierStockPartSyncInput1.setPartId("1019");
		supplierStockPartSyncInput1.setOeNo("XSLE1002");
		supplierStockPartSyncInput1.setBalanceCount(BigDecimal.TEN);
		supplierStockPartSyncInput1.setManufacturer("XXX");
		supplierStockPartSyncInput1.setPartName("partName");
		supplierStockPartSyncInput1.setOriginPlace("OriginPlace");
		supplierStockPartSyncInput1.setOperateMode(1);
		supplierStockPartSyncInput1.setCostPrice(BigDecimal.TEN);
		supplierStockPartSyncInput1.setTradePrice(BigDecimal.TEN);
		supplierStockPartSyncInput1.setInsurancePrice(BigDecimal.TEN);
		supplierStockPartSyncInput1.setRepairFactoryPrice(BigDecimal.TEN);
		supplierStockPartSyncInput1.setRepairStationPrice(BigDecimal.TEN);
		inputs.add(supplierStockPartSyncInput1);

		SupplierStockPartSyncInput supplierStockPartSyncInput2 = new SupplierStockPartSyncInput();
		supplierStockPartSyncInput2.setOeNo("oeNo");
		supplierStockPartSyncInput2.setTradePrice(BigDecimal.valueOf(120));
		supplierStockPartSyncInput2.setDepotId("2");
		supplierStockPartSyncInput2.setDepotAddress("XXs");
		supplierStockPartSyncInput2.setPartId("");
		supplierStockPartSyncInput2.setOeNo("XSLE1002");
		supplierStockPartSyncInput2.setBalanceCount(BigDecimal.TEN);
		supplierStockPartSyncInput2.setManufacturer("XXX");
		supplierStockPartSyncInput2.setPartName("partName");
		supplierStockPartSyncInput2.setOriginPlace("OriginPlace");
		supplierStockPartSyncInput2.setOperateMode(1);
		supplierStockPartSyncInput2.setCostPrice(BigDecimal.TEN);
		supplierStockPartSyncInput2.setTradePrice(BigDecimal.TEN);
		supplierStockPartSyncInput2.setInsurancePrice(BigDecimal.TEN);
		supplierStockPartSyncInput2.setRepairFactoryPrice(BigDecimal.TEN);
		supplierStockPartSyncInput2.setRepairStationPrice(BigDecimal.TEN);
		inputs.add(supplierStockPartSyncInput2);
		supplierSyncInput.setParts(inputs);
		ResponseObject responseObject = new CooperateStockSyncClient().initData(supplierSyncInput);

		System.out.println(JSON.toJSONString(responseObject));
	}

	@Test
	public void bulkData() throws Exception {
		//todo test
		new CooperateStockSyncClient().bulkData(null);
	}

}
