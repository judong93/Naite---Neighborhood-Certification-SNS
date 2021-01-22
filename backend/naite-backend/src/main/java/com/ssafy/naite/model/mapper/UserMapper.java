package com.ssafy.naite.model.mapper;

import java.util.List;

import com.ssafy.naite.model.dto.User;

public interface UserMapper {
	public List<User> getAllUsers() throws Exception;
}
