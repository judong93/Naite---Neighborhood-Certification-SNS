package com.ssafy.naite.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.naite.model.dto.User;
import com.ssafy.naite.model.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;
	
	public List<User> getAllUsers() throws Exception {
		return userMapper.getAllUsers();
	}
}
