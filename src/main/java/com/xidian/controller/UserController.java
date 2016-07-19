package com.xidian.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.forms.User;
import com.xidian.service.api.UserService;
import com.xidian.util.SecurityUtil;

@Controller
public class UserController {
	@Resource(name="userServiceImpl")
	UserService userService;
	
	@RequestMapping(value={"/","bbk.htm"},method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView testReturnUser(@RequestParam(value = "error", required = false) boolean error) {
		ModelAndView mav = new ModelAndView("user/login");
		if(error == true){
			mav.addObject("error","«Î ‰»Î’˝»∑µƒ’À∫≈ªÚ√‹¬Î£°");
		}
		return mav;
	}
	@RequestMapping(value="bbf.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView registerPage(HttpServletRequest request,HttpServletResponse response) {
		return new ModelAndView("user/register");
	}
	
	
	//’˝ Ω¥˙¬Î
	@RequestMapping(value="Login",method={RequestMethod.POST,RequestMethod.GET})
	public void login(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    JSONObject ret=new JSONObject();
	    //”√ªß√˚,‘›”√” œ‰
		String email= request.getParameter("userName");
		//√‹¬Î
		String password=request.getParameter("password");
		User existUser=userService.getUserByEmail(email);
		if (existUser!=null) {
			String pwd = existUser.getPassword();
			if(SecurityUtil.passwordsMatch(password, pwd)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", existUser);
				
				ret.put("Response", "µ«¬º≥…π¶");
				ret.put("Status", "success");
				ret.put("url", "mainPage.htm");
			} else {
				ret.put("Response", "√‹¬Î¥ÌŒÛ");
				ret.put("Status", "fail");
			}
		} else {
			ret.put("Response", "’À∫≈¥ÌŒÛ");
			ret.put("Status", "fail");
		}
		response.getWriter().write(ret.toString());
	}
	
	
	
	@RequestMapping(value="Register",method={RequestMethod.POST,RequestMethod.GET})
	public void register(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException 
	{
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8"); 
		JSONObject ret=new JSONObject();
		try {
		    String name = request.getParameter("nickName");
		    String password = request.getParameter("password");
			String email = request.getParameter("email");
			String sex = request.getParameter("sex");
			String address = request.getParameter("address");
			String school = request.getParameter("schoolAddress");
			String tempIsTeacher = request.getParameter("isTeacher");
			String key = request.getParameter("key");
			
			User user = new User();
			user.setUsername(email);
			user.setPassword(SecurityUtil.encryptPassword(password));
			user.setSex(sex);
			user.setName(name);
			user.setAddress(address);
			user.setSchool(school);
			user.setRegister_date(new Date());
			
			if(userService.getUserByEmail(email) == null && key.equals("a12adsfa132zsds43qa")){
				userService.addUser(user);
				User newUser = userService.getUserByEmail(email);
				ret.put("Status", "success");
				ret.put("UserID", newUser.getUid());
				ret.put("info", "◊¢≤·≥…π¶");
				ret.put("url", "bbk.htm");
			} else {
				ret.put("Status", "fail");
				ret.put("info", "◊¢≤· ß∞‹");
				ret.put("UserID", 0);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("Status", "fail");
			ret.put("info", "◊¢≤· ß∞‹");
			ret.put("UserID", 0);
		}
		response.getWriter().write(ret.toString());
	}
}
