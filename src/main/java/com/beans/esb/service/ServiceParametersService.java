package com.beans.esb.service;

import java.util.List;
import java.util.Set;

import com.beans.esb.model.ServicesParameters;


public interface  ServiceParametersService {
	
	 ServicesParameters create(ServicesParameters systemParameters);
	 ServicesParameters delete(int id);
	 List<ServicesParameters> findAll();
	 ServicesParameters update(ServicesParameters systemParameters);
	 ServicesParameters findById(int id);	
	 boolean  checkPropertyName(String propertyName);
	 
	 boolean  checkPropertyNameWithId(String propertyName,int id);
	 String findByPropertyName(String propertyName);
	 Set<ServicesParameters> findAllInSet();
	

}

