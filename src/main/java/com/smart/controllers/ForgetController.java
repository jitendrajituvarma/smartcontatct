package com.smart.controllers;

import java.util.Random;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgetController {

	@GetMapping("/forgot")
	public String openEmailForm() {
		
		return "forgot_email_form";
	}
	
	@PostMapping("/sendOtp")
	public String sendOpt(@RequestParam("email") String email) {
		Random random=new Random(1000);
		int otp=random.nextInt(9999);
		System.out.println("OTP"+otp);
		//write the code to email for otp
		//commented
		return "veryfryOTP";
	}
}
