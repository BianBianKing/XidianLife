package com.xidian.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xidian.forms.Cards;
import com.xidian.service.api.CardsService;

@Controller
@RequestMapping(value="/admin")
public class CardsController{
	@Resource(name="cardsServiceImpl")
	CardsService cardsService;
	
	@RequestMapping(value="cardsList.htm",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView cardsList(HttpServletRequest request,HttpServletResponse response) {
		List<Cards> tempList = cardsService.getCards();
		ModelAndView tempView = new ModelAndView("cards/cardsList");
		tempView.addObject("cardsList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="searchCards.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView searchCards(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String numString = request.getParameter("num");
		String nameString = request.getParameter("name");
		List<Cards> tempList;
		if (numString != null) {
			System.out.println("num:"+numString);
			tempList = cardsService.getCardsByNum(numString);
		} else {
			System.out.println("name:"+nameString);
			tempList = cardsService.getCardsByLostName(nameString);
		}
		ModelAndView tempView = new ModelAndView("cards/cardsList");
		tempView.addObject("cardsList", tempList);
		return tempView;
	}
	
	@RequestMapping(value="deleteCards.action",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView deleteCards(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		response.setContentType("application/json");  
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    String temp_id = request.getParameter("id");
	    Long id = Long.parseLong(temp_id);
	    ModelAndView tempView = new ModelAndView("cards/cardsList");
	    
	    try {
	    	cardsService.deleteCards(id);;
	    	tempView.addObject("status", "success");
	    	tempView.addObject("info", "É¾³ý³É¹¦£¡");
	    } catch (Exception e) {
	    	tempView.addObject("status", "fail");
	    	tempView.addObject("info", "É¾³ýÊ§°Ü£¡");
	    	e.printStackTrace();
	    }
	    
	    List<Cards> tempList = cardsService.getCards();
	    tempView.addObject("cardsList", tempList);
	    return tempView;
	}
}
