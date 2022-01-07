package com.myblog.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myblog.entitys.Blogs;
import com.myblog.entitys.Tag;
import com.myblog.entitys.User;
import com.myblog.service.BlogRepository;
import com.myblog.service.TagRapository;
import com.myblog.service.UserRepository;


@Controller
@RequestMapping("/user")
public class UserController {
	Blogs blog1;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TagRapository tagRapository;
	
	 
	  
	@RequestMapping(value="/blog/{page}", method=RequestMethod.GET)
	public String showblog(@PathVariable ("page") Integer page, Model m,Principal principal)
	{
		m.addAttribute("title","Blog");
		String username=principal.getName();
		
		
		
		User user=this.userRepository.getUserByUserName(username);
		
		
		
		m.addAttribute("name",user.getName());
		
		Sort sort= Sort.by(Sort.Direction.DESC,"cId");
		Pageable pageable = PageRequest.of(page, 5,sort);
		Page<Blogs> list = this.blogRepository.findBlogsByUser(user.getId(),pageable );
		
		System.out.println(list);
		m.addAttribute("bloglist",list);
		m.addAttribute("currentPage",page);
		m.addAttribute("totalPages", list.getTotalPages());
		return "normal/blog";
	}
	
	@RequestMapping("/addblog")
	public String addblog(Model model)
	{
		model.addAttribute("blog", new Blogs());
		
		return "normal/addblog";
	}
	@PostMapping("/process-blog")
	public String processblog(@ModelAttribute Blogs blog,BindingResult result1, @RequestParam("image") MultipartFile file, 
			Principal principal,@RequestParam("catagorie") String tagName, Model m)
			
	{
		String name = principal.getName();
	
		User userByUserName = userRepository.getUserByUserName(name);
		
		blog.setUser(userByUserName);
		
		Tag tags= new Tag();
		tags.setName(tagName);
		List<Tag> taglist=new ArrayList<>();
		
		taglist.add(tags);
		
		
		blog.setTags(taglist);
		
		System.out.println(tags);
		 
		try {
		
		  if (file.isEmpty()) {
		  
			  System.out.println("Image is empty"); 
		  }else {
		  
		  blog.setImage(file.getOriginalFilename());
		  
		  
	   File savefile = new ClassPathResource("static/image").getFile(); 
		Path path = Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
		Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
		  System.out.println("Image is uploaded"); 
		      } 
		  }
		
		catch (IOException e) { }
		 
		this.blogRepository.save(blog);
		
		return "normal/addblog";
	}
	
	
	
}
