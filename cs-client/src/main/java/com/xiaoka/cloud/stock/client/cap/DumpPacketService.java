package com.xiaoka.cloud.stock.client.cap;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhouze
 * @date 2018/1/9
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class DumpPacketService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DumpPacketService.class);

	private static final String COUNT_KEY = DumpPacketService.class.getName() + ".count";
	private static final int    COUNT     = Integer.getInteger(COUNT_KEY, Integer.MAX_VALUE);

	private static final String READ_TIMEOUT_KEY = DumpPacketService.class.getName() + ".readTimeout";
	private static final int    READ_TIMEOUT     = Integer.getInteger(READ_TIMEOUT_KEY, 10);

	private static final String SNAPLEN_KEY = DumpPacketService.class.getName() + ".snaplen";
	private static final int    SNAPLEN     = Integer.getInteger(SNAPLEN_KEY, 65536);

	private static final String PCAP_FILE_KEY = "dumpPacket.pcapFile";
	private static final String PCAP_FILE     = System.getProperty(PCAP_FILE_KEY, "cs-client/target/packets.pcap");

	private static final String  TIMESTAMP_PRECISION_NANO_KEY = DumpPacketService.class.getName() + ".timestampPrecision.nano";
	private static final boolean TIMESTAMP_PRECISION_NANO     = Boolean.getBoolean(TIMESTAMP_PRECISION_NANO_KEY);


	public void dumpPackets(String filter) throws PcapNativeException {
		List<PcapNetworkInterface> alldev = Pcaps.findAllDevs();
		if (CollectionUtils.isEmpty(alldev)) {
			return;
		}

		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
				.setNameFormat("cs-dump-packet-client-%d").build();

		//Common Thread Pool
		ExecutorService pool = new ThreadPoolExecutor(alldev.size(), alldev.size(),
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1024),
				namedThreadFactory);

		alldev.forEach(dev -> {
			pool.execute(() -> {
				try {
					dumpTask(dev, filter);
				} catch (Exception e) {
					LOGGER.error("dump出现错误,{},{}", dev.getName(), dev.getAddresses(), e);
				}
			});
		});

		pool.shutdown();
	}

	private void dumpTask(PcapNetworkInterface nif, String filter) throws PcapNativeException, NotOpenException {
		if (nif == null) {
			return;
		}

		PcapHandle.Builder phb = new PcapHandle.Builder(nif.getName())
				.snaplen(SNAPLEN)
				.promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS)
				.timeoutMillis(READ_TIMEOUT);
		if (TIMESTAMP_PRECISION_NANO) {
			phb.timestampPrecision(PcapHandle.TimestampPrecision.NANO);
		}
		PcapHandle handle = phb.build();

		if (StringUtils.isNotBlank(filter)) {
			handle.setFilter(
					filter, BpfProgram.BpfCompileMode.OPTIMIZE
			);
		}

		int        num    = 0;
		String     path   = PCAP_FILE.concat("_").concat(nif.getName().replace("\\", "").replace("/", ""));
		PcapDumper dumper = handle.dumpOpen(path);
		try {
			while (true) {
				Packet packet = handle.getNextPacket();
				if (packet != null) {
					dumper.dump(packet, handle.getTimestamp());
					num++;
					if (num >= COUNT) {
						num = 0;
					}
				}
			}
		} finally {
			dumper.close();
			handle.close();
		}
	}

}
