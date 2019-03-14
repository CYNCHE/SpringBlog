package com.cyn.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_type")
public class Type {
	
	@Id
	@GeneratedValue
	private Long id;
	@javax.validation.constraints.NotBlank(message = "Type name can not be empty!")
	private String name;
	
	@OneToMany(mappedBy = "type")
	private List<Blog> blogs = new ArrayList<>();
	
	
	public Type() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + "]";
	}
	
	
}
