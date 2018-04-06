package com.beans.esb.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;

import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.beans.esb.datamodel.UsersDataModel;
import com.beans.esb.model.ServicesParameters;
import com.beans.esb.model.Users;
import com.beans.esb.service.ServiceParametersService;
import com.beans.esb.service.UsersService;


@Component
@ViewScoped
public class UsersManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(UsersManagedBean.class);
	List<String> users = new ArrayList<String>();
	private Users selectedUser = new Users();
	private Users newUser = new Users();
	private List<Users> usersList;
	private UsersDataModel usersDataModel;
	private Users loggedInUser;
	private Set<ServicesParameters> serviceSet = null;
	private Set<ServicesParameters> assignedServiceSet = new HashSet<ServicesParameters>();
	private Set<ServicesParameters> unassignedServiceSet = new HashSet<ServicesParameters>();
	private DualListModel<ServicesParameters> dualServiceList = new DualListModel<ServicesParameters>();
	private boolean insertDelete = false;
	
	

	@Autowired
	private UsersService usersService;

	@Autowired
	private ServiceParametersService serviceParametersService;


	public Users getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}


	public Users getNewUser() {
		return newUser;
	}

	public List<String> getUsers() {
		return users;
	}


	public void setUsers(List<String> users) {
		this.users = users;
	}



	public void setNewUser(Users newUser) {
		this.newUser = newUser;
	}

	public Users getSelectedUser() {
		return selectedUser;
	}
	
	
	public Set<ServicesParameters> getServiceSet() {
		return serviceSet;
	}

	public void setServiceSet(Set<ServicesParameters> serviceSet) {
		this.serviceSet = serviceSet;
	}

	public Set<ServicesParameters> getAssignedServiceSet() {
		return assignedServiceSet;
	}

	public void setAssignedServiceSet(Set<ServicesParameters> assignedServiceSet) {
		this.assignedServiceSet = assignedServiceSet;
	}

	public Set<ServicesParameters> getUnassignedServiceSet() {
		return unassignedServiceSet;
	}

	public void setUnassignedServiceSet(Set<ServicesParameters> unassignedServiceSet) {
		this.unassignedServiceSet = unassignedServiceSet;
	}

	public DualListModel<ServicesParameters> getDualServiceList() {
		return dualServiceList;
	}

	public void setDualServiceList(DualListModel<ServicesParameters> dualServiceList) {
		this.dualServiceList = dualServiceList;
	}

	public void setSelectedUser(Users selectedUser) {
		this.selectedUser = selectedUser;
		this.assignedServiceSet = this.selectedUser.getUserServices();
		serviceSet=null; 
		serviceSet = serviceParametersService.findAllInSet();
		this.unassignedServiceSet.addAll(this.serviceSet);
		this.unassignedServiceSet.removeAll(this.assignedServiceSet);
		List<ServicesParameters> unassignedServiceList = new ArrayList<ServicesParameters>();
		unassignedServiceList.addAll(unassignedServiceSet);
		List<ServicesParameters> assignedServiceList = new ArrayList<ServicesParameters>();
		assignedServiceList.addAll(assignedServiceSet);
		this.dualServiceList = new DualListModel<ServicesParameters>(unassignedServiceList, assignedServiceList);
	}

	public UsersDataModel getUsersDataModel() {
		return new UsersDataModel(getUsersList());
	}

	public void setUsersDataModel(UsersDataModel usersDataModel) {
		this.usersDataModel = usersDataModel;
	}
	

	public List<Users> getUsersList() {
		if(usersList == null) {			
			usersList = usersService.findAll();
		}		
		return usersList;
	}


	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}


	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	
	public void doCreateUser() {
		             
		try {
			 logger.info("UsersManagedBean.doCreateUser is creating new user:"+newUser.getUsername());
			System.out.println("Getting logged in user from session..");
			//loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
			String newUsername=newUser.getUsername();		
						 					
			newUser.setEnabled(true);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(newUser.getPassword());
			newUser.setPassword(hashedPassword); 
			newUser.setCreatedBy("admin");
			newUser.setCreationTime(new Date());
			List<ServicesParameters> selectedServiceList = dualServiceList.getTarget();
			HashSet<ServicesParameters> selectedServiceSet = new HashSet<ServicesParameters>();
			selectedServiceSet.addAll(selectedServiceList);
			newUser.setUserServices(selectedServiceSet);
			usersService.create(newUser);
			setInsertDelete(true);
			if(usersList == null || insertDelete == true) {			
				usersList = usersService.findAll();
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("SUCCESS : User  " + newUser.getUsername()+ " has been created."));
			newUser = new Users();
		
		} catch(Exception e){ 
			if(e instanceof DataIntegrityViolationException){
			  FacesMessage msg = new  FacesMessage("FAILURE : Username  Already Exist.");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage(null, msg); 
			  }else{
			  FacesMessage msg = new  FacesMessage("FAILURE: Not able to add user currently to System");
			  msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			  FacesContext.getCurrentInstance().addMessage(null, msg); 
			  }
		}
		
	}
	
	
	void checkBeforeUpdate(){
		RequestContext.getCurrentInstance().execute("PF('userDialog').show()");
	}
	
     public void onRowSelect(SelectEvent event) {
	    Users userSelected = (Users) event.getObject();		
		setSelectedUser(usersService.findByUsername(userSelected.getUsername()));
		checkBeforeUpdate();

	}
	
	
  

	public void doInitializeForm() {
		newUser = new Users();
	}
	
	
   /* public void doUpdateUser(){
	
		try{
			logger.info("UsersManagedBean.doUpdateUser is updating new user:"+selectedUser.getUsername());

			//loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
			
			List<ServicesParameters> selectedServiceList = dualServiceList.getTarget();
			HashSet<ServicesParameters> selectedServiceSet = new HashSet<ServicesParameters>();
			selectedServiceSet.addAll(selectedServiceList);
			selectedUser.setUserServices(selectedServiceSet);
			selectedUser.setLastModifiedBy("admin");
			usersService.update(selectedUser);
			setInsertDelete(true);
			if(usersList == null || insertDelete == true) {			
				usersList = usersService.findAll();
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("SUCCESS : User " +selectedUser.getUsername()+ "  has been updated."));
		}catch(Exception e){
   		    logger.error("UsersManagedBean.doUpdateUser:Error while updating  user: "+selectedUser.getUsername(),e);
			FacesMessage msg = new FacesMessage("FAILURE : Not able to update user in the System ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);  
			FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
	}*/
	
	/*public void deleteUser() {
		System.out.println("Username is going to be deleted is : "
				+ selectedUser.getUsername());
		if (!"admin".equalsIgnoreCase(selectedUser.getUsername())
				&& usersService.deleteUser(selectedUser.getUsername()) > 0)
			System.out.println("Deleted successfully");
		else
			System.out.println("Could not delete. Try later..");
	}*/
	
	public void populatePickList(){
		serviceSet = serviceParametersService.findAllInSet();
		unassignedServiceSet = new HashSet<ServicesParameters>();
		this.unassignedServiceSet.addAll(this.serviceSet);
		List<ServicesParameters> unassignedServiceList = new ArrayList<ServicesParameters>();
		unassignedServiceList.addAll(unassignedServiceSet);
		List<ServicesParameters> assignedServiceList = new ArrayList<ServicesParameters>();
		this.dualServiceList = new DualListModel<ServicesParameters>(unassignedServiceList, assignedServiceList);	
		
	}
	
	/*public void doDeleteUser() {
		System.out.println("Username is going to be deleted is : "
				+ selectedUser.getUsername());
		if (!"admin".equalsIgnoreCase(selectedUser.getUsername())
				&& usersService.deleteUser(selectedUser.getUsername()) > 0)
			System.out.println("Deleted successfully");
		else
			System.out.println("Could not delete. Try later..");
		
		try {
			logger.info("UsersManagedBean.doDeleteUser is deleting new user:"+selectedUser.getUsername());

			loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
			
			getUsersService().delete(selectedUser.getId());
			setInsertDelete(true);
			if(usersList == null || insertDelete == true) {			
				usersList = usersService.findAll();
			}
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.UPDATED,SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),getLoggedInUser().getUsername(), getLoggedInUser().getUsername() + " has deleted User" +selectedUser.getUsername());
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("SUCCESS : User "+selectedUser.getUsername()+ " has been deleted."));
				
			
		//	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Info",getExcptnMesProperty("info.role.delete")));
		//	new Refresh().refreshPage();
		} catch (BFTException e) {
   		    logger.error("UsersManagedBean.doDeleteUser:Error while deleting  user: "+selectedUser.getUsername(),e);
			FacesMessage msg = new FacesMessage("FAILURE : Not able to delete user in the System");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR); 
			FacesContext.getCurrentInstance().addMessage(null, msg);  
		}	
		
	}*/

	public boolean isInsertDelete() {
		return insertDelete;
	}

	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}
	
	public void showDailogue(){
		populatePickList();
		RequestContext.getCurrentInstance().execute("PF('newUserDialog').show()");
        return;

	}
	
	
	public List<ServicesParameters> getServiceListBy(String username){
		Users suser=usersService.findByUsername(username);
		this.assignedServiceSet = suser.getUserServices();
		List<ServicesParameters> assignedServiceList = new ArrayList<ServicesParameters>();
		assignedServiceList.addAll(assignedServiceSet);
		return assignedServiceList;
		
	}
	
	
	

}
