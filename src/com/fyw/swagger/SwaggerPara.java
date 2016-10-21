package com.fyw.swagger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SwaggerPara {
	
private JSONArray paraJA ;

	public SwaggerPara() {
		paraJA = new JSONArray();
	}

	public void addPara(String aName, String aIn, String aDescrip, boolean isRequired, String aType) {
		JSONObject paraJO = new JSONObject();
		paraJO = new JSONObject();
		paraJO.put("name",aName);
		paraJO.put("in", aIn);
		paraJO.put("description", aDescrip);
		paraJO.put("required", isRequired);
		
		if(aType.equals("string")||aType.equals("boolean")){
			paraJO.put("type", aType);
		}else if(aType.equals("int")||aType.equals("integer")||aType.equals("unsigned integer")){
			paraJO.put("type", "integer");
			paraJO.put("format", "int32");
		}else if(aType.equals("int64")||aType.equals("long")){
			paraJO.put("type", "integer");
			paraJO.put("format", "int64");
		}else if(aType.equals("double")||aType.equals("float")){
			paraJO.put("type", "number");
			paraJO.put("format", aType);
		}else if(aType.equals("binary")||aType.equals("byte")||aType.equals("date")){
			paraJO.put("type", "string");
			paraJO.put("format", aType);
		}else if(aType.equals("datetime")){
			paraJO.put("type", "string");
			paraJO.put("format", "date-time");
		}else if(aType.equals("bool")){
			paraJO.put("type", "boolean");
		}
		
		paraJA.add(paraJO);
	}
	
	public JSONArray getParaJA(){
		return paraJA;
	}
	
	public void clear(){
		paraJA.clear();
	}

}
