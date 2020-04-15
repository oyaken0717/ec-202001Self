package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.service.AdminService;

@Controller
@RequestMapping("/csv")
public class CsvController {

	@Autowired
	private AdminService adminService;
	
//■　https://kuwalab.hatenablog.jp/entry/spring_mvc41/022
    @RequestMapping("/csvDown")
    public void csvDown(HttpServletResponse response) {
    	
        response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=utf-8");
        response.setHeader("Content-Disposition","attachment; filename=\"test.csv\"");
        List<Order> orderList = adminService.findAll();
        
        try (PrintWriter pw = response.getWriter()) {
        	for (Order order : orderList) {
        		for (OrderItem orderItem : order.getOrderItemList()) {
        			String status = "未入金";
        			if (order.getStatus() == 2) {
						status = "入金済";
					}
        			pw.print(
        					order.getId()+","+
        					order.getOrderDate()+","+
        					orderItem.getItem().getName()+","+
        					order.getUser().getName()+","+
        					status+","+
        					orderItem.getSubTotal()+","+
        					",\r\n");				
				}
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}