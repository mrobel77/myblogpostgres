package com.myblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myblog.entitys.Blogs;


public interface BlogRepository extends JpaRepository<Blogs, Integer>  {
	
@Query("from Blogs as d where d.user.id=:userId")	
public Page<Blogs> findBlogsByUser(@Param("userId") int userId, Pageable pageable);

	
	
	}
