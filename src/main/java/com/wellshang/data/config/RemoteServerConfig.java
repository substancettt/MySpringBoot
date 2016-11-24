package com.wellshang.data.config;

import java.lang.reflect.Method;
import java.net.InetAddress;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "connection")
public class RemoteServerConfig {

	private static final Logger LOG = LoggerFactory
			.getLogger(RemoteServerConfig.class);

	private String username;

	private InetAddress remoteAddress;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRemoteAddress(InetAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@Bean
	public KeyGenerator myKeyGenerator() {
		return new KeyGenerator() {

			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	@PostConstruct
	public void xxx() {
		LOG.info("remoteServer initialized username:{}, remoteAddress:{}.",
				username, remoteAddress);
	}

	public String showServerInfo() {
		LOG.info("Http requst comes, showServerInfo called...");
		return "Niu bi !";
	}

}
