package com.bestarch.demo.segmentation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bestarch.demo.segmentation.service.UserSegmentationService;

@RestController
@RequestMapping("/")
public class SegmentWebController {

	@Autowired
	UserSegmentationService service;

	@PostMapping("segments")
	public ResponseEntity<?> addTestSegments() {
		service.createTestSegments();
		return ResponseEntity.accepted().build();
	}

	@PostMapping("users")
	public ResponseEntity<?> addTestUsers() {
		service.createTestUsers();
		return ResponseEntity.accepted().build();
	}

	@PutMapping("segment/{segment}/user/{username}")
	public ResponseEntity<?> mapUserToSegment(@PathVariable String segment, @PathVariable String username) {
		service.mapUserToSegment(segment, username);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("segment/{segment}/user/{username}")
	public ResponseEntity<?> isUserExists(@PathVariable String segment, @PathVariable String username) {
		Boolean res = service.isUserExists(segment, username);
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("segment/{segment}/user/{username}")
	public ResponseEntity<?> removeUserFromSegment(@PathVariable String segment, @PathVariable String username) {
		Boolean res = service.removeUserFromSegment(segment, username);
		return ResponseEntity.ok(res);
	}

}