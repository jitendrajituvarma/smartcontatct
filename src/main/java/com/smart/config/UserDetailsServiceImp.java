package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entity.User;

public class UserDetailsServiceImp implements UserDetailsService { 
	@Autowired
	private UserRepository  userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 User user=	userRepo.getUserByUserName(username);
	
	 if(user==null){
		  try {
		 throw new UsernameNotFoundException("user not found exception"); 
		  }catch (UsernameNotFoundException e) {
			System.out.println(e.getMessage());
		}
	 }
		 CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	
	}

}
