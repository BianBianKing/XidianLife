package com.xidian.dao.api;

import com.xidian.forms.User;

public interface UserDao {
	public User getUserByEmail(String email);
	public User getUserById(Long id);
	public String getPassword(String name);
	public void addUser(User user);
	public void deleteUser(long id);
	public void updateUser(User user);
}
