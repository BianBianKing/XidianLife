package com.xidian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TestController {
	@RequestMapping("/test")
	public String test(){
		return "bachelortimetableAdmin/test";
	}
}
