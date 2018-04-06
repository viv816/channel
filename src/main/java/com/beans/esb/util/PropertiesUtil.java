package com.beans.esb.util;

import java.util.ResourceBundle;
import java.util.Locale;


 
public class PropertiesUtil {

 
	private static ResourceBundle appExceptionMsgBundle = ResourceBundle.getBundle("ErrorMessages", Locale.getDefault());
	
	public static ResourceBundle getAppExceptionMsgBundle() {
		return appExceptionMsgBundle;
	}
	
	public static void setAppExceptionMsgBundle(ResourceBundle appExceptionMsgBundle) {
		PropertiesUtil.appExceptionMsgBundle = appExceptionMsgBundle;
	}
	
	public static String getExcptnMesProperty(String key)
    {
		if(appExceptionMsgBundle != null && appExceptionMsgBundle.containsKey(key)){
			return appExceptionMsgBundle.getString(key).intern();
		} else{
			return "Error in processing your request, please try after sometime!!!";
		}
    }


}
