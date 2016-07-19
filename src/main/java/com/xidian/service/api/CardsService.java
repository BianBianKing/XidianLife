package com.xidian.service.api;



import java.util.List;

import com.xidian.forms.Cards;

public interface CardsService {
	public void deleteCards(Long id);
	public List<Cards> getCards();
	public List<Cards> getCardsByNum(String num);
	public List<Cards> getCardsByLostName(String lostName);
	void addCards(String lostNum, String lostName, String keeperName,
			String keeperPhone);
}
