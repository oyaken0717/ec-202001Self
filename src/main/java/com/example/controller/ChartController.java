package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Sale;
import com.example.service.ChartService;

@RestController
@RequestMapping("/chart")
public class ChartController {

	@Autowired
	private ChartService chartService;
	
	@RequestMapping(value="/graph", method = RequestMethod.POST)
	public List<Sale> graph(Integer year) {
		List<Sale> saleList = chartService.findByYear(year);
		
//		System.out.println("saleList");
//		System.out.println(saleList.toString());
//		
//		List<Integer> yokos = new ArrayList<>();
//		List<Integer> tates = new ArrayList<>();
//		for (Sale sale : saleList) {
//			yokos.add(sale.getMonth());
//			tates.add(sale.getTotalPrice());
//		}
//		model.addAttribute("yokos",yokos);
//		model.addAttribute("tates",tates);
//		System.out.println("yokos");
//		System.out.println(yokos.toString());
		
		return saleList;
	}
}
