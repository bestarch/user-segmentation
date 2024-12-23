package com.bestarch.demo.segmentation.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	
	public void addTestUsersToSegment2() {
		try {
			AtomicInteger window = new AtomicInteger(2);
			IntStream.rangeClosed(1, 2).forEach(index -> {
				String username = "user:"+index;
				long before = System.currentTimeMillis();
				unifiedJedis.pipelined();
				unifiedJedis.pipelined().cfAdd(SEGMENTS[0], username);
				if (index >= window.get()) {
					unifiedJedis.pipelined().sync();
					window.set(window.get()+2);
				}
				long after = System.currentTimeMillis();
				logger.info("Time taken to execute: {} msec", (after-before));
			});
			unifiedJedis.pipelined().sync();
		} catch (Exception e) {
			logger.error("An error occurred", e);
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Adding test users to 3 segments
	 */
	public void addTestUsersToSegment() {
		long before = System.currentTimeMillis();
		try {
			AtomicInteger window = new AtomicInteger(10000);
			IntStream.rangeClosed(1, 30000000).forEach(index -> {
				String username = "user:"+index;
				unifiedJedis.pipelined();
				unifiedJedis.pipelined().cfAdd(SEGMENTS[0], username);
				if (index >= window.get()) {
					unifiedJedis.pipelined().sync();
					window.set(window.get()+10000);
				}
			});
			unifiedJedis.pipelined().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long after = System.currentTimeMillis();
		logger.info("1. Time taken to execute: {} msec", (after-before));
		
		before = System.currentTimeMillis();
		try {
			AtomicInteger window = new AtomicInteger(1000);
			IntStream.rangeClosed(2, 10000).forEach(index -> {
				if (index % 2 == 0) {
					String username = "user:"+index;
					unifiedJedis.pipelined();
					unifiedJedis.pipelined().cfAdd(SEGMENTS[1], username);
					if (index >= window.get()) {
						unifiedJedis.pipelined().sync();
						window.set(window.get()+1000);
					}
				}
			});
			unifiedJedis.pipelined().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
		after = System.currentTimeMillis();
		logger.info("2. Time taken to execute: {} msec", (after-before));
		
		before = System.currentTimeMillis();
		try {
			AtomicInteger window = new AtomicInteger(1010000);
			IntStream.rangeClosed(1000000, 2000000).forEach(index -> {
				String username = "user:"+index;
				unifiedJedis.pipelined();
				unifiedJedis.pipelined().cfAdd(SEGMENTS[2], username);
				if (index >= window.get()) {
					unifiedJedis.pipelined().sync();
					window.set(window.get()+10000);
				}
			});
			unifiedJedis.pipelined().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
		after = System.currentTimeMillis();
		logger.info("3. Time taken to execute: {} msec", (after-before));
		
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
