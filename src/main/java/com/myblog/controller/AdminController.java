package com.myblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myblog.entitys.Blogs;
import com.myblog.service.BlogRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@RequestMapping("/index")
	public String dashboard(Model m)
	{
		List<Blogs> list = this.blogRepository.findAll();
		m.addAttribute("title", "Dash board");
		m.addAttribute("list",list);
		return "admin/user_dashboard";
	}
	
	//delalet blogs
		@GetMapping("/delete/{cid}")
		public String deleteBlog(@PathVariable("cid")Integer cId, Model m, HttpSession session)
		{
			Optional<Blogs> optionalBlog = this.blogRepository.findById(cId);
			Blogs blogs = optionalBlog.get();
			this.blogRepository.delete(blogs);
				
			return "redirect:/admin/index";
			
		}

}
