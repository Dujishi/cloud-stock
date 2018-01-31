package com.xiaoka.cloud.stock.service.crawl.linglingqi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.*;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.CarBrandResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataCollectService;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/12/19
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml",
		"classpath*:spring/tool-service.xml",
		"classpath*:spring/cloud-stock-elk-test.xml"
})
public class ZeroDataCollectServiceTest extends ContainerTest {


	@Resource
	ZeroDataCollectService zeroDataCollectService;

	@Test
	public void saveBrandsTest() {
		String result = "{\"code\": 1, \"title\": \"\\u9009\\u62e9\\u54c1\\u724c\", \"keys\": [], \"time\": 1516685653, \"msg\": \"\", \"data\": [{\"brand\": \"alfaromeo\", \"img\": \"/img/alfaromeo.png\", \"name\": \"\\u963f\\u5c14\\u6cd5\\u7f57\\u5bc6\\u6b27\", \"auth\": \"\", \"uri\": \"/cars/show?brand=alfaromeo\"}, {\"brand\": \"audi\", \"img\": \"/img/audi.png\", \"name\": \"\\u5965\\u8fea\", \"auth\": \"\", \"uri\": \"/cars/show?brand=audi\"}, {\"brand\": \"astonmartin\", \"img\": \"/img/astonmartin.png\", \"name\": \"\\u963f\\u65af\\u987f\\u9a6c\\u4e01\", \"auth\": \"\", \"uri\": \"/cars/show?brand=astonmartin\"}, {\"brand\": \"bmw\", \"img\": \"/img/bmw.png\", \"name\": \"\\u5b9d\\u9a6c\", \"auth\": \"\", \"uri\": \"/cars/show?brand=bmw\"}, {\"brand\": \"porsches\", \"img\": \"/img/porsches.png\", \"name\": \"\\u4fdd\\u65f6\\u6377\", \"auth\": \"\", \"uri\": \"/cars/show?brand=porsches\"}, {\"brand\": \"baic\", \"img\": \"/img/baic.png\", \"name\": \"\\u5317\\u6c7d\", \"auth\": \"\", \"uri\": \"/cars/show?brand=baic\"}, {\"brand\": \"benz\", \"img\": \"/img/benz.png\", \"name\": \"\\u5954\\u9a70\", \"auth\": \"\", \"uri\": \"/cars/show?brand=benz\"}, {\"brand\": \"honda\", \"img\": \"/img/honda.png\", \"name\": \"\\u672c\\u7530\", \"auth\": \"\", \"uri\": \"/cars/show?brand=honda\"}, {\"brand\": \"bentley\", \"img\": \"/img/bentley.png\", \"name\": \"\\u5bbe\\u5229\", \"auth\": \"\", \"uri\": \"/cars/show?brand=bentley\"}, {\"brand\": \"vwag\", \"img\": \"/img/vwag.png\", \"name\": \"\\u5927\\u4f17\", \"auth\": \"\", \"uri\": \"/cars/show?brand=vwag\"}, {\"brand\": \"ferrari\", \"img\": \"/img/ferrari.png\", \"name\": \"\\u6cd5\\u62c9\\u5229\", \"auth\": \"\", \"uri\": \"/cars/show?brand=ferrari\"}, {\"brand\": \"fiat\", \"img\": \"/img/fiat.png\", \"name\": \"\\u83f2\\u4e9a\\u7279\", \"auth\": \"\", \"uri\": \"/cars/show?brand=fiat\"}, {\"brand\": \"toyota\", \"img\": \"/img/toyota.png\", \"name\": \"\\u4e30\\u7530\", \"auth\": \"\", \"uri\": \"/cars/show?brand=toyota\"}, {\"brand\": \"jaguar\", \"img\": \"/img/jaguar.png\", \"name\": \"\\u6377\\u8c79\", \"auth\": \"\", \"uri\": \"/cars/show?brand=jaguar\"}, {\"brand\": \"jeep\", \"img\": \"/img/jeep.png\", \"name\": \"JEEP\", \"auth\": \"\", \"uri\": \"/cars/show?brand=jeep\"}, {\"brand\": \"chrysler\", \"img\": \"/img/chrysler.png\", \"name\": \"\\u514b\\u83b1\\u65af\\u52d2\", \"auth\": \"\", \"uri\": \"/cars/show?brand=chrysler\"}, {\"brand\": \"bullstuff\", \"img\": \"/img/bullstuff.png\", \"name\": \"\\u5170\\u535a\\u57fa\\u5c3c\", \"auth\": \"\", \"uri\": \"/cars/show?brand=bullstuff\"}, {\"brand\": \"rolls-royce\", \"img\": \"/img/rolls-royce.png\", \"name\": \"\\u52b3\\u65af\\u83b1\\u65af\", \"auth\": \"\", \"uri\": \"/cars/show?brand=rolls-royce\"}, {\"brand\": \"lexus\", \"img\": \"/img/lexus.png\", \"name\": \"\\u96f7\\u514b\\u8428\\u65af\", \"auth\": \"\", \"uri\": \"/cars/show?brand=lexus\"}, {\"brand\": \"land_rover\", \"img\": \"/img/land_rover.png\", \"name\": \"\\u8def\\u864e\", \"auth\": \"\", \"uri\": \"/cars/show?brand=land_rover\"}, {\"brand\": \"maybach\", \"img\": \"/img/maybach.png\", \"name\": \"\\u8fc8\\u5df4\\u8d6b\", \"auth\": \"\", \"uri\": \"/cars/show?brand=maybach\"}, {\"brand\": \"maserati\", \"img\": \"/img/maserati.png\", \"name\": \"\\u739b\\u838e\\u62c9\\u8482\", \"auth\": \"\", \"uri\": \"/cars/show?brand=maserati\"}, {\"brand\": \"minis\", \"img\": \"/img/minis.png\", \"name\": \"MINI\", \"auth\": \"\", \"uri\": \"/cars/show?brand=minis\"}, {\"brand\": \"acura\", \"img\": \"/img/acura.png\", \"name\": \"\\u8bb4\\u6b4c\", \"auth\": \"\", \"uri\": \"/cars/show?brand=acura\"}, {\"brand\": \"mitsubishi\", \"img\": \"/img/mitsubishi.png\", \"name\": \"\\u4e09\\u83f1\", \"auth\": \"\", \"uri\": \"/cars/show?brand=mitsubishi\"}, {\"brand\": \"skoda\", \"img\": \"/img/skoda.png\", \"name\": \"\\u65af\\u67ef\\u8fbe\", \"auth\": \"\", \"uri\": \"/cars/show?brand=skoda\"}, {\"brand\": \"smart\", \"img\": \"/img/smart.png\", \"name\": \"smart\", \"auth\": \"\", \"uri\": \"/cars/show?brand=smart\"}, {\"brand\": \"tesla\", \"img\": \"/img/tesla.png\", \"name\": \"\\u7279\\u65af\\u62c9\", \"auth\": \"\", \"uri\": \"/cars/show?brand=tesla\"}, {\"brand\": \"volvo\", \"img\": \"/img/volvo.png\", \"name\": \"\\u6c83\\u5c14\\u6c83\", \"auth\": \"\", \"uri\": \"/cars/show?brand=volvo\"}, {\"brand\": \"hyundai\", \"img\": \"/img/hyundai.png\", \"name\": \"\\u73b0\\u4ee3\", \"auth\": \"\", \"uri\": \"/cars/show?brand=hyundai\"}, {\"brand\": \"seat\", \"img\": \"/img/seat.png\", \"name\": \"\\u897f\\u96c5\\u7279\", \"auth\": \"\", \"uri\": \"/cars/show?brand=seat\"}]}";
		ZeroResp<List<CarBrandResp>> dto = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<CarBrandResp>>>() {
		});
		//得到品牌列表
		List<CarBrandResp> respList = dto.getData();
		if (CollectionUtils.isNotEmpty(respList)) {
			List<ZeroBrandDto> zeroBrandDtoList = Lists.newArrayList();
			respList.forEach(resp -> {
				ZeroBrandDto zeroBrandDto = new ZeroBrandDto();
				try {
					zeroBrandDto.setBrand(URLDecoder.decode(resp.getBrand(), "UTF-8"));
					zeroBrandDto.setName(URLDecoder.decode(resp.getName(), "UTF-8"));
					zeroBrandDto.setUri(URLDecoder.decode(resp.getImg(), "UTF-8"));
					zeroBrandDtoList.add(zeroBrandDto);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			});
			zeroDataCollectService.saveBrands(zeroBrandDtoList);
			//车型品牌
		}

	}

	public static void main(String[] args) {
		try {
			System.out.println(URLDecoder.decode("\u9009\u62e9\u54c1\u724c", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveCarModelsTest() {
		List<ZeroCarModelDto> list = Lists.newArrayList();
		ZeroCarModelDto       c1   = new ZeroCarModelDto();
		c1.setBrand("maserati");
		c1.setCarModel("Coupé");
		list.add(c1);

		ZeroCarModelDto c2 = new ZeroCarModelDto();
		c2.setBrand("maserati");
		c2.setCarModel("Ghibli 2014 - 2016");
		list.add(c2);

		zeroDataCollectService.saveCarModels(list);
	}

	@Test
	public void saveGroupsTest() {
		List<ZeroGroupDto> list = Lists.newArrayList();
		ZeroGroupDto       g1   = new ZeroGroupDto();
		g1.setcId(1793);
		g1.setBrand("maserati");
		g1.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		g1.setEngine("3.0 BT SOFT V6 2WD 330 HP");
		g1.setGearBox("AUTOMATIC");
		g1.setMarket("ALGERIA");
		g1.setYear("2014");
		g1.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		g1.setGroupName("01 发动机");
		g1.setGroupNum("01");
		List<ZeroSubGroupDto> sgList = Lists.newArrayList();
		ZeroSubGroupDto       sg1    = new ZeroSubGroupDto();
		sg1.setcId(1793);
		sg1.setBrand("maserati");
		sg1.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		sg1.setMarket("ALGERIA");
		sg1.setYear("2014");
		sg1.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		sg1.setGroupName("01 发动机");
		sg1.setSubGroup("00_2");
		sg1.setSubGroupName("1_00_2 曲轴箱");
		sg1.setSubGroupUrl("https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUkSiUlSic3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUkNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTclJUonN2g%3D&w=160&h=160");
		sg1.setSubDesc("");
		sg1.setSubMid("01_00_2");
		sg1.setSubModel("");
		sgList.add(sg1);
		ZeroSubGroupDto sg2 = new ZeroSubGroupDto();
		sg2.setcId(1793);
		sg2.setBrand("maserati");
		sg2.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		sg2.setMarket("ALGERIA");
		sg2.setYear("2014");
		sg2.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		sg2.setGroupName("01 发动机");
		sg2.setSubGroup("10_2");
		sg2.setSubGroupName("01_10_2 曲轴机构");
		sg2.setSubGroupUrl("https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUkSiQlSic3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUkNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTckJUonN2g%3D&w=160&h=160");
		sg2.setSubDesc("");
		sg2.setSubMid("01_10_2");
		sg2.setSubModel("");
		sgList.add(sg2);
		g1.setZeroSubGroups(sgList);
		list.add(g1);

		ZeroGroupDto g2 = new ZeroGroupDto();
		g2.setcId(1793);
		g2.setBrand("maserati");
		g2.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		g2.setEngine("3.0 BT SOFT V6 2WD 330 HP");
		g2.setGearBox("AUTOMATIC");
		g2.setMarket("ALGERIA");
		g2.setYear("2014");
		g2.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		g2.setGroupName("03 变速箱");
		g2.setGroupNum("03");
		List<ZeroSubGroupDto> sgList2 = Lists.newArrayList();
		ZeroSubGroupDto       sg3     = new ZeroSubGroupDto();
		sg3.setcId(1793);
		sg3.setBrand("maserati");
		sg3.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		sg3.setMarket("ALGERIA");
		sg3.setYear("2014");
		sg3.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		sg3.setGroupName("03 变速箱");
		sg3.setSubGroup("02_1");
		sg3.setSubGroupName("03_02_1 自动变速箱的传动控制器");
		sg3.setSubGroupUrl("https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUmSiUnSiQ3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUmNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTclJ0okN2g%3D&w=160&h=160");
		sg3.setSubDesc("");
		sg3.setSubMid("03_02_1");
		sg3.setSubModel("");
		sgList2.add(sg3);
		ZeroSubGroupDto sg4 = new ZeroSubGroupDto();
		sg4.setcId(1793);
		sg4.setBrand("maserati");
		sg4.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		sg4.setMarket("ALGERIA");
		sg4.setYear("2014");
		sg4.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		sg4.setGroupName("03 变速箱");
		sg4.setSubGroup("10_2");
		sg4.setSubGroupName("03_10_2 变速箱外壳");
		sg4.setSubGroupUrl("https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUkSiQlSic3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUkNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTckJUonN2g%3D&w=160&h=160");
		sg4.setSubDesc("");
		sg4.setSubMid("03_10_2");
		sg4.setSubModel("");
		sgList2.add(sg4);
		g2.setZeroSubGroups(sgList2);
		list.add(g2);

		zeroDataCollectService.saveGroups(list);

		Assert.assertNotNull(list);
		Assert.assertTrue(list.stream().allMatch(x -> null != x.getGroupId()));
		Assert.assertTrue(list.stream().allMatch(x -> {
			if (CollectionUtils.isNotEmpty(x.getZeroSubGroups())) {
				return x.getZeroSubGroups().stream().allMatch(p -> null != p.getGroupId() && null != p.getSubGroupId());
			}
			return true;
		}));
	}

	@Test
	public void saveSubGroupImgsTest() {
		List<ZeroSubGroupImgDto> list = Lists.newArrayList();
		ZeroSubGroupImgDto       img1 = new ZeroSubGroupImgDto();
		img1.setGroupId(12);
		img1.setSubGroupId(11);
		img1.setGroupName("03 变速箱");
		img1.setSubGroupName("03_02_1 自动变速箱的传动控制器");
		img1.setImgUrl("https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUmSiUnSiQ3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUmNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTclJ0okN2g%3D&w=160&h=160");
		img1.setHeight(BigDecimal.valueOf(1024));
		img1.setWidth(BigDecimal.valueOf(966));
		img1.setX1(BigDecimal.valueOf(101));
		img1.setX2(BigDecimal.valueOf(102));
		img1.setY1(BigDecimal.valueOf(201));
		img1.setY2(BigDecimal.valueOf(202));
		img1.setItId("1");
		list.add(img1);

		ZeroSubGroupImgDto img2 = new ZeroSubGroupImgDto();
		img2.setGroupId(12);
		img2.setSubGroupId(12);
		img2.setGroupName("03 变速箱");
		img2.setSubGroupName("03_10_2 变速箱外壳");
		img2.setImgUrl("https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUkSiQlSic3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUkNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTckJUonN2g%3D&w=160&h=160");
		img2.setHeight(BigDecimal.valueOf(2024));
		img2.setWidth(BigDecimal.valueOf(1966));
		img2.setX1(BigDecimal.valueOf(1101));
		img2.setX2(BigDecimal.valueOf(1102));
		img2.setY1(BigDecimal.valueOf(1201));
		img2.setY2(BigDecimal.valueOf(1202));
		img1.setItId("1");
		list.add(img2);


		zeroDataCollectService.saveSubGroupImgs(list);
	}


	@Test
	public void saveSubGroupPartsTest(){
		List<ZeroSubGroupPartsDto> list = Lists.newArrayList();
		ZeroSubGroupPartsDto p1 = new ZeroSubGroupPartsDto();
		p1.setcId(1793);
		p1.setBrand("maserati");
		p1.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		p1.setGroupId(12);
		p1.setSubGroup("02_1");
		p1.setSubGroupId(11);
		p1.setSubGroupName("03 变速箱");
		p1.setGroupName("03 变速箱");
		p1.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		p1.setPid("p1001");
		p1.setRealPid("p1001");
		p1.setImgUrl("1https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUkSiQlSic3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUkNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTckJUonN2g%3D&w=160&h=160");
		p1.setItId("11");
		p1.setNum("011");
		list.add(p1);

		ZeroSubGroupPartsDto p2 = new ZeroSubGroupPartsDto();
		p2.setcId(1793);
		p2.setItId("12");
		p2.setNum("012");
		p2.setBrand("maserati");
		p2.setCarModel("3.0 BT SOFT V6 2WD 330 HP");
		p2.setGroupId(12);
		p2.setSubGroup("02_1");
		p2.setSubGroupId(12);
		p2.setSubGroupName("03_10_2 变速箱外壳");
		p2.setImgUrl("https://data.007vin.com/ppyimage/tmbnail?code=maserati&key=bjd2enFwNy81NyQlJj5MJyZKJyUkI0okJEohSicnIjc5NTdgfHE3LzU3OCAmIiYnIiIgJSUjLSYmJy0nIi03OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUkSiQlSic3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUkNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTckJUonN2g%3D&w=160&h=160");
		p2.setGroupImg("https://007vin.com/stcimgs/img/9a829877e5efb12a87d19689420f942a.jpg");
		p2.setGroupName("03 变速箱");
		p2.setPid("p1001");
		p2.setRealPid("p1001");
		list.add(p2);

		zeroDataCollectService.saveSubGroupParts(list);
	}

	@Test
	public void savePartInfosTest(){
		List<ZeroPartInfoDto> list = Lists.newArrayList();
		ZeroPartInfoDto p1 = new ZeroPartInfoDto();
		p1.setBrand("maserati");

		p1.setPid("p1001");
		p1.setPidLabel("原厂配件名1");
		p1.setPidModel("3.0 BT SOFT V6 2WD 330 HP1");
		p1.setPidRemark("pid remark1");
		p1.setRealPid("p1001");
		list.add(p1);

		ZeroPartInfoDto p2 = new ZeroPartInfoDto();
		p2.setBrand("maserati");
		p2.setPid("p1002");
		p2.setPidLabel("原厂配件名2");
		p2.setPidModel("3.0 BT SOFT V6 2WD 330 HP2");
		p2.setPidRemark("pid remark2");
		p2.setRealPid("p1002");
		list.add(p2);

		zeroDataCollectService.savePartInfos(list);
	}

	@Test
	public void savePartPricesTest(){
		List<ZeroPartPriceDto> list = Lists.newArrayList();
		ZeroPartPriceDto p1 = new ZeroPartPriceDto();
		p1.setBrand("maserati");
		p1.setPid("p1001");
		p1.setAmount("999");
		p1.setChannel("X1");
		p1.setCostPrice("999");
		p1.setEotPrice("999");
		p1.setFactoryType("999");
		p1.setLocation("999");
		p1.setOrigin("原厂");
		p1.setPartType("999");
		p1.setPrice("999");
		p1.setRemark("999");
		p1.setSupplier("999");
		list.add(p1);
		ZeroPartPriceDto p2 = new ZeroPartPriceDto();
		p2.setBrand("maserati");
		p2.setPid("p1002");
		p2.setAmount("888");
		p2.setChannel("X2");
		p2.setCostPrice("888");
		p2.setEotPrice("888");
		p2.setFactoryType("888");
		p2.setLocation("888");
		p2.setOrigin("副厂");
		p2.setPartType("888");
		p2.setPrice("888");
		p2.setRemark("888");
		p2.setSupplier("888");
		list.add(p2);

		zeroDataCollectService.savePartPrices(list);
	}

	@Test
	public void saveAdapterCarModelsTest(){
		List<ZeroAdapterCarModelDto> list = Lists.newArrayList();
		ZeroAdapterCarModelDto a1 = new ZeroAdapterCarModelDto();
		a1.setBrand("maserati");
		a1.setPid("p1001");
		a1.setCarModel("carMOdel1");
		a1.setGroupName("零件组aa");
		a1.setMainGroupName("主组aa");
		a1.setYear("2015aa");
		a1.setMarket("aa");
		list.add(a1);
		ZeroAdapterCarModelDto a2 = new ZeroAdapterCarModelDto();
		a2.setBrand("maserati");
		a2.setPid("p1001");
		a2.setCarModel("carMOdel2");
		a2.setGroupName("零件组bb");
		a2.setMainGroupName("主组bb");
		a2.setYear("2015bb");
		a2.setMarket("bb");
		list.add(a2);

		zeroDataCollectService.saveAdapterCarModels(list);
	}

}
