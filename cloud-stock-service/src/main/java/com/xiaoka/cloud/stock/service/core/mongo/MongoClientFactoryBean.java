/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: MongoClientFactoryBean.java
 * Author:   sabo
 * Date:     Apr 4, 2015 7:20:08 AM
 * Description: 
 */
package com.xiaoka.cloud.stock.service.core.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.util.CollectionUtils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * mongo client对象
 *
 * @author sabo
 */
public class MongoClientFactoryBean extends AbstractFactoryBean<MongoClient> implements PersistenceExceptionTranslator{
	
	private MongoClientOptions mongoClientOptions;
	private String host;
	private Integer port;
	private String strReplicaSetSeeds;
	private List<MongoCredential> credentials;
	
	private static final String COLON = ":";
	private static final String COMMA = ",";
	

	public MongoClientOptions getMongoClientOptions() {
		return mongoClientOptions;
	}

	public void setMongoClientOptions(MongoClientOptions mongoClientOptions) {
		this.mongoClientOptions = mongoClientOptions;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public List<MongoCredential> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<MongoCredential> credentials) {
		this.credentials = credentials;
	}

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
	    // TODO Auto-generated method stub
	    return null;
    }

    @Override
    public Class<? extends MongoClient> getObjectType() {
	    return MongoClient.class;
    }

	@Override
    protected void destroyInstance(MongoClient instance) throws Exception {
	    instance.close();
    }

    @Override
    protected MongoClient createInstance() throws Exception {
    	if(mongoClientOptions == null){
    		mongoClientOptions = MongoClientOptions.builder().build();
    	}
    	if(credentials == null){
    		credentials = Collections.emptyList();
    	}
	    return createMongoClient();
    }
    
    private MongoClient createMongoClient() throws UnknownHostException {
    	if(StringUtils.isNoneBlank(strReplicaSetSeeds)){
    		List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
    		for(String strReplicaSetSeed : strReplicaSetSeeds.split(COMMA)){
    			String hostName = StringUtils.substringBefore(strReplicaSetSeed, COLON);
    			String port = StringUtils.substringAfter(strReplicaSetSeed, COLON);
    			replicaSetSeeds.add(new ServerAddress(hostName, Integer.parseInt(port)));
    		}
    		if (!CollectionUtils.isEmpty(replicaSetSeeds)) {
    			return new MongoClient(replicaSetSeeds, credentials, mongoClientOptions);
    		}
    	}
    	return new MongoClient(createConfiguredOrDefaultServerAddress(), credentials, mongoClientOptions);
    }
    
    private ServerAddress createConfiguredOrDefaultServerAddress() throws UnknownHostException {

		ServerAddress defaultAddress = new ServerAddress();

		return new ServerAddress(StringUtils.isNotBlank(host) ? host : defaultAddress.getHost(),
				port != null ? port.intValue() : defaultAddress.getPort());
	}

	public String getStrReplicaSetSeeds() {
		return strReplicaSetSeeds;
	}

	public void setStrReplicaSetSeeds(String strReplicaSetSeeds) {
		this.strReplicaSetSeeds = strReplicaSetSeeds;
	}
    
    

}
