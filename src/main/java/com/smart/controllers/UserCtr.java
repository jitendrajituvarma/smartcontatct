package com.smart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.UserRepository;
import com.smart.entity.User;

@RestController
public class UserCtr {
	@Autowired
	UserRepository repository;

	@GetMapping("/index/{email}")
	public User getUser(@PathVariable("email") String email) {
		System.out.println("HELLO wordlg");
		return repository.getUserByUserName(email);
	}
}
