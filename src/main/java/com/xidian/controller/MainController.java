package com.xidian.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.forms.UserInfo;

@Controller
@RequestMapping("/admin")
public class MainController {
	@RequestMapping(value={"/","mainPage.htm"},method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView mainPage() {
		ModelAndView temp = new ModelAndView("mainPage");
		//HttpSession session = request.getSession();
		return temp;
	}
}
