package com.bestarch.demo.segmentation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.UnifiedJedis;

@Service
public class UserSegmentationService {
	
	@Autowired
	UnifiedJedis unifiedJedis;

	public void createTestSegments() {
		// TODO Auto-generated method stub
		
	}

	public void createTestUsers() {
		// TODO Auto-generated method stub
		
	}

	public void mapUserToSegment(String segment, String username) {
		// TODO Auto-generated method stub
		
	}

	public Boolean isUserExists(String segment, String username) {
		// TODO Auto-generated method stub
		return false;
	}

	public Boolean removeUserFromSegment(String segment, String username) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
