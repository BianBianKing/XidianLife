package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.CardsDao;
import com.xidian.forms.Cards;
import com.xidian.forms.Courses;

@Repository("cardsDaoImpl")
public class CardsDaoImpl implements CardsDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public void addCards(Cards cards) {
		Session session=sessionFactory.getCurrentSession();
		session.save(cards);
	}

	@Override
	public void deleteCards(Long id) {
		Session session=sessionFactory.getCurrentSession();
		Cards cards = (Cards)session.get(Cards.class,id);
		session.delete(cards);
	}

	@Override
	public List<Cards> getCards() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Cards cards order by cards.date desc");
		List<Cards> cardsList=query.list();
		return cardsList;
	}

	@Override
	public List<Cards> getCardsByNum(String num) {
		Session session=sessionFactory.getCurrentSession();
		String query_string = "from Cards cards where lower(cards.lostNum) like lower('%";
		for (int i = 0; i < num.length(); ++i) {
			query_string += num.charAt(i) + "%";
		}
		query_string += "') order by cards.date desc";
		Query query=session.createQuery(query_string);
		List<Cards> cardsList=query.list();
		return cardsList;
	}

	@Override
	public List<Cards> getCardsByLostName(String lostName) {
		Session session=sessionFactory.getCurrentSession();
		String query_string = "from Cards cards where lower(cards.lostName) like lower('%";
		for (int i = 0; i < lostName.length(); ++i) {
			query_string += lostName.charAt(i) + "%";
		}
		query_string += "') order by cards.date desc";
		System.out.println(query_string);
		Query query=session.createQuery(query_string);
		List<Cards> cardsList=query.list();
		return cardsList;
	}

}
