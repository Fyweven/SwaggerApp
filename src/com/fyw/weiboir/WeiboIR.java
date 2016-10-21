package com.fyw.weiboir;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fyw.swagger.SwaggeDefi;
import com.fyw.swagger.SwaggerPara;
import com.fyw.swagger.SwaggerPath;
import com.fyw.swagger.SwaggerProp;
import com.fyw.swagger.SwaggerResult;

import static com.fyw.frame.SwaggerFrame.aTextArea;
import static com.fyw.weiboir.WeiboConstans.*;

public class WeiboIR {
	
	private static SwaggeDefi swaggeDefi;
	private static SwaggerResult swaggerResult;
	private static SwaggerPath paths;
	private static int counter = 0;
	
	public static void wbIR() throws IOException{
		init();
		getWeiboPath();
		getResponse();
		getError();
		swaggerResult.addJO("definitions", swaggeDefi.getDefiOJ());
		String result = swaggerResult.getResultJO().toString(3);
		aTextArea.setText(result);
		aTextArea.setCaretPosition(0);
	}
	
	private static void init() {
		paths = new SwaggerPath();
		swaggeDefi = new SwaggeDefi();
		swaggerResult = new SwaggerResult();
		swaggerResult.addPara("swagger", SWAGGER_VERSION);
		JSONObject infoJO = new JSONObject();
		infoJO.put("title", WEIBO_TITLE);
		infoJO.put("description", WEIBO_DESCRIP);
		infoJO.put("version", WEIBO_APIVERSION);
		swaggerResult.addJO("info", infoJO);
		swaggerResult.addPara("host", WEIBO_HOST);
		JSONArray aJA = new JSONArray();
		aJA.add(WEIBO_SCHEMES);
		swaggerResult.addJA("schemes", aJA);
		aJA.clear();
		swaggerResult.addPara("basePath", WEIBO_BASEPATH);
		aJA.add(WEIBO_PRODUCES);
		swaggerResult.addJA("produces", aJA);
	}
	
	private static void getWeiboPath() throws IOException {
		Document aDoc = Jsoup.connect(WB_HOME_URL).get();
       
		Elements elements = aDoc.select("table").select("a[href]");
		for (Element element : elements) {
            String linkString = element.attr("href"); 
            if(linkString.startsWith("/wiki/2/") && counter < 12){
               getApiContent("http://open.weibo.com"+linkString);
            }
	    }
		swaggerResult.addJO("paths", paths.getPathJo());
	}

	private static void getApiContent(String aUrl) throws IOException {
		Document aDoc = Jsoup.connect(aUrl).timeout(8000).get();
		Elements urlElement = aDoc.getElementsByAttributeValue("class", "external free");
		String c_sum = aDoc.select("p").eq(0).text();
		
		String cur_url = (urlElement.text().trim());
		cur_url = cur_url.substring(cur_url.indexOf('2')+1);
		String cur_operation = aDoc.select("p").eq(3).text().toLowerCase();
		String cur_tag = "";
		if(cur_url.indexOf('/', 1)>0)
			   cur_tag = cur_url.substring(1,cur_url.indexOf('/', 1));
		else
			   cur_tag = cur_url.substring(1);
		String c_in = "";
		if(cur_operation.equals("post"))
			c_in = "formData";
		else if(cur_operation.equals("get"))
			c_in = "query";
		Elements para_elements = aDoc.select("table").first().select("tr");
		int cur_num = para_elements.size();
		SwaggerPara cur_para = new SwaggerPara();
		for(int i=1; i<cur_num; i++){
			Elements cpara_elements = para_elements.eq(i).select("td");
			String cur_name = cpara_elements.eq(0).text();
			boolean cur_isre = cpara_elements.eq(1).text().equals("true");
			String cur_type = cpara_elements.eq(2).text();
			String cur_desc = cpara_elements.eq(3).text();
			cur_para.addPara(cur_name, c_in, cur_desc, cur_isre, cur_type);
		}
		String cur_res = "0";
		if(aDoc.select("table").eq(1).hasText()){
			cur_res = "status";
			if(cur_tag.equals("comments")){
				cur_res = "comment";
			}else if(cur_tag.equals("users")){
				cur_res = "user";
			}else if(cur_tag.equals("friendships")){
				cur_res = "user";
			}else if(cur_tag.equals("remind")){
				cur_res = "remind";
			}else if(cur_tag.equals("short_url")){
				cur_res = "url_short";
			}else if(cur_tag.equals("location")){
				cur_res = "geo";
			}
		}		
		paths.addPath(cur_url, cur_operation, cur_tag, cur_para, c_sum, cur_res);
		System.out.println(counter++);
	}
	
	private static void getResponse() throws IOException {
		Document aDoc = Jsoup.connect(WB_RESPONSE_URL).timeout(5000).get();
		Elements res_elements = aDoc.select("table");
		for(int i=0; i<7; i++){
			Elements prop_elements = res_elements.eq(i).select("tr"); 
			SwaggerProp cur_prop = new SwaggerProp();
			for(int j=1; j<prop_elements.size(); j++){
				Elements cur_elements = prop_elements.eq(j).select("td");
				String cur_name = cur_elements.eq(0).text();
				String cur_type = cur_elements.eq(1).text();
				String cur_desc = cur_elements.eq(2).text();
				if(cur_type.equals("object array")) cur_type = "array"; //问题
				cur_prop.addProp(cur_name, cur_type, cur_desc);
			}
			swaggeDefi.addObject(WB_RESPONSE_NAME[i], cur_prop);
		}
	}
	
	private static void getError() {
		SwaggerProp erroeProp = new SwaggerProp();
		erroeProp.addProp("request", "string", "");
		erroeProp.addProp("error_code", "integer", "");
		erroeProp.addProp("error", "string", "");
		swaggeDefi.addObject("Error", erroeProp);
	}

}
