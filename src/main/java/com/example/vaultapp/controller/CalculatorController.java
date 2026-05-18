package com.example.vaultapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vaultapp.entity.User;
import com.example.vaultapp.repository.UserRepository;

@RestController
@RequestMapping("/calc")
public class CalculatorController {
	@Autowired
	private UserRepository repo;

	/*
	 * @PostMapping("/check") public Map<String, Object> calculate(@RequestBody
	 * Map<String, String> req) {
	 * 
	 * int a = Integer.parseInt(req.get("a")); int b =
	 * Integer.parseInt(req.get("b")); int result = a + b;
	 * 
	 * int pin = Integer.parseInt(req.get("pin"));
	 * 
	 * Map<String, Object> response = new HashMap<>();
	 * 
	 * if (result == pin) { response.put("vault", true); } else {
	 * response.put("vault", false); }
	 * 
	 * response.put("result", result);
	 * 
	 * return response; }
	 */

	@PostMapping("/check")
	public Map<String, Object> check(
	        @RequestBody Map<String, String> req
	) {

	    Map<String, Object> response =
	            new HashMap<>();

	    try {

	        // 🔥 GET VALUES
	        String userIdStr =
	                req.get("userId");

	        String result =
	                req.get("result");

	        // 🔥 VALIDATION
	        if (userIdStr == null ||
	            result == null) {

	            response.put(
	                "vault",
	                false
	            );

	            response.put(
	                "message",
	                "Missing fields"
	            );

	            return response;
	        }

	        Long userId =
	                Long.parseLong(userIdStr);

	        // 🔥 FIND USER
	        User user =
	                repo.findById(userId)
	                    .orElseThrow();

	        // 🔥 CHECK PIN
	        boolean vault =
	            new BCryptPasswordEncoder()
	                .matches(
	                    result,
	                    user.getPin()
	                );

	        response.put(
	            "result",
	            result
	        );

	        response.put(
	            "vault",
	            vault
	        );

	        return response;

	    } catch (Exception e) {

	        e.printStackTrace();

	        response.put(
	            "vault",
	            false
	        );

	        response.put(
	            "message",
	            "Invalid request"
	        );

	        return response;
	    }
	}

}
