package com.example.vaultapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vaultapp.entity.User;
import com.example.vaultapp.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("/register")
	public ResponseEntity<?> register(
	        @RequestBody User user
	) {

	    try {

	        return ResponseEntity.ok(
	        		service.register(user)
	        );

	    } catch (Exception e) {

	        return ResponseEntity
	                .badRequest()
	                .body(
	                        Map.of(
	                                "message",
	                                e.getMessage()
	                        )
	                );

	    }

	}

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> req) {
		return service.login(req.get("username"), req.get("password"));
	}
}
