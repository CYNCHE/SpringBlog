package com.cyn.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cyn.po.Type;



public interface TypeRepository extends JpaRepository<Type, Long> {

	
	Type findByName(String name);
	
	@Query("select t from Type t")
	List<Type> findTop(Pageable pageable);
	
}
