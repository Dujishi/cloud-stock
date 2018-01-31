package com.xiaoka.cloud.stock.client.cap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xiaoka.cloud.stock.client.cap.dto.ErpUserAuthDto;

import org.apache.commons.lang3.StringUtils;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.EOFException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author zhouze
 * @date 2018/1/9
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ReadPacketService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DumpPacketService.class);

	private static final int COUNT = Integer.MAX_VALUE;

	private static final String PCAP_FILE_KEY = "dumpPacket.pcapFile";
	private static final String PCAP_FILE     = System.getProperty(PCAP_FILE_KEY, "cs-client/target/packets.pcap");
	//private static final String PCAP_FILE     = System.getProperty(PCAP_FILE_KEY, "packets.pcap");

	public ErpUserAuthDto readPacket() throws PcapNativeException {
		List<PcapNetworkInterface> alldev = Pcaps.findAllDevs();
		if (CollectionUtils.isEmpty(alldev)) {
			return null;
		}


		List<String> paths = Lists.newArrayList();
		alldev.forEach(dev -> {
			try {
				String path = PCAP_FILE.concat("_").concat(dev.getName().replace("\\", "").replace("/", ""));
				paths.add(path);
			} catch (Exception e) {
				LOGGER.error("dump出现错误,{},{}", dev.getName(), dev.getAddresses(), e);
			}
		});

		ErpUserAuthDto erpUserAuthDto = new ErpUserAuthDto();
		for (String path : paths) {
			PcapHandle handle;
			try {
				handle = Pcaps.openOffline(path, PcapHandle.TimestampPrecision.NANO);
			} catch (PcapNativeException e) {
				handle = Pcaps.openOffline(path);
			}
			for (int i = 0; i < COUNT; i++) {
				try {
					Packet packet= handle.getNextPacketEx();
					if (null != packet.getPayload() && null != packet.getPayload().getPayload()) {
						byte[] data = packet.getPayload().getPayload().getRawData();
						String some = bytesToAscii(data, 0, data.length);
						if (some.contains("HTTP/1.1")) {
							if (!some.contains("Cookie")) {
								continue;
							}
							String[] lists = some.trim().split("\r\n");
							for (String list : lists) {
								if (list.contains("Cookie") && !list.contains("Set-Cookie") && some.contains("/HzryERP/2016/ShowMenu")) {
									erpUserAuthDto.setCookies(list.substring(list.indexOf(":") + 1).trim());
								}
								if (list.contains("KsPid=") && list.contains("GET") && list.contains("HTTP")) {
									try {
										String url = list.substring(list.indexOf("GET") + 3, list.indexOf("HTTP"));
										if (StringUtils.isNotBlank(url)) {
											Map<String, String> params = urlParser(url);
											if (null != params && params.size() > 0 && null != params.get("kspid") && StringUtils.isNotBlank(params.get("kspid"))) {
												erpUserAuthDto.setSpecialId(params.get("kspid"));
											}
										}
									} catch (Exception e) {
									}
								}
							}
						}
					}

				} catch (TimeoutException | NotOpenException|IllegalArgumentException  ignored ) {
				} catch (EOFException e) {
					LOGGER.error("EOF");
					break;
				}catch (Exception e){
				}
			}

			handle.close();
		}

		return erpUserAuthDto;
	}

	private static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
		if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
			return null;
		}
		if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
			return null;
		}

		String asciiStr = null;
		byte[] data     = new byte[dateLen];
		System.arraycopy(bytes, offset, data, 0, dateLen);
		try {
			asciiStr = new String(data, "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
		}
		return asciiStr;
	}

	public static Map<String, String> urlParser(String URL) {
		Map<String, String> mapRequest = Maps.newHashMap();

		String[] arrSplit;

		String strUrlParam = truncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual;
			arrSplitEqual = strSplit.split("[=]");
			if (arrSplitEqual.length > 1) {
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			} else {
				if (arrSplitEqual[0] != "") {
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	private static String truncateUrlPage(String strURL) {
		String   strAllParam = null;
		String[] arrSplit;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

}
