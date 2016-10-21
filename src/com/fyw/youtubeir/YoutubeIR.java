package com.fyw.youtubeir;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fyw.swagger.SwaggeDefi;
import com.fyw.swagger.SwaggerPara;
import com.fyw.swagger.SwaggerPath;
import com.fyw.swagger.SwaggerProp;
import com.fyw.swagger.SwaggerResp;
import com.fyw.swagger.SwaggerResult;

import static com.fyw.frame.SwaggerFrame.aTextArea;
import static com.fyw.youtubeir.YoutubeConstans.*;

public class YoutubeIR {

	private static SwaggeDefi swaggeDefi = new SwaggeDefi();
	private static SwaggerResult swaggerResult = new SwaggerResult();
	private static SwaggerPath paths = new SwaggerPath();
	private static int counter = 0;
	
	public static void ytIR() throws IOException{
		init();
		getYoutubePath();
		getError();
		swaggerResult.addJO("definitions", swaggeDefi.getDefiOJ());
		String result = swaggerResult.getResultJO().toString(3);
		
		aTextArea.setText(result);
		aTextArea.setCaretPosition(0);
	}
	
	private static void init(){
		swaggerResult.addPara("swagger", SWAGGER_VERSION);
		JSONObject infoJO = new JSONObject();
		infoJO.put("title", YOUTUBE_TITLE);
		infoJO.put("description", YOUTUBE_DESCRIP);
		infoJO.put("version", YOUTUBE_APIVERSION);
		swaggerResult.addJO("info", infoJO);
		swaggerResult.addPara("host", YOUTUBE_HOST);
		JSONArray aJA = new JSONArray();
		aJA.add(YOUTUBE_SCHEMES);
		swaggerResult.addJA("schemes", aJA);
		aJA.clear();
		swaggerResult.addPara("basePath", YOUTUBE_BASEPATH);
		aJA.add(YOUTUBE_PRODUCES);
		swaggerResult.addJA("produces", aJA);
	}
	
	private static void getYoutubePath() throws IOException{
		Document aDoc = Jsoup.connect(YTB_HOME_URL).timeout(8000).get();
		Elements aElements = aDoc.select("nav").eq(3).select("a");
		for(Element e : aElements){
			String c_url = e.attr("href");
			if(c_url.length()>47){
				int sub = c_url.indexOf('/',48);
				if(sub != -1 && counter<8)
				  getApicontent(e.attr("href"));
			}
		}
		swaggerResult.addJO("paths", paths.getPathJo());
	}
	
	private static void getApicontent(String aUrl) throws IOException{
		Document aDoc = Jsoup.connect(aUrl).timeout(8000).get();
	    String c_sum = aDoc.select("p").eq(0).text();
	    String c_info = aDoc.select("pre").eq(0).text();
	    int c_sub = c_info.indexOf(' ');
	    int c_aub = c_info.indexOf('3')+1;	    
	    String c_url = c_info.substring(c_aub);
	    System.out.println(c_url);
	    String c_oper = c_info.substring(0,c_sub).toLowerCase();
	    String c_tag;
	    c_sub = c_url.indexOf('/',1);
	    if(c_url.indexOf('/',1)==-1)
	    	c_tag = c_url.substring(1);
	    else
	    	c_tag = c_url.substring(1,c_sub);
	    
	    Elements aElements = aDoc.getElementsByAttributeValue("id", "params").select("table").select("tr");
	    boolean  a = true;
	    SwaggerPara c_para = new SwaggerPara();
	    for(int i = 1; i<aElements.size(); i++){
	    	String tempStr = aElements.eq(i).text(); 
	    	if(tempStr.startsWith("Required"))
	    		continue;
	    	else if(tempStr.startsWith("Filters")){
	    		a = false;
	    		continue;
	    	}else if(tempStr.startsWith("Optional")){
	    		a = false;
	    		continue;
	    	}
	    	Elements bElements =  aElements.eq(i).select("td");
	    	String c_name = bElements.first().text();
	    	String c_type = bElements.last().select("code").first().text().toString();
	    	String c_des = bElements.last().text().substring(c_type.length()+1);
	    	c_para.addPara(c_name, "query", c_des, a, c_type);
	    }
	    Elements cElements = aDoc.getElementsByAttributeValue("id", "properties");
	    if(cElements.hasText())
	         paths.addPath(c_url, c_oper, c_tag, c_para, c_sum,getResponse(cElements));
	    else
	    	paths.addPath(c_url, c_oper, c_tag, c_para, c_sum,"0");
	    System.out.println(counter++);
	}
	
	private static SwaggerResp getResponse(Elements eElements){
		Elements resElements = eElements.select("tr");
		SwaggerProp resProp = new SwaggerProp();
		for(int i = 1; i<resElements.size(); i++){ 
	    	Elements bElements = resElements.eq(i).select("td");
	    	String c_name = bElements.first().text();
	    	String c_type = bElements.last().select("code").first().text().toString();
	    	String c_des = bElements.last().text().substring(c_type.length()+1);
	    	if(c_type.equals("list")) c_type = "array";
	    	resProp.addProp(c_name, c_type, c_des);
	    }
		JSONObject aJO = new JSONObject();
		aJO.element("properties", resProp.getPropJO());
		SwaggerResp curResp= new SwaggerResp(aJO); 
		return curResp;
	}
	
	private static void getError() {
		SwaggerProp erroeProp = new SwaggerProp();
		erroeProp.addProp("Error type", "string", "");
		erroeProp.addProp("Error detail", "string", "");
		erroeProp.addProp("Description", "string", "");
		swaggeDefi.addObject("Error", erroeProp);
	}
	
}
