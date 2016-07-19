package com.xidian.service.api;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.xidian.forms.User;

public interface UserService extends UserDetailsService{
	public User getUserByEmail(String email);
	public User getUserById(Long id);
	public String getPassword(String name);
	public void addUser(User user);
	public void deleteUser(long id);
	public void updateUser(User user);
}
