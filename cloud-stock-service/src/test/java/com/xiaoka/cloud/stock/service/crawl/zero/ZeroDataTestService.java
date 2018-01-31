package com.xiaoka.cloud.stock.service.crawl.zero;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelAccountEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarSubGroupChooseEntity;
import com.xiaoka.cloud.stock.service.core.util.HttpUtil;
import com.xiaoka.cloud.stock.service.crawl.core.processor.ZeroSevenProcessor;
import com.xiaoka.cloud.stock.service.crawl.core.util.ZeroSevenLoginUtil;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto.AccountMsgDto;
import com.xiaoka.freework.container.test.ContainerTest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Objects;

import static com.xiaoka.cloud.stock.service.crawl.core.util.ZeroSevenLoginUtil.singleInstance;

/**
 * Do something
 *
 * @author gancao 2017/12/18 10:40
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
		"classpath*:spring/tool-service.xml"
})
public class ZeroDataTestService extends ContainerTest {

	@Resource
	private ZeroSevenProcessor zeroSevenProcessor;
	@Resource
	private ZeroChooseService zeroChooseService;

	@Test
	public void success() {
		boolean s = zeroChooseService.isComplete(1792);
		System.out.println(s);
	}

	@Test
	public void zeroData() {
		ZeroAccountEntity entity = new ZeroAccountEntity();
		entity.setPhone("15088715024");
		entity.setPassword("715024");
		entity.setIp("178.49.136.84");
		entity.setPort(8080);
		zeroSevenProcessor.init(entity, singleInstance().getHeaders(false, entity), null);

		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(entity.getIp(), entity.getPort())));
		Spider spider = Spider.create(zeroSevenProcessor);
		spider.setDownloader(httpClientDownloader);
		spider.addUrl("https://www.007vin.com/cars/show?brand=maserati");
		spider.thread(1);
		spider.run();
	}

	@Test
	public void clean() {
		zeroChooseService.cleanCrawlData();
	}

	@Test
	public void test() {
		ZeroCarModelAccountEntity entity = new ZeroCarModelAccountEntity();
		entity.setBrand("玛莎拉蒂");
		entity.setCarModel("Coupé");
		entity.setMarket("AUSTRALIA");
		entity.setYear("2006");
		entity.setEngine("4200cc");
		entity.setGearBox("CAMBIOCORSA");
		String phone = zeroChooseService.getAccessPhone(entity);
		System.out.println(phone);
	}

	public static void main(String[] args) {
		String ip = "113.128.9.176:3937";
		String[] address = ip.split(",");
		for (int i = 0; i < 2; i++) {
			for (String str : address) {
				str = StringUtils.deleteWhitespace(str);
				System.out.println(str);
				String[] result = str.split(":");
				ZeroAccountEntity entity = new ZeroAccountEntity();
				entity.setPhone("15655461518");
				entity.setPassword("461518");
				entity.setIp(result[0]);
				entity.setPort(Integer.valueOf(result[1]));
				Header[] headers = ZeroSevenLoginUtil.singleInstance().getHeaders(false, entity);
				if (Objects.nonNull(headers)) {
					AccountMsgDto accountMsgDto = new AccountMsgDto();
					accountMsgDto.setZeroAccountEntity(entity);
					accountMsgDto.setCookieMap(ZeroSevenLoginUtil.getCookiesMap(headers));
					try {
						String respString = HttpUtil.doRespString("https://www.007vin.com/ppyvin/searchvins?vin=ZAMSS57E8E1103195&brands=all", accountMsgDto, null);
						System.out.println(respString);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Test
	public void batchInsertZeroCarSubGroup() {
		List<ZeroCarSubGroupChooseEntity> zeroCarSubGroupChooseEntityList = Lists.newArrayList();
		ZeroCarSubGroupChooseEntity entity = new ZeroCarSubGroupChooseEntity();
		entity.setInnerGroupId(31);
		entity.setInnerSubGroupId(1452);
		entity.setGroupId(8992);
		entity.setGroupName("09 车身");
		entity.setAuth(
				"bjd2enFwNy81NyQlJj5MJyZKJyUkIEokJEohSicnIjc5NTdgfHE3LzU3OCAlLSElISwiIiwnIyYnIiYlJCI3OTU3d2d0e3E3LzU3eHRmcGd0YXw3OTU3eHxxNy81NyUsSiIkSiQ3OTU3dmFsZXA3LzU3diUlJjc5NTd8ZmZgd2Y3LzUlOTU3e2B4Ny81NyUsNzk1N3xmSnN8eWFwZzcvNSU5NTdmYHdyZ3pgZTcvNTciJEokN2g%3D");
		zeroCarSubGroupChooseEntityList.add(entity);
		zeroChooseService.batchInsertZeroCarSubGroup(zeroCarSubGroupChooseEntityList);
	}

	private static boolean isValid(String ip) {
		boolean isIpReachable = false;
		InetAddress address;
		try {
			address = InetAddress.getByName(ip);
			isIpReachable = address.isReachable(3000);
			System.out.println("Name: " + address.getHostName());

			System.out.println("Addr: " + address.getHostAddress());

			System.out.println("isIpReachable: " + isIpReachable);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isIpReachable;
	}

}
