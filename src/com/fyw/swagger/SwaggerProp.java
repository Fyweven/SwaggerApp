package com.fyw.swagger;

import net.sf.json.JSONObject;

public class SwaggerProp {

	private JSONObject propJO;
	
	public SwaggerProp() {
		propJO = new JSONObject();
	}
	
	public void addArrayRef(String aName, String aRef){
		JSONObject JO1 = new JSONObject();
		JSONObject JO2 = new JSONObject();
		JO1.put("$ref", "#/definitions/"+aRef);
		JO2.put("type", "array");
		JO2.put("items", JO1);
		propJO.element(aName, JO2);
	}
	
	public void addObjectRef(String aName, String aRef){
		JSONObject JO1 = new JSONObject();
		JO1.put("$ref", "#/definitions/"+aRef);
		propJO.element(aName, JO1);
	}
	
	public void addProp(String aName, String aType, String aDesc) {
		JSONObject JO1 = new JSONObject();
		if(aType.equals("string")||aType.equals("object")){
			JO1.put("type", aType);
		}else if(aType.equals("int")||aType.equals("integer")){
			JO1.put("type", "integer");
			JO1.put("format", "int32");
		}else if(aType.equals("int64")||aType.equals("long")){
			JO1.put("type", "integer");
			JO1.put("format", "int64");
		}else if(aType.equals("double")||aType.equals("float")){
			JO1.put("type", "number");
			JO1.put("format", aType);
		}else if (aType.equals("bool")||aType.equals("boolean")) {
			JO1.put("type", "boolean");
		}else if(aType.equals("binary")||aType.equals("byte")||aType.equals("date")){
			JO1.put("type", "string");
			JO1.put("format", aType);
		}else if(aType.equals("array")){
			JSONObject JO2 = new JSONObject();
			JO1.put("type", aType);
			JO1.put("items", JO2);
		}
		if(!aDesc.isEmpty())
		    JO1.put("description",aDesc);
		propJO.element(aName, JO1);
	}
	
	public void clear(){
		propJO.clear(); 
	}
	
	public JSONObject getPropJO() {
		return propJO;
	}
	
}
