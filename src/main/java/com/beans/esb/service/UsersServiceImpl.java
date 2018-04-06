package com.beans.esb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beans.esb.model.Users;
import com.beans.esb.repository.UsersRepository;


@Service
public class UsersServiceImpl implements UsersService {

	@Resource
	private UsersRepository usersRepository;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Transactional(rollbackFor = { Exception.class })
	public Users create(Users users) throws Exception{	
		try{
		 Users usersToBeCreated;
		 
		 usersToBeCreated =	usersRepository.findByUsername(users.getUsername());
		 if(usersToBeCreated!=null ){
			 throw new DataIntegrityViolationException("User already exists"); 
		 }else{
		 usersToBeCreated =	usersRepository.findPreviouslyExistingUser(users.getUsername());
		 if(usersToBeCreated!=null){
			 
			
			 usersToBeCreated.setEnabled(true);
			 usersToBeCreated.setLastModifiedBy(users.getLastModifiedBy());
			 usersToBeCreated.setLastModifiedTime(new Date());
			 log.info("UsersServiceImpl.create:New user is saving in database with name :"+users.getUsername());
			 usersRepository.save(usersToBeCreated);
			 return usersToBeCreated;
		 }
		 else{
			log.info("UsersServiceImpl.create:New user is saving in database with name :"+users.getUsername());

		 	return usersRepository.save(users);
		 }
		 }
		}catch(Exception e){
			log.error("UsersServiceImpl.create: Error in creating new user in database with name:"+users.getUsername(),e);
			if(e instanceof DataIntegrityViolationException)
				throw new DataIntegrityViolationException(e.getMessage());
			   throw e;
		}
	}
	
	
	public List<String> findUsersList(String username) {
		 List<String> s1 = new ArrayList<String>();
		
		String usernameSearchTerm = username + "%";
		s1= usersRepository.findUserList(usernameSearchTerm);
		return s1;
	}
	
	public List<Users> findAll() {
		List<Users> usersList = usersRepository.findAll();
		return usersList;
	}
	
	
	public boolean findUserExistInApplication(String username) {
		int count = usersRepository.findUsernameExists(username);
		if(count==1)
			return true;
	    return false;
	}
	
		
	public Users findByUsername(String username) {
		return usersRepository.findByUsername(username);
	}
	
	@Transactional
	public int deleteUser(String username) {
		return usersRepository.deleteUser(username);
	}


	


	


	


	

	


	
	
	

}