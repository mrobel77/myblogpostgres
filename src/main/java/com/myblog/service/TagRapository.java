package com.myblog.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.entitys.Tag;

public interface TagRapository extends JpaRepository<Tag, Integer> {

}
