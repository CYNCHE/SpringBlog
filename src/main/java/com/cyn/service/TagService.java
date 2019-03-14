package com.cyn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyn.po.Tag;

public interface TagService {

	Tag saveTag(Tag tag);
	
	void deleteTag(Long id);
	
	Optional<Tag> getTagById(Long id);
	
	Tag getTagByName(String Name);
	
	Page<Tag> listTag(Pageable pageable);
	
	List<Tag> listTag();
	
	List<Tag> listTag(String ids);
	
	List<Tag> listTagTop(Integer size); 

	Tag updateTag(Long id, Tag tag);
}
