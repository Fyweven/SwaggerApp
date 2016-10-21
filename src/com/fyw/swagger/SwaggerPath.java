package com.fyw.swagger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SwaggerPath {
	
	private JSONObject pathsJO;

	public SwaggerPath() {
		pathsJO = new JSONObject();
	}
	
	public void addPath(String aPath,String anOperation,String aTag,SwaggerPara aParas,String aSum,String aRes){
		JSONObject pathJO = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("summary", aSum);
		ja.add(aTag);
		jo1.put("tags", ja);
		ja.clear();
		if(anOperation.equals("post")){
			ja.add("application/x-www-form-urlencoded");
			jo1.put("consumes", ja);
			ja.clear();
		}
		
		jo1.put("parameters", aParas.getParaJA());
		
		SwaggerResp aResp;
		if(!aRes.equals("0")){
		    aResp = new SwaggerResp(aRes);
		}else{
			aResp = new SwaggerResp();
		}
		jo1.put("responses", aResp.getRespJO());
		
		if(pathsJO.has(aPath)){
			pathJO = JSONObject.fromObject(pathsJO.get(aPath));
			pathJO.element(anOperation, jo1);
		}else{
			pathJO.element(anOperation, jo1);
		}
		pathsJO.element(aPath, pathJO);
	}
	
	public void addPath(String aPath, String anOperation,String aTag,SwaggerPara aParas,String aSum,SwaggerResp aRes){
		JSONObject pathJO = new JSONObject();
		JSONArray ja = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("summary", aSum);
		ja.add(aTag);
		jo1.put("tags", ja);
		ja.clear();
		if(anOperation.equals("post")){
			ja.add("application/x-www-form-urlencoded");
			jo1.put("consumes", ja);
			ja.clear();
		}
		
		jo1.put("parameters", aParas.getParaJA());
		
		
		jo1.put("responses", aRes.getRespJO());

		if(pathsJO.has(aPath)){
			pathJO = JSONObject.fromObject(pathsJO.get(aPath));
			pathJO.element(anOperation, jo1);
		}else{
			pathJO.element(anOperation, jo1);
		}
		pathsJO.element(aPath, pathJO);
	}
	
	public JSONObject getPathJo() {
		return pathsJO;
	}

}
