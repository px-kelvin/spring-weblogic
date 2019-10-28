package com.inspur.JMS.controller;

import java.util.Date;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inspur.JMS.service.ProducerServiceImpl;

import entity.student;



@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	@Qualifier("jmsQueue")
	private Destination destination;
	
	@Autowired
	private ProducerServiceImpl producerService;
	
	@RequestMapping("first")
	public  String first() {
		producerService.sendMessage(destination, "你好，现在是：" + new Date().toLocaleString());
		return null;
	}
	
}
