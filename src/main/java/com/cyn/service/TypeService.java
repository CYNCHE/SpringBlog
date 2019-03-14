package com.cyn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cyn.po.Type;

public interface TypeService {
	
	Type saveType(Type type);
	
	Optional<Type> getType(Long id);
	
	Page<Type> listType(Pageable pageable);
	
	List<Type> listType();
	
	Type updateType(Long id, Type type);
	
	List<Type> listTypeTop(Integer size);
	
	void deleteType(Long id);
	
	Type getTypeByName(String name);
	
}
