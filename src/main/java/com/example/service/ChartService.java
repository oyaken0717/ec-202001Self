package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Sale;
import com.example.repository.ChartRepository;

@Service
@Transactional
public class ChartService {

	@Autowired
	private ChartRepository chartRepository;
	
	public List<Sale> findByYear(Integer year){
		List<Sale> saleList = chartRepository.fingByYear(year);
				
		return saleList;
	}
}
