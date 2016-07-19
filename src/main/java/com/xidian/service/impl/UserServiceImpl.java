package com.xidian.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xidian.dao.api.UserDao;
import com.xidian.forms.User;
import com.xidian.forms.UserInfo;
import com.xidian.service.api.UserService;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService{
	@Resource(name="userDaoImpl")
	UserDao userDao;
	
	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	public String getPassword(String name) {
		return userDao.getPassword(name);
	}
	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public void deleteUser(long id) {
		userDao.deleteUser(id);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public User getUserById(Long id) {
		return userDao.getUserById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username){
		System.out.println("111");
		try{
			System.out.println(username);
			User user = getUserByEmail(username);
			System.out.println(user);
			if(user == null)
				return null;
			UserInfo UserInfo = new UserInfo(user.getUsername(),user.getPassword().toLowerCase(),user.getName(),true,true,true,true,getAuthorities());
			return UserInfo;
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
