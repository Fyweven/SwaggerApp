package com.fyw.swagger;

import net.sf.json.JSONObject;

public class SwaggeDefi {
	
	private JSONObject defiJO; 
	public SwaggeDefi() {
		defiJO = new JSONObject();
	}
	
	public void addObject(String aName , SwaggerProp aProp){
		JSONObject aJO = new JSONObject();
		aJO.put("type", "object");
		aJO.put("properties", aProp.getPropJO());
		defiJO.element(aName, aJO);
	}
	
	public JSONObject getDefiOJ(){
		return defiJO;
	}
	
}
