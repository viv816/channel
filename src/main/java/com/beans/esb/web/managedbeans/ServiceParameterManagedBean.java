package com.beans.esb.web.managedbeans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
//import javax.faces.context.FacesContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beans.esb.datamodel.ServiceParametersDataModel;
import com.beans.esb.model.ServicesParameters;
import com.beans.esb.service.ServiceParametersService;
import com.beans.esb.util.BFTException;

import org.primefaces.context.RequestContext;
@Component
@ViewScoped
public class ServiceParameterManagedBean implements Serializable {

private static final long serialVersionUID = 1L;

    /* @Autowired
      private UsersService usersService;*/
    @Autowired
     ServiceParametersService systemParametersService;
    final static Logger logger = Logger.getLogger(ServiceParameterManagedBean.class);

    
	private List<ServicesParameters> systemParamatersList;
	private ServiceParametersDataModel  systemParametersDataModel;
	private ServicesParameters newSystemParameters = new ServicesParameters();
	private ServicesParameters selectedSystemParameters = new ServicesParameters();
	private boolean insertDelete = false;
	//private Users loggedInUser;
    
	/*@Autowired
	private SystemAuditTrailRecordService systemAuditTrailRecordService;*/
	
	public ServiceParametersService getSystemParametersService() {
		return systemParametersService;
	}

	public void setSystemParametersService(ServiceParametersService systemParametersService) {
		this.systemParametersService = systemParametersService;
	}

	public ServicesParameters getNewSystemParameters() {
		return newSystemParameters;
	}

	public void setNewSystemParameters(ServicesParameters newSystemParameters) {
		this.newSystemParameters = newSystemParameters;
	}

	/*public Users getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
		*/
	public ServiceParametersDataModel getSystemParametersDataModel() {
		
			return new ServiceParametersDataModel(getSystemParamatersList());
	}

	public void setSystemParametersDataModel(ServiceParametersDataModel systemParametersDataModel) {
		this.systemParametersDataModel = systemParametersDataModel;
	}

	public boolean isInsertDelete() {
		return insertDelete;
	}
	
	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}
	
	public List<ServicesParameters> getSystemParamatersList() {
		
		if(systemParamatersList == null){
			systemParamatersList = systemParametersService.findAll();
		}
		
		return systemParamatersList;
	}

	public void setSystemParamatersList(List<ServicesParameters> systemParamatersList) {
		this.systemParamatersList = systemParamatersList;
	}

	public ServicesParameters getSelectedSystemParameters() {
		return selectedSystemParameters;
	}

	public void setSelectedSystemParameters(
			ServicesParameters selectedSystemParameters) {
		this.selectedSystemParameters = selectedSystemParameters;
	}
	
	/*public SystemAuditTrailRecordService getSystemAuditTrailRecordService() {
		return systemAuditTrailRecordService;
	}

	public void setSystemAuditTrailRecordService(
			SystemAuditTrailRecordService systemAuditTrailRecordService) {
		this.systemAuditTrailRecordService = systemAuditTrailRecordService;
	}*/

		
	public void doCreateSystemParameter() {
		if(getSystemParametersService().checkPropertyName(newSystemParameters.getServiceName())){
			System.out.println("PropetyName already Exist");
			FacesMessage msg = new FacesMessage("FAILURE : Services Parameters " + newSystemParameters.getServiceCode()+" Already Exists");  
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);  
			FacesContext.getCurrentInstance().addMessage(null, msg); 
			return;
		}
		else{	
		
		try{
			logger.info("SystemParameterManagedBean.doCreateSystemParameter is creating Service name: "+newSystemParameters.getServiceCode()+"and propertyValue"+newSystemParameters.getServiceCode());
			/*loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");*/
	        newSystemParameters.setDeleted(false);
			newSystemParameters.setCreatedBy("admin");
			newSystemParameters.setCreationTime(new java.util.Date());
			getSystemParametersService().create(newSystemParameters);
			setInsertDelete(true);
			//systemAuditTrailRecordService.log(SystemAuditTrailActivity.CREATED,SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),getLoggedInUser().getUsername(), getLoggedInUser().getUsername() + " has created new System Paramenter " + newSystemParameters.getServiceCode());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : New Services Parameter record created" ));
			newSystemParameters = new ServicesParameters();
			if(systemParamatersList == null || insertDelete == true){
				systemParamatersList = systemParametersService.findAll();
			}
			
		}catch(BFTException e){
			
			  logger.error("SystemParameterManagedBean.doCreateSystemParameter: Error while creating Service name: "+newSystemParameters.getServiceCode()+"and propertyValue"+newSystemParameters.getServiceCode(),e);
			  FacesMessage msg = new  FacesMessage("Error in creating  Services Name:" + newSystemParameters.getServiceCode()+"and propertyValue"+newSystemParameters.getServiceCode());
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
		}
	}
	
	public void doUpdateSystemParameter(){
	 if(getSystemParametersService().checkPropertyNameWithId(selectedSystemParameters.getServiceCode(),selectedSystemParameters.getId())){
			FacesMessage msg = new FacesMessage("FAILURE : Service Parameters " + selectedSystemParameters.getServiceCode()+"  Already Exists");  
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);  
			FacesContext.getCurrentInstance().addMessage(null, msg); 
			return;
		
			
		}
		else{	
		      try{
					logger.info("SystemParameterManagedBean.doUpdateSystemParameter is updating  property name: "+selectedSystemParameters.getServiceCode()+"and propertyValue"+selectedSystemParameters.getServiceCode());
					//loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
					selectedSystemParameters.setLastModifiedBy("admin");
					systemParametersService.update(selectedSystemParameters);
					setInsertDelete(true);
					if(systemParamatersList == null || insertDelete == true){
					     systemParamatersList = systemParametersService.findAll();
				     }
					//systemAuditTrailRecordService.log(SystemAuditTrailActivity.UPDATED,SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),getLoggedInUser().getUsername(), getLoggedInUser().getUsername() + " has updated System Paramenter " + selectedSystemParameters.getServiceCode());
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS : Services Parameters" + selectedSystemParameters.getServiceCode()+" has been updated " ));
		        }catch(BFTException e){
		        	  logger.error("SystemParameterManagedBean.doUpdateSystemParameter:Error while Updating property name: "+selectedSystemParameters.getServiceCode()+"and propertyValue"+selectedSystemParameters.getServiceCode(),e);
					  FacesMessage msg = new  FacesMessage("Error in Updating : Services Parameters " + selectedSystemParameters.getServiceCode());
					  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					  FacesContext.getCurrentInstance().addMessage(null, msg); 
			
		         }
		     }
	}
	
	public void doDeleteSystemParameter(){
		try{
			logger.info("SystemParameterManagedBean.doDeleteSystemParameter is deleting  property name: "+selectedSystemParameters.getServiceCode()+"and propertyValue"+selectedSystemParameters.getServiceCode());
			//loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
			getSystemParametersService().delete(selectedSystemParameters.getId());
			setInsertDelete(true);
			//systemAuditTrailRecordService.log(SystemAuditTrailActivity.DELETED,SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),getLoggedInUser().getUsername(), getLoggedInUser().getUsername() + " has deleted System Paramenter " + selectedSystemParameters.getServiceCode());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("SUCCESS :Services Parameter record deleted" ));
			if(systemParamatersList == null || insertDelete == true){
				systemParamatersList = systemParametersService.findAll();
			}
		}catch(BFTException e) {
			  logger.error("SystemParameterManagedBean.doDeleteSystemParameter:Error while Deleting property name: "+selectedSystemParameters.getServiceCode()+"and propertyValue"+selectedSystemParameters.getServiceCode(),e);
			  FacesMessage msg = new  FacesMessage("Error in deleting : SystemParameters " + selectedSystemParameters.getServiceCode());
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage(null, msg); 
		}
	}
	
	
	void checkBeforeUpdate(){
		//loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
		//HashSet<String> accessRights=usersService.getAccessRightsMapForUser("admin");
		/*if(accessRights.contains("SY01E")||accessRights.contains("SY01D")){	*/	
			//if(("admin".equalsIgnoreCase("system"))&&selectedSystemParameters.getCreatedBy().equalsIgnoreCase("system")){
				RequestContext.getCurrentInstance().execute("PF('systemParameterDialog').show()");
			/*}
			else{
					if(!("admin".equalsIgnoreCase("system"))&&!(selectedSystemParameters.getCreatedBy().equalsIgnoreCase("system"))){
						RequestContext.getCurrentInstance().execute("PF('systemParameterDialog').show()");
					}
					else{
							RequestContext.getCurrentInstance().execute("PF('systemParameterDialog').hide()");
							FacesMessage msg = new FacesMessage("You Dont have Rights to Update this Record");  
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);  
							FacesContext.getCurrentInstance().addMessage(null, msg); 
					        return;
						}      
			}
		}
		else{
				RequestContext.getCurrentInstance().execute("PF('systemParameterDialog').hide()");
				FacesMessage msg = new FacesMessage("You Dont have Rights to Update this Record");  
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);  
				FacesContext.getCurrentInstance().addMessage(null, msg); 
		        return;
		}*/
		   
	}
	
	
	
	
		
	public void onRowSelect(SelectEvent event){
		setSelectedSystemParameters((ServicesParameters)event.getObject());
		checkBeforeUpdate();
	}
	
	public void showDailogue(){
		
		RequestContext.getCurrentInstance().execute("PF('newSystemParametersDialog').show()");
        return;

	}
	
	
}
