package com.beans.esb.model;

import java.io.Serializable;
import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name="serviceParameters")
public class ServicesParameters  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@Column(name="servicename", nullable=false , length=30) 
	private String serviceName;
	
	@Lob
	@Column(name="servicecode", nullable=false) 
	private String serviceCode;    	
	
	@Column(name="description" , nullable=false , length=255)
	private String description; 
	
	@Column(name="createdBy",nullable=true)
	private String createdBy;
	
	@Column(name="creationTime",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date creationTime;
	
	@Column(name="lastModifiedBy",nullable=true)
	private String lastModifiedBy;
	
	@Column(name="lastModifiedTime",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date lastModifiedTime;
	
	private boolean isDeleted = false;
	private boolean active=true;

	@Column(name = "isDeleted", columnDefinition = "NUMBER(1)")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
    public ServicesParameters(){
    	
    }
  
     public int getId() {
 		return id;
 	}

 	public void setId(int id) {
 		this.id = id;
 	}

 	
 	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
 	
	public String getCreatedBy() {
		return createdBy;
	}
 	
 	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
 	
 	public java.util.Date getCreationTime() {
		return creationTime;
	}
 	
 	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
 	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
 	
 	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
 	
 	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}
 	
 	
 	@Column(name="isDeleted", columnDefinition="NUMBER(1)") 
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}		
	
 	@Override
	public boolean equals(Object obj) {
		ServicesParameters inputSystemParameters = (ServicesParameters) obj;
		if(getId() == inputSystemParameters.getId()) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public int hashCode() {
		return id;
	}
		
 }
