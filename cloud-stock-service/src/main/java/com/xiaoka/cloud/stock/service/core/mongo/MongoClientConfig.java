package com.xiaoka.cloud.stock.service.core.mongo;

import com.google.common.collect.Lists;
import com.mongodb.MongoCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/**
 * mongo client初始化工厂类
 *
 * @author gancao 2017/11/06 15:25
 * @see [相关类/方法]
 */
@Configuration
public class MongoClientConfig {

	@Value("${env.mongodb.ddyc.username}")
	private String username;

	@Value("${env.mongodb.ddyc.password}")
	private String password;

	@Value("${env.mongodb.ddyc.host}")
	private String replicaSetSeeds;

	@Value("${env.mongodb.ddyc.db}")
	private String dbName;

	@Bean(name = "mongoClient")
	public MongoClientFactoryBean getConfig() {
		MongoCredential c = MongoCredential.createScramSha1Credential(username, dbName, password.toCharArray());
		MongoClientFactoryBean b = new MongoClientFactoryBean();
		b.setStrReplicaSetSeeds(replicaSetSeeds);
		List<MongoCredential> credentials = Lists.newArrayList(c);
		b.setCredentials(credentials);
		return b;
	}
}
