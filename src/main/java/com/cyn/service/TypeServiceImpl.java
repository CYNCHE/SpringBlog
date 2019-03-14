package com.cyn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import com.cyn.NotFoundException;
import com.cyn.dao.TypeRepository;
import com.cyn.po.Type;

@Service
public class TypeServiceImpl implements TypeService{

	
	@Autowired
	private TypeRepository typeRepository; 
	
	@Transactional
	@Override
	public Type saveType(Type type) {
		// TODO Auto-generated method stub
		return typeRepository.save(type);
	}

	@Transactional
	@Override
	public Optional<Type> getType(Long id) {
		// TODO Auto-generated method stub
		return typeRepository.findById(id);
	}

	@Transactional
	@Override
	public List<Type> listTypeTop(Integer size) {
		
		Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
		
		Pageable pageable = PageRequest.of(0, size, sort);
		return typeRepository.findTop(pageable);
	}

	@Transactional
	@Override
	public Page<Type> listType(Pageable pageable) {
		// TODO Auto-generated method stub
		return typeRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		// TODO Auto-generated method stub
		
		Optional<Type> t = typeRepository.findById(id);
		
		if (t == null) {
			throw new NotFoundException("This type does not exist!");
		}
		
		BeanUtils.copyProperties(type, t.get());
		return typeRepository.save(t.get());
	}

	@Transactional
	@Override
	public void deleteType(Long id) {
		// TODO Auto-generated method stub
		typeRepository.deleteById(id);
		
	}

	@Override
	@Transactional
	public List<Type> listType() {
		// TODO Auto-generated method stub
		return typeRepository.findAll();
	}

	@Transactional
	@Override
	public Type getTypeByName(String name) {
		// TODO Auto-generated method stub
		return typeRepository.findByName(name);
	}
	
	
	
	
	
	

}
