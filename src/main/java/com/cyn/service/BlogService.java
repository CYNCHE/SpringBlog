package com.cyn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyn.po.Blog;
import com.cyn.vo.BlogQuery;

public interface BlogService {
	
	Optional<Blog> getBlog(Long id);
	
	Blog saveBlog(Blog blog);
	
	void deleteBlog(Blog blog);
	
	void deleteBlog(Long id);
	
	List<Blog> listRecommendedBlogTop(Integer size);
	
	Blog updateBlog(Long id, Blog blog);
	
	Page<Blog> listBlog(String query, Pageable pageable);
	
	Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);
	
	Page<Blog> listBlog(Pageable pageable);
	
	Blog getBlogByName(String name);
}
