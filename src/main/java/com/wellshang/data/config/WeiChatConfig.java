package com.wellshang.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="weiChat")
public class WeiChatConfig {

	private static final Logger LOG = LoggerFactory
			.getLogger(TaskExecutorConfig.class);

	public String tocken;
	public String apiKey;
	public String tuLingApiKey;

	public void setTocken(String tocken) {
		this.tocken = tocken;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setTuLingApiKey(String tuLingApiKey) {
		this.tuLingApiKey = tuLingApiKey;
	}
	
	public void showConfig() {
		LOG.error("WChatConfig: tocken:{}, apiKey:{}, tuLingApiKey:{}", tocken, apiKey, tuLingApiKey);
	}
	
}
