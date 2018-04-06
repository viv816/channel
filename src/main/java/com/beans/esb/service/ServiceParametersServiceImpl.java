package com.beans.esb.service;

import java.util.List;
import java.util.Set;

//import java.util.Date;
//import java.util.List;
//import java.util.Set;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beans.esb.model.ServicesParameters;
import com.beans.esb.repository.ServiceParametersRepository;
import com.beans.esb.util.BFTException;

@Service
public class ServiceParametersServiceImpl implements ServiceParametersService {

	
	@Resource
	private ServiceParametersRepository  systemParameterRepository;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	@Transactional(rollbackFor={Exception.class,BFTException.class})
	public ServicesParameters create(ServicesParameters systemParameters) {
		try{
			ServicesParameters systemParametersToBeCreated = systemParameters;

		log.info("SystemParametersServiceImpl.create:New System Parameters is saving into database with Name :"+systemParameters.getServiceName()+"and"+systemParameters.getServiceCode());
		return systemParameterRepository.save(systemParametersToBeCreated);
	}catch (Exception e) {
		log.error("SystemParametersServiceImpl.create:Error while saving new System Parameters :"+systemParameters.getServiceName()+"and"+systemParameters.getServiceCode(), e);
		throw new BFTException("err.systemParameters.create", e);
	}
  }

	@Override
	public ServicesParameters delete(int id) {
		ServicesParameters systemParameters = systemParameterRepository.findOne(id);
		if(systemParameters != null){
			systemParameters.setDeleted(true);
			log.info("SystemParametersServiceImpl.delete:System Parameters is deleting from the database with Name :"+systemParameters.getServiceName()+"and "+systemParameters.getServiceCode());
			systemParameterRepository.save(systemParameters);
		}
		return systemParameters;
	}

	@Override
	public List<ServicesParameters> findAll() {
		List<ServicesParameters> systemParametersList = systemParameterRepository.findAll();
		return systemParametersList;
	}

	@Override
	public ServicesParameters update(ServicesParameters systemParameters) {
	
		ServicesParameters systemParametersToBeUpdated = systemParameterRepository.findOne(systemParameters.getId());
		if(systemParametersToBeUpdated != null){
			systemParametersToBeUpdated.setId(systemParameters.getId());
			systemParametersToBeUpdated.setServiceName(systemParameters.getServiceName());
			systemParametersToBeUpdated.setServiceCode(systemParameters.getServiceCode());
			systemParametersToBeUpdated.setDescription(systemParameters.getDescription());
			systemParametersToBeUpdated.setLastModifiedBy(systemParameters.getLastModifiedBy());
			systemParametersToBeUpdated.setActive(systemParameters.isActive());
			systemParametersToBeUpdated.setLastModifiedTime(new java.util.Date());
			log.info("SystemParametersServiceImpl.update:System Parameters is updating into database with Name :"+systemParameters.getServiceName()+"and:"+systemParameters.getServiceCode());
			systemParameterRepository.save(systemParametersToBeUpdated);
		}
		
		return systemParametersToBeUpdated;
	}

	@Override
	public ServicesParameters findById(int id) {
		ServicesParameters systemParameters = systemParameterRepository.findOne(id);
		if(systemParameters == null)
			throw new BFTException("No System Parameters Found");
		return systemParameters;
	}	
	
	@Override
	public boolean checkPropertyName(String propertyName){
		ServicesParameters systemParameters = systemParameterRepository.findByPropertyName(propertyName);
		if(systemParameters!= null)
			return true;
		return false;
	}

	@Override
	public boolean checkPropertyNameWithId(String propertyName, int id) {
		ServicesParameters systemParameters = systemParameterRepository.checkPropertyName(propertyName, id);
		if(systemParameters!= null)
			return true;
		return false;
	}
	
	@Override
	public String findByPropertyName(String propertyName) {
		
		return systemParameterRepository.findByPropertyName(propertyName).getServiceCode();
	}

	@Override
	public Set<ServicesParameters> findAllInSet() {
		Set<ServicesParameters> resultSet = systemParameterRepository.findAllInSet();
		return resultSet;
	}
	
}

