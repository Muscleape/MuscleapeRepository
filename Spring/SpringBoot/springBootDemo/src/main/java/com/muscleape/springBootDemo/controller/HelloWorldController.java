package com.muscleape.springBootDemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muscleape.springBootDemo.domain.User;

//RestController的意思就是controller里面的方法都是以json格式输出，不用再写什么jackjson配置了
@RestController
public class HelloWorldController {

	@RequestMapping("/")
	public String home() {
		return "Hello Docker World!";
	}

	@RequestMapping("/hello")
	public String index() {
		return "Hello World";
	}

	@RequestMapping("/getUser")
	public User getUser() {
		User user = new User();
		user.setUserName("小明");
		user.setPassWord("xxxx");
		return user;
	}
}
