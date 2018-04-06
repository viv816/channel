package com.beans.esb.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

//import java.util.List;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.beans.esb.model.ServicesParameters;


public interface ServiceParametersRepository extends CrudRepository<ServicesParameters, Integer>{

	
	@Query("select sp from ServicesParameters sp where isDeleted = 0")
	List<ServicesParameters> findAll();
	

	@Query("select sp from ServicesParameters sp where  sp.serviceName=? and isDeleted = 0")
	ServicesParameters findByPropertyName(String propertyName);
	
	@Query("select sp from ServicesParameters sp where   sp.serviceName=? and isDeleted = 0 and sp.id not in(?) ")
	ServicesParameters checkPropertyName(String propertyName,int id);
	
	@Query("select s from ServicesParameters s where isDeleted = 0")
	 Set<ServicesParameters> findAllInSet();
	
	
	
	
	
}
