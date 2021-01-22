package com.ssafy.naite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.naite.model.dto.Test;
import com.ssafy.naite.model.service.TestService;

import io.swagger.annotations.ApiOperation;

@RestController
public class TestController {

	@Autowired
	TestService testService;
	
	@ApiOperation(value = "테스트")
	@GetMapping("/test")
	public @ResponseBody List<Test> getAll() throws Exception {
		return testService.getAll();
	}
}
