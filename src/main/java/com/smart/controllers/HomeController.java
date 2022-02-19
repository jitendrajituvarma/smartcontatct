package com.smart.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.helper.Message;

@Controller
public class HomeController {
    @Autowired
	UserRepository repository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/home")
	public String homePage(Model model) {
		model.addAttribute("title"+"smart contact management");
		return "home";
	}
	@GetMapping("/about")
	public String homeabout(Model model) {
		model.addAttribute("title"+"smart contact about management");
		return "about";
	}
	@GetMapping("/signup")
	public String homesignup(Model model) {
		model.addAttribute("title"+"smart contact about management");
		model.addAttribute("user", new User());
		return "signup";
	}
	@GetMapping("/sigin")
	public String homeLogin(Model model) {
		model.addAttribute("title"+"smart contact about management");
		return "login";
	}
	@GetMapping("/show")
	public String homeshow(Model model) {
		model.addAttribute("title"+"smart contact about management");
		return "show";
	}
	
	@PostMapping("/do_register")
	public String doRegistration(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,@RequestParam(
			value ="agreement" ,defaultValue ="false") boolean agreement ,Model model ,
			HttpSession httpSession){
		try {
			    if(bindingResult.hasErrors()) {
			        System.out.println(bindingResult.toString());	
			    	model.addAttribute("user",user);
			    	return "signup";
			    }
			if(!agreement) {
				System.out.println(agreement);
				throw new Exception("you have not agree to turm and condition");
				}
				user.setEnable(true);
				user.setRole("ROLE_USER");
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			    User result=repository.save(user);
				System.out.println(user);
				model.addAttribute("user",new User());
				httpSession.setAttribute("message", new Message("Successfully Register","alert-success"));
				return "signup";
				
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("user",user);
			httpSession.setAttribute("message", new Message("Something went wrong"+e.getMessage(),"alart-dander"));
			return "signup";
		}
		 
	}
     
	
}
