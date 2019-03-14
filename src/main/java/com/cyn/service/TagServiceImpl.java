package com.cyn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cyn.NotFoundException;
import com.cyn.dao.TagRepository;
import com.cyn.po.Tag;

@Service
public class TagServiceImpl implements TagService{
	
	

	@Autowired
	private TagRepository tagRepository;

	@Transactional
	@Override
	public Tag saveTag(Tag tag) {
		
		return tagRepository.save(tag);
	}

	@Transactional
	@Override
	public List<Tag> listTag(String ids) {
		return tagRepository.findAllById(converToList(ids));
	}
	
	@Transactional
	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
		Pageable pageable = PageRequest.of(0, size, sort);
		return tagRepository.findTop(pageable);
	}

	private List<Long> converToList(String ids) {
		List<Long> list = new ArrayList<>();
		if (ids != "" && ids != null) {
			String[] idarray = ids.split(",");
			System.out.println("TagService" + list);
			for (int i = 0; i < idarray.length; ++i) {
				list.add(Long.valueOf(idarray[i]));
			}
			
		}
		
		return list;
	}
	
	

	@Transactional
	@Override
	public void deleteTag(Long id) {
		// TODO Auto-generated method stub
		tagRepository.deleteById(id);
	}

	@Transactional
	@Override
	public Optional<Tag> getTagById(Long id) {
		// TODO Auto-generated method stub
		return tagRepository.findById(id);
	}

	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		// TODO Auto-generated method stub
		Optional<Tag> t = tagRepository.findById(id);
		
		if (t == null) {
			throw new NotFoundException("This tag does not exist!");
		}
		
		BeanUtils.copyProperties(tag, t.get());
		return tagRepository.save(t.get());
	}
	

	@Transactional
	@Override
	public Tag getTagByName(String name) {
		// TODO Auto-generated method stub
		return tagRepository.findByName(name);
	}

	@Transactional
	@Override
	public Page<Tag> listTag(Pageable pageable) {
		// TODO Auto-generated method stub
		return tagRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public List<Tag> listTag() {
		// TODO Auto-generated method stub
		return tagRepository.findAll();
	}
	
	
}
