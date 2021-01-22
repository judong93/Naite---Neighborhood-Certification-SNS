package com.ssafy.naite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.naite.model.dto.User;
import com.ssafy.naite.model.service.UserService;

@Controller
//@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/list")
	public @ResponseBody List<User> query() throws Exception {
		return userService.getAllUsers();
	}
	
}
