package com.xidian.dao.api;

import java.util.List;

import com.xidian.forms.Cards;

public interface CardsDao {
	public void addCards(Cards cards);
	public void deleteCards(Long id);
	public List<Cards> getCards();
	public List<Cards> getCardsByNum(String num);
	public List<Cards> getCardsByLostName(String lostName);
}
