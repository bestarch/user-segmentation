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
@RequestMapping("/segment")
public class SegmentWebController {

	@Autowired
	UserSegmentationService service;

	@PostMapping("/")
	public ResponseEntity<?> addTestSegments() {
		service.createTestSegments();
		return ResponseEntity.accepted().build();
	}

	@PostMapping("/users")
	public ResponseEntity<?> addTestUsersToSegment() {
		service.addTestUsersToSegment();
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/{segment}/user/{username}")
	public ResponseEntity<?> mapUserToSegment(@PathVariable String segment, @PathVariable String username) {
		Boolean res = service.mapUserToSegment(segment, username);
		return ResponseEntity.ok(res);
	}

	@GetMapping("/{segment}/user/{username}")
	public ResponseEntity<?> isUserExists(@PathVariable String segment, @PathVariable String username) {
		Boolean res = service.isUserExists(segment, username);
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{segment}/user/{username}")
	public ResponseEntity<?> removeUserFromSegment(@PathVariable String segment, @PathVariable String username) {
		Boolean res = service.removeUserFromSegment(segment, username);
		return ResponseEntity.ok(res);
	}

}