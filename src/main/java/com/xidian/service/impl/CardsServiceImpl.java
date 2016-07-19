package com.xidian.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.CardsDao;
import com.xidian.forms.Cards;
import com.xidian.service.api.CardsService;

@Service("cardsServiceImpl")
@Transactional
public class CardsServiceImpl implements CardsService{
	@Resource(name="cardsDaoImpl")
	CardsDao cardsDao;

	@Override
	public void addCards(String lostNum, String lostName, String keeperName, String keeperPhone) {
		Cards tempCards = new Cards();
		tempCards.setDate(new Date());
		tempCards.setKeeperName(keeperName);
		tempCards.setKeeperPhone(keeperPhone);
		tempCards.setLostName(lostName);
		tempCards.setLostNum(lostNum);
		cardsDao.addCards(tempCards);
	}

	@Override
	public void deleteCards(Long id) {
		cardsDao.deleteCards(id);
	}

	@Override
	public List<Cards> getCards() {
		return cardsDao.getCards();
	}

	@Override
	public List<Cards> getCardsByNum(String num) {
		return cardsDao.getCardsByNum(num);
	}

	@Override
	public List<Cards> getCardsByLostName(String lostName) {
		return cardsDao.getCardsByLostName(lostName);
	}
}
