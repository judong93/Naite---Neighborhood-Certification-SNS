package com.ssafy.naite.model.mapper;

import java.util.List;

import com.ssafy.naite.model.dto.Test;

public interface TestMapper {
	public List<Test> getAll() throws Exception;
}
