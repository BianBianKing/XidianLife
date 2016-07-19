package com.xidian.service.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.UserDao;


public class UserService implements UserDetailsService{//
	@Resource(name="userDaoImpl")
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		System.out.println("111");
		try{
			System.out.println(username);
			com.xidian.forms.User user = userDao.getUserByEmail(username);
			System.out.println(user);
			if(user == null)
				return null;
			UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword().toLowerCase(),true,true,true,true,getAuthorities());
			return userDetails;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public Collection<GrantedAuthority> getAuthorities() {  
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();  
        authList.add(new GrantedAuthorityImpl("ROLE_ADMIN")); 
        return authList;  
    }
}
