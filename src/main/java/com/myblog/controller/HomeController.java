package com.myblog.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myblog.entitys.Blogs;
import com.myblog.entitys.User;
import com.myblog.helper.Massage;
import com.myblog.service.BlogRepository;
import com.myblog.service.UserRepository;



@Controller
public class HomeController {

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlogRepository blogRepository;
	@GetMapping("/")
	public String home()
	{
		
		
		return "index";
	}
	
	 @RequestMapping("/signin")
	  public String loginhandle(Model m) 
	  {
	  m.addAttribute("title", "Login Page");
	  return "login"; 
	  }
	 
	 @RequestMapping(value="/blog/{page}", method=RequestMethod.GET)
		public String showblog(@PathVariable ("page") Integer page, Model m)
		{
			m.addAttribute("title","Blog");
																							
			
			Sort sort= Sort.by(Sort.Direction.DESC,"cId");
			Pageable pageable = PageRequest.of(page, 5,sort);
			Page<Blogs> list = this.blogRepository.findAll(pageable);
			
			
			m.addAttribute("bloglist",list);
			m.addAttribute("currentPage",page);
			m.addAttribute("totalPages", list.getTotalPages());
			return "blog";
		}
	
	@RequestMapping("/signup")	  
    public String signup(Model m)
    {	
		   m.addAttribute("title","Register!");
		   m.addAttribute("user", new User());
 	   return "signup";
    }
	 @RequestMapping(value="/do_register",method=RequestMethod.POST)	  
     public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
  		   @RequestParam(value="agreement", defaultValue="false") boolean agreement, Model m,
  		   HttpSession session)
     {
		   try {
			   if(!agreement)
			   {
				   System.out.println("you have not agree");
				   throw new Exception("you haven't agree ");
			   }
			   if(result1.hasErrors())
			   {
				   System.out.println("ERROR"+result1.toString());
				   m.addAttribute("user",user);
				   return"signup";
			   }
			   
			   user.setRole("ROLE_USER");
			   user.setEnabled(true);
			   user.setImageUrl("default.png");
			   user.setEmail(user.getEmail());
			   user.setPassword(passwordEncoder.encode(user.getPassword()));
			   System.out.println("Agreement "+agreement);
			  
			  User result = this.userRepository.save(user);
			   m.addAttribute("user", new User());
			  System.out.println("User"+result);
			   session.setAttribute("message", new Massage("registraion successfull","alert-success" ));
			   return "signup";
		   }catch (Exception e)
		   {
			   e.printStackTrace();
			 //  m.addAttribute("user",user);
			   session.setAttribute("message", new Massage("Something went wrong"+e.getMessage(),"alert-danger"));
			   return "signup";
		   }
     }
}
