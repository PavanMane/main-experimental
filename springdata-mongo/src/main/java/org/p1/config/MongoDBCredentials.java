package org.p1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix="p1.mongodb")
public class MongoDBCredentials {

	private String host;
	
	private String port;
	
	private String databaseName;

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
}
