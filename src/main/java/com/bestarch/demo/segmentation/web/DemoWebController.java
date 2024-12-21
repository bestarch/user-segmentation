package com.bestarch.demo.segmentation.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestarch.demo.segmentation.service.UserSegmentationService;

import redis.clients.jedis.UnifiedJedis;

@RestController
@RequestMapping("/demo")
public class DemoWebController {
	
	@Autowired
	UserSegmentationService service;
	
	@Autowired
	UnifiedJedis unifiedJedis;

	@PostMapping("/add")
	public ResponseEntity<?> addKeys() {
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getKeys() {
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/reset")
	public ResponseEntity<?> resetRedisFailure() {
		return ResponseEntity.accepted().build();
	}

}