package com.example.vaultapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	public User register(@RequestBody User user) {
		return service.register(user);
	}

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> req) {
		return service.login(req.get("username"), req.get("password"));
	}
}
