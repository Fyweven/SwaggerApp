package com.fyw.swagger;

import net.sf.json.JSONObject;

public class SwaggerResp {
	
	private JSONObject respJO;
	public SwaggerResp() {
		respJO = new JSONObject();
		JSONObject aJO = new JSONObject();
		JSONObject bJO = new JSONObject();
		bJO.element("$ref", "#/definitions/Error");
		aJO.put("description", "Unexpected error");
		aJO.put("schema", bJO);
		respJO.put("default", aJO);
	}
	public SwaggerResp(String aRef) {
		respJO = new JSONObject();
		JSONObject aJO = new JSONObject();
		JSONObject bJO = new JSONObject();
		bJO.element("$ref", "#/definitions/"+aRef);
		aJO.put("description", "OK");
		aJO.put("schema", bJO);
		respJO.put("200", aJO);
		aJO.clear();
		bJO.clear();
		bJO.element("$ref", "#/definitions/Error");
		aJO.put("description", "Unexpected error");
		aJO.put("schema", bJO);
		respJO.put("default", aJO);
	}
	public SwaggerResp(JSONObject aRes){
		respJO = new JSONObject();
		JSONObject aJO = new JSONObject();
		JSONObject bJO = new JSONObject();
		aJO.put("description", "OK");
		aJO.put("schema", aRes);
		respJO.put("200", aJO);
		aJO.clear();
		bJO.element("$ref", "#/definitions/Error");
		aJO.put("description", "Unexpected error");
		aJO.put("schema", bJO);
		respJO.put("default", aJO);
	}
	public JSONObject getRespJO() {
		return respJO;
	}

}
