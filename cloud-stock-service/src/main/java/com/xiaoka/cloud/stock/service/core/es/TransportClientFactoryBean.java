/*
 * Copyright (C), 2014-2014, 杭州小卡科技有限公司
 * FileName: TransportClientFactoryBean.java
 * Author:   sabo
 * Date:     Dec 9, 2014 4:13:56 PM
 * Description: 
 */
package com.xiaoka.cloud.stock.service.core.es;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.net.InetAddress;

/**
 * es客户端
 *
 * @author gancao 2018/1/8 15:37
 * @see [相关类/方法]
 * @since [版本号]
 */
public class TransportClientFactoryBean implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(TransportClientFactoryBean.class);
	private String clusterNodes = "127.0.0.1:9300";
	private String clusterName;
	private Boolean clientIgnoreClusterName = Boolean.FALSE;
	private String clientPingTimeout = "5s";
	private TransportClient client;
	private static final String COLON = ":";
	private static final String COMMA = ",";

	@Override
	public void destroy() throws Exception {
		try {
			logger.info("Closing elasticSearch  client");
			if (client != null) {
				client.close();
			}
		} catch (final Exception e) {
			logger.error("Error closing ElasticSearch client: ", e);
		}
	}

	@Override
	public TransportClient getObject() throws Exception {
		return client;
	}

	@Override
	public Class<TransportClient> getObjectType() {
		return TransportClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isBlank(clusterName)){
			throw new IllegalArgumentException("clusterName should not be null");
		}
		buildClient();
	}

	private void buildClient() throws Exception {
		client = new PreBuiltTransportClient(settings());
		Assert.hasText(clusterNodes, "[Assertion failed] clusterNodes settings missing.");
		for (String clusterNode : StringUtils.split(clusterNodes, COMMA)) {
			String hostName = StringUtils.substringBefore(clusterNode, COLON);
			String port = StringUtils.substringAfter(clusterNode, COLON);
			Assert.hasText(hostName, "[Assertion failed] missing host name in 'clusterNodes'");
			Assert.hasText(port, "[Assertion failed] missing port in 'clusterNodes'");
			logger.info("adding transport node : " + clusterNode);
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.parseInt(port)));
		}
		client.connectedNodes();
	}

	private Settings settings() {
		return Settings.builder()
		               .put("cluster.name", clusterName)
		               .put("client.transport.sniff", true)
		               .build();
	}

	public void setClusterNodes(String clusterNodes) {
		this.clusterNodes = clusterNodes;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getClientPingTimeout() {
		return clientPingTimeout;
	}

	public void setClientPingTimeout(String clientPingTimeout) {
		this.clientPingTimeout = clientPingTimeout;
	}

	public Boolean getClientIgnoreClusterName() {
		return clientIgnoreClusterName;
	}

	public void setClientIgnoreClusterName(Boolean clientIgnoreClusterName) {
		this.clientIgnoreClusterName = clientIgnoreClusterName;
	}

}