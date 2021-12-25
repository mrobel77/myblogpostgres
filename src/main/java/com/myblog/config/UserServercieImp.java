package com.myblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myblog.entitys.User;
import com.myblog.service.UserRepository;


public class UserServercieImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
           User user =userRepository.getUserByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("WE Couldn't found User");
		}
		CustomUser customUser= new CustomUser(user);
		return customUser;
	}

}
