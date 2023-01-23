package com.smart.controllers;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.smart.dao.ContactRepositry;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Message;

@Controller
 @RequestMapping("/user") 
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContactRepositry contactReop;
	
	@ModelAttribute 
	public void addUserDetails(Model model,Principal principal) {
		String name=principal.getName();
		System.out.println(name);
	    User user=userRepository.getUserByUserName(name);
	    model.addAttribute("user",user);
	    System.out.println(user);
		
	}
	
	@RequestMapping("/index")
	 public String dashboard(Model model,Principal principal) {
		
		
		 return "normal/user_dashboard";
	 }
	
	// open add contact form
	
	@GetMapping("/add_contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title","add_conatct");
		model.addAttribute("conatct",new Contact());
		return "normal/add_contact_form";
	}
	
	@PostMapping("/process-contact")
	public String processContect(@ModelAttribute Contact contact ,
			@RequestParam("profileImage") MultipartFile file,
			Principal principal,
			HttpSession httpSession) {
		
		try {
			//proceesing and uploading file
			if(file.isEmpty()) {
				//file is empty
				contact.setImage("user.png");
			}else {
				//
			contact.setImage(file.getOriginalFilename());
			
			
	        
			File saveFile =	new ClassPathResource("static/img").getFile();
			file.transferTo(new File("C:\\Users\\JKUMAR33\\Documents\\workspace-spring-tool-suite-4-4.13.0.RELEASE\\smartcontactmanager\\src\\main\\resources\\static\\img\\"+file.getOriginalFilename()));
			
			/*
			 * Path path=
			 * Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename(
			 * )); Files.copy(file.getInputStream(), path,
			 * StandardCopyOption.REPLACE_EXISTING);
			 */
			
			System.out.println("IMAGE IS UPLOADED");
			httpSession.setAttribute("message",new Message("Your contact is added","success"));
			
			
			}
	
		String username= principal.getName();
		User user=userRepository.getUserByUserName(username);
		contact.setUser(user);
		user.getContacts().add(contact);
		userRepository.save(user);
		System.out.println("added to database");
		System.out.println("conatct"+contact.toString());
		
		} catch (Exception e) {
		System.out.println(e.getMessage());
			// TODO: handle exception
		httpSession.setAttribute("message",new Message("Something went worng",e.getMessage()));

		}
		return "normal/add_contact_form";
	}
	
	//show contact
	
	@GetMapping("/show_contact/{page}")
	public String showContact(@PathVariable("page")Integer page, Model model ,Principal principal) {
		String username=principal.getName();
		
	    User user=	this.userRepository.getUserByUserName(username);
	    //page request
	     Pageable pageable=  PageRequest.of(page,5);
	    
	    Page<Contact> listContact= contactReop.findContactsByUserId(user.getId(),pageable);
	    System.out.println(listContact);
		model.addAttribute("title","show contact");
		model.addAttribute("contacts",listContact);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpage",listContact.getTotalPages());
		
		
		return "normal/show_contact";
	}
	
	
	//show particular details
	@GetMapping("/contact/{id}")
	public String showParticularDetails(@PathVariable("id") Integer id ,Model model,Principal principal) {
	   String username=principal.getName();
	   User user=this.userRepository.getUserByUserName(username);
	   Optional<Contact> contactOptional=contactReop.findById(id);
	   Contact contact=contactOptional.get();
	  
	   
	   if(user.getId()==contact.getUser().getId()) {
		   model.addAttribute("contact",contact);
	   }
		return "normal/contact_details";
	}
	
	//delete contact
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid,HttpSession httpSession) {
		
	Optional<Contact> contact =	contactReop.findById(cid);
	Contact contacts =contact.get();
	contacts.setUser(null);
	contactReop.delete(contacts);
	
	httpSession.setAttribute("message", new Message("contact deleted successful !! ","success"));
	return "redirect:/user/show_contact/0";
	}
	
	@GetMapping("/contact/{id}")
	public String hi() {
		System.out.println("hello friends");
		System.out.println("hello friends");
		return "welocom";
	}

}
