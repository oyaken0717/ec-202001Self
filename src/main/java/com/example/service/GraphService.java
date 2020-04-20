package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Graph;
import com.example.repository.GraphRepository;

@Service
@Transactional
public class GraphService {

	
	@Autowired
	private GraphRepository graphRepository;
	
	public List<Graph> findByYear(Integer year) {
		List<Graph> saleList = graphRepository.findByYear(year);  
		return saleList;

	} 
}
