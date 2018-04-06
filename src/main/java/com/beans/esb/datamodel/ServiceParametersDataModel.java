package com.beans.esb.datamodel;
import java.util.List;

import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

import com.beans.esb.model.ServicesParameters;


public class ServiceParametersDataModel extends ListDataModel<ServicesParameters> implements SelectableDataModel<ServicesParameters>{

     public ServiceParametersDataModel(){
    	 
     }
     
     public ServiceParametersDataModel(List<ServicesParameters> data){
    	 super(data);
     }
     
     @SuppressWarnings("unchecked")
 	public ServicesParameters getRowData(String rowKey) {
 		
 		List<ServicesParameters> systemParametersList = (List<ServicesParameters>)getWrappedData();
 		Integer rowKeyInt = Integer.parseInt(rowKey);
 		  for(ServicesParameters systemParameters : systemParametersList) {
 	          if(systemParameters.getId() == rowKeyInt) {
 	              return systemParameters;
 	          }
 	      }
 		return null;
 	}
     
     public Object getRowKey(ServicesParameters systemParameters) {
 		
 		return systemParameters.getId();
 	}
}
