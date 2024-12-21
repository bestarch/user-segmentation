package com.bestarch.demo.segmentation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.HostAndPortMapper;
import redis.clients.jedis.UnifiedJedis;

@Configuration
public class RedisConfig_Simple {

	/**
	 * Redis Configuration
	 */
	@Value("${redis.url:localhost}")
	private String url;

	@Value("${redis.port:6379}")
	private Integer port;

	@Value("${redis.password:admin}")
	private String password;
	

	@Bean
    public UnifiedJedis unifiedJedis() {
		HostAndPortMapper hp =  hostAndPort -> {
            return new HostAndPort(url, port);
        };
        UnifiedJedis jedis = new UnifiedJedis("redis://default:"+password+'@'+url+":"+port);
        return jedis;
    }

}
