package com.cyn.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyn.NotFoundException;
import com.cyn.dao.BlogRepository;
import com.cyn.po.Blog;
import com.cyn.po.Type;
import com.cyn.vo.BlogQuery;

import util.MyBeanUtils;



@Service
public class BlogServiceImpl implements BlogService {

	
	@Autowired
	private BlogRepository blogRepository;
	
	public BlogServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	@Transactional
	public Optional<Blog> getBlog(Long id) {
		
		return blogRepository.findById(id);
	}

	@Override
	@Transactional
	public Blog saveBlog(Blog blog) {
		if (blog.getId() == null) {
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
			return blogRepository.save(blog);
		} 
		else {
			return updateBlog(blog.getId(), blog);
		}
		
	}

	
	@Override
	@Transactional
	public Page<Blog> listBlog(String query, Pageable pageable) {
		// TODO Auto-generated method stub
		
		return blogRepository.findByQuery(query, pageable);
	}


	@Transactional
	@Override
	public List<Blog> listRecommendedBlogTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
		Pageable pageable = PageRequest.of(0, size, sort);
		return blogRepository.findTop(pageable);
	}


	@Override
	@Transactional
	public Page<Blog> listBlog(Pageable pageable) {
		// TODO Auto-generated method stub
		return blogRepository.findAll(pageable);
	}

	
	@Override
	@Transactional
	public void deleteBlog(Long id) {
		// TODO Auto-generated method stub
		blogRepository.deleteById(id);
		
	}


	@Override
	@Transactional
	public void deleteBlog(Blog blog) {
		
		blogRepository.delete(blog);
	}

	@Override
	@Transactional
	public Blog updateBlog(Long id, Blog blog) {
		System.out.println("I am here");
		Blog blog1 = blogRepository.findById(id).get();
		if (blog1 == null) {
			throw new NotFoundException("This blog does not exist!");
		}
		BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
		blog1.setUpdateTime(new Date());
		return blogRepository.save(blog1);
	}

	@Override
	@Transactional
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		
		return blogRepository.findAll(new Specification<Blog>() {
			
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (blog.getTitle() != "" && blog.getTitle() != null) {
					predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
				}
				if (blog.getTypeId() != null) {
					predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
				}
				if (blog.isRecommended()) {
					System.out.println("Always true Always true Always true Always true Always true Always true Always true Always true Always true Always true Always true Always true");
					predicates.add(cb.equal(root.<Boolean>get("recommended"), blog.isRecommended()));
				}
				
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				//System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
				return null;
			}
		}, pageable);
	}

	@Override
	@Transactional
	public Blog getBlogByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
