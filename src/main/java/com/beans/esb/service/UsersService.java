package com.beans.esb.service;

import java.util.List;

import com.beans.esb.model.Users;
public interface UsersService {
	  Users create(Users users) throws Exception;
	  List<Users> findAll();
	  List<String> findUsersList(String username);
	  boolean findUserExistInApplication(String username);
	  Users findByUsername(String username);
	  int deleteUser(String username);
		  
}
