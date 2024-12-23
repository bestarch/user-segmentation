package com.bestarch.demo.segmentation.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.AbstractPipeline;
import redis.clients.jedis.UnifiedJedis;

@Service
public class UserSegmentationService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UnifiedJedis unifiedJedis;
	
	private static String[] SEGMENTS = {"newdelhi_millenials_watched_cricket","mumbai_users_watched_concert","mumbai_users_watched_scifi_movies","kolkata_users_watched_cricket","bangalore_users_watched_drama_movies"};

	public void createTestSegments() {
		for (String segment: SEGMENTS) {
			unifiedJedis.cfReserve(segment, 30000000l);
		}
	}

	
	/**
	 * Adding test users to segments
	 */
	public void addTestUsersToSegment() {
		long before = System.currentTimeMillis();
		
		try {
			AbstractPipeline pipeline = unifiedJedis.pipelined();
			IntStream.rangeClosed(1, 2000).forEach(index -> {
				String username = "user:"+index;
				pipeline.cfAdd(SEGMENTS[0], username);
			});
			pipeline.sync();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("1. An error occurred while creating test records", e);
		}
		
		long after = System.currentTimeMillis();
		logger.info("1. Time taken to execute: {} msec", (after-before));
		
		before = System.currentTimeMillis();
		try {
			AbstractPipeline pipeline = unifiedJedis.pipelined();
			IntStream.rangeClosed(200, 400).forEach(index -> {
				if (index % 2 == 0) {
					String username = "user:"+index;
					pipeline.cfAdd(SEGMENTS[1], username);
				}
			});
			pipeline.sync();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("1. An error occurred while creating test records", e);
		}
		after = System.currentTimeMillis();
		logger.info("2. Time taken to execute: {} msec", (after-before));
		
		/*before = System.currentTimeMillis();
		try {
			AbstractPipeline pipeline = unifiedJedis.pipelined();
			AtomicInteger window = new AtomicInteger(1010000);
			IntStream.rangeClosed(1000000, 2000000).forEach(index -> {
				String username = "user:"+index;
				pipeline.cfAdd(SEGMENTS[2], username);
				if (index >= window.get()) {
					pipeline.sync();
					window.set(window.get()+10000);
				}
			});
			pipeline.sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
		after = System.currentTimeMillis();
		logger.info("3. Time taken to execute: {} msec", (after-before));
		*/
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
