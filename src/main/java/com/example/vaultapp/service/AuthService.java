package com.example.vaultapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vaultapp.config.JwtUtil;
import com.example.vaultapp.entity.User;
import com.example.vaultapp.repository.UserRepository;

@Service
public class AuthService {
	
	 @Autowired
	    private UserRepository repo;

	    public User register(User user) {
	        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	        user.setPin(new BCryptPasswordEncoder().encode(user.getPin()));
	        return repo.save(user);
	    }

	    public Map<String, Object> login(String username, String password) {
	        User user = repo.findByUsername(username).orElseThrow();

	        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
	            throw new RuntimeException("Invalid password");
	        }

	        String token =
	        	    JwtUtil.generateToken(
	        	        user.getUsername()
	        	    );

	        	Map<String, Object> response =
	        	    new HashMap<>();

	        	response.put("token", token);
	        	response.put("id", user.getId());
	        	response.put(
	        	    "username",
	        	    user.getUsername()
	        	);

	        	return response;
	    }

}
