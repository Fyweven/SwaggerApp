package com.fyw.swagger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SwaggerResult {
	
	private JSONObject resultJO;
	public SwaggerResult() {
		resultJO = new JSONObject();
	}
	public JSONObject getResultJO() {
		return resultJO;
	}
	public void addJO(String aName, JSONObject aJO){
		resultJO.put(aName, aJO);
	}
	public void addPara(String aName, String aString) {
		resultJO.put(aName, aString);
	}
	public void addJA(String aName, JSONArray aJA) {
		resultJO.put(aName, aJA);
	}
}
