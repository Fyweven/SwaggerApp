package com.fyw.youkuir;


import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.fyw.swagger.SwaggeDefi;
import com.fyw.swagger.SwaggerPara;
import com.fyw.swagger.SwaggerPath;
import com.fyw.swagger.SwaggerProp;
import com.fyw.swagger.SwaggerResult;

import static com.fyw.frame.SwaggerFrame.aTextArea;
import static com.fyw.youkuir.YoukuConstans.*;

public class YoukuIR {
	
	private static SwaggeDefi swaggeDefi = new SwaggeDefi();
	private static SwaggerResult swaggerResult = new SwaggerResult();
	private static SwaggerPath paths = new SwaggerPath();
	public static int counter = 0;
	private static String tempJO = "";
	private static String temp_name = "";
	private static int res_num = 0;
	private static int temp_num = 0;
	
	public static void ykIR() throws Exception {
		
		init();		
		for(int i=0; i<3; i++){
			getResObject(YK_RESPONSE_NUM[i], YK_RESPONSE_URL[i]);
		}
		getYoukuPath();
		getError();
		swaggerResult.addJO("definitions", swaggeDefi.getDefiOJ());
		String result = swaggerResult.getResultJO().toString(3);
		
		aTextArea.setText(result);
		aTextArea.setCaretPosition(0);
	}
	
	private static void init(){
		swaggerResult.addPara("swagger", SWAGGER_VERSION);
		JSONObject infoJO = new JSONObject();
		infoJO.put("title", YOUKU_TITLE);
		infoJO.put("description", YOUKU_DESCRIP);
		infoJO.put("version", YOUKU_APIVERSION);
		swaggerResult.addJO("info", infoJO);
		swaggerResult.addPara("host", YOUKU_HOST);
		JSONArray aJA = new JSONArray();
		aJA.add(YOUKU_SCHEMES);
		swaggerResult.addJA("schemes", aJA);
		aJA.clear();
		swaggerResult.addPara("basePath", YOUKU_BASEPATH);
		aJA.add(YOUKU_PRODUCES);
		swaggerResult.addJA("produces", aJA);
	}
	
	private static void getYoukuPath() throws IOException{
		for(int i=0;i<7;i++){
			Document aDoc = Jsoup.connect(YK_IR_URL[i]).get();
			Elements elements1 = aDoc.getElementsByAttributeValue("class", "reference external");
			for(int j=0;j<elements1.size();j++){
				String c_url = "http://cloud.youku.com/"+elements1.eq(j).attr("href").substring(1);
				if(counter < 31&&!c_url.endsWith("49")&&!c_url.endsWith("47"))
					getApiContent(c_url);
			}
		}
		swaggerResult.addJO("paths", paths.getPathJo());
	}
	
	private static void getApiContent(String aUrl) throws IOException{
		Document aDoc = Jsoup.connect(aUrl).timeout(5000).get();
		String c_sum = aDoc.select("h1").text();		
		Elements elements1 = aDoc.select("ol");
		Elements elements2 = elements1.select("li"); 
		Elements info_element = elements2.eq(0);
		Elements para_element = elements2.eq(1).select("tbody").eq(1).select("tr");
		String c_url = info_element.select("a").text();
		String c_operation = info_element.select("td").eq(1).text().toLowerCase();
		c_url = c_url.substring(28);
		String c_tag = c_url.substring(1,c_url.indexOf("/",1));
		
		String c_in = "";
		if(c_operation.equals("post"))
			c_in = "formData";
		else if(c_operation.equals("get"))
			c_in = "query";
		
		SwaggerPara c_paras = new SwaggerPara(); 
		for(int i=0; i<para_element.size(); i++){
			Elements cpara_element = para_element.eq(i).select("td");
			String c_name = cpara_element.eq(0).text();
			String c_type = cpara_element.eq(1).text();
			boolean c_isre = cpara_element.eq(2).text().equals("true");
			String c_desc = cpara_element.eq(4).text();
			if(c_type.equals("json string")||c_type.equals("int/string")) 
				c_type = "string";
			c_paras.addPara(c_name, c_in, c_desc, c_isre, c_type);
		}
		String c_res = getResponse(c_tag, elements2);
		paths.addPath(c_url, c_operation, c_tag, c_paras, c_sum, c_res);
		System.out.println(counter++);
	}
	
	private static void getResObject(int aNum, String aUrl) throws IOException{
		Document aDoc = Jsoup.connect(aUrl).timeout(5000).get();		
		Elements elements1 = aDoc.select("ol");
		Elements elements2 = elements1.select("li");
		Elements res_elements = elements2.eq(2).select("table"); 
		SwaggerProp d_Prop = new SwaggerProp();
		
		for(int j=1; j<aNum; j++){
			
			Elements tr_elements = res_elements.eq(j).select("tr"); 
			for(int i=1; i<tr_elements.size(); i++){
				Elements c_Elements = tr_elements.eq(i).select("td");
				String c_name = c_Elements.eq(0).text();
				String c_type = c_Elements.eq(1).text();
				String c_des = c_Elements.last().text();
				d_Prop.addProp(c_name, c_type, c_des);
			}
			swaggeDefi.addObject(YK_RESPONSE_NAME[res_num++], d_Prop);
		}
	}
	
	private static String getResponse(String aName, Elements aElements){
		
		Elements res_elements = aElements.eq(2).select("table"); 
		SwaggerProp c_Prop = new SwaggerProp();
		Elements tr_elements = res_elements.eq(0).select("tr");
		for(int i=1; i<tr_elements.size(); i++){
			Elements c_Elements = tr_elements.eq(i).select("td");
			String c_name = c_Elements.eq(0).text();
			String c_type = c_Elements.eq(1).text();
			String c_des = c_Elements.last().text();
			if(c_type.equals("object")){
				int x;
				for(x=0; x<4; x++){
					if(YK_RESPONSE_NAME[x].equals(c_name))
						break;
				}
				if(x<4)
				   c_Prop.addObjectRef(c_name, c_name);
				else
					c_Prop.addProp(c_name, c_type, c_des);	
			}
			else
				c_Prop.addProp(c_name, c_type, c_des);		
		}
		if(counter==7||counter==8){
			c_Prop.addArrayRef("show", "show");
			
		}
		if(aName.equals(temp_name)){
			if(c_Prop.getPropJO().toString().equals(tempJO)){
				aName = aName+temp_num;
			}else{
				aName = aName + (++temp_num);
				swaggeDefi.addObject(aName, c_Prop);
				tempJO = c_Prop.getPropJO().toString();
			}
		}else{
			temp_name = aName;
			temp_num = 0;
			aName = aName + temp_num;
			swaggeDefi.addObject(aName, c_Prop);
			tempJO = c_Prop.getPropJO().toString();
		}
		return aName;
	}
	
	private static void getError() {
		SwaggerProp erroeProp = new SwaggerProp();
		erroeProp.addProp("code", "integer", "");
		erroeProp.addProp("type", "string", "");
		erroeProp.addProp("description", "string", "");
		swaggeDefi.addObject("Error", erroeProp);
	}
}
