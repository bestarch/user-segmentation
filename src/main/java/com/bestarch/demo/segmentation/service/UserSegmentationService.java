package com.bestarch.demo.segmentation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.UnifiedJedis;

@Service
public class UserSegmentationService {
	
	@Autowired
	UnifiedJedis unifiedJedis;
	
	private static String[] SEGMENTS = {"newdelhi_millenials_watched_cricket","","","",""};

	public void createTestSegments() {
		for (String segment: SEGMENTS) {
			unifiedJedis.cfReserve(segment, 30000000l);
		}
	}

	public void createTestUsers() {
		IntStream.rangeClosed(1, 30000000).forEach(index -> {
			String username = "user:"+index;
			unifiedJedis.hset(username, Map.of("username", username));
		});
	}

	public Boolean mapUserToSegment(String segment, String username) {
		return unifiedJedis.cfAdd(segment, username);
		
	}

	public Boolean isUserExists(String segment, String username) {
		Boolean res = unifiedJedis.cfExists(segment, username);
		return res;
	}

	public Boolean removeUserFromSegment(String segment, String username) {
		Boolean res = unifiedJedis.cfDel(segment, username);
		return res;
	}
	
	

}
