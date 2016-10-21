package com.fyw.frame;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fyw.swagger.SwaggeDefi;
import com.fyw.swagger.SwaggerPara;
import com.fyw.swagger.SwaggerPath;
import com.fyw.swagger.SwaggerProp;
import com.fyw.swagger.SwaggerResult;

@SuppressWarnings("serial")
public class CustomFrame extends JFrame{

	private static SwaggeDefi swaggeDefi = new SwaggeDefi();
	private static SwaggerResult swaggerResult = new SwaggerResult();
	private static SwaggerPara swaggerPara = new SwaggerPara();
	private static SwaggerPath paths = new SwaggerPath();
	private static SwaggerProp swaggerProp = new SwaggerProp();
	
	public CustomFrame() {
		super("自定义参数");
		this.setSize(1100, 700);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		final myText tiText = new myText(10);
		final myText veText = new myText(10);
		final myText hoText = new myText(10);
		final myText scText = new myText(10);
		final myText baText = new myText(10);
		
		JPanel aPanel = new JPanel(new FlowLayout());
		
		aPanel.add(new myButton("Title:"));
		aPanel.add(tiText);
		aPanel.add(new myButton("Version:"));
		aPanel.add(veText);
		aPanel.add(new myButton("Host:"));
		aPanel.add(hoText);
		aPanel.add(new myButton("Schemes:"));
		aPanel.add(scText);
		aPanel.add(new myButton("Basepath:"));
		aPanel.add(baText);	
		JButton aButton = new JButton("添加Info");
		aButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				init(tiText.getText(),veText.getText(),hoText.getText(),scText.getText(),baText.getText());
			}
		});
		aPanel.add(aButton);
		
		final myText paText = new myText(13);
		final myText meText = new myText(13);
		final myText taText = new myText(13);
		final myText rsobText = new myText(13);	
		JPanel bPanel = new JPanel(new FlowLayout());
		bPanel.add(new myButton("Path:"));
		bPanel.add(paText);
		bPanel.add(new myButton("Method:"));
		bPanel.add(meText);
		bPanel.add(new myButton("Tag:"));
		bPanel.add(taText);
		bPanel.add(new myButton("返回对象:"));
		bPanel.add(rsobText);
		JButton bButton = new JButton("添加Path");
		bButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				paths.addPath(paText.getText(), meText.getText(), taText.getText(), swaggerPara, null, rsobText.getText());
				swaggerPara.clear();
				paText.setText("");
			}
		});
		bPanel.add(bButton);
		
		JPanel cPanel = new JPanel(new FlowLayout());
		final myText onaText = new myText(15);
		JButton gButton = new JButton("添加Object");
		gButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				swaggeDefi.addObject(onaText.getText(), swaggerProp);
				swaggerProp.clear();
				onaText.setText("");
			}
		});
		cPanel.add(new myButton("Object名称:"));
		cPanel.add(onaText);
		cPanel.add(gButton);
		
		final myText naText = new myText(10);
		final myText inText = new myText(10);
		final myText irText = new myText(10);
		final myText tyText = new myText(10);
		final myText deText = new myText(10);
		JPanel dPanel = new JPanel(new FlowLayout());
		dPanel.add(new myButton("参数名:"));
		dPanel.add(naText);
		dPanel.add(new myButton("类型:"));
		dPanel.add(tyText);
		dPanel.add(new myButton("描述:"));
		dPanel.add(deText);
		dPanel.add(new myButton("位置："));
		dPanel.add(inText);
		dPanel.add(new myButton("是否必须:"));
		dPanel.add(irText);
		
		JButton cButton = new JButton("添加请求参数");
		cButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				swaggerPara.addPara(naText.getText(), inText.getText(), deText.getText(), irText.getText().equals("true"), tyText.getText());
			    naText.setText("");
			}
		});
		
		JButton eButton = new JButton("添加Object参数");
		eButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				swaggerProp.addProp(naText.getText(), tyText.getText(), deText.getText());
				naText.setText("");
			}
		});
		dPanel.add(cButton);
		dPanel.add(eButton);
		
		
		JButton zButton = new JButton("完成");
		zButton.setSize(60, 10);
		zButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				swaggerResult.addJO("paths", paths.getPathJo());
				swaggerResult.addJO("definitions", swaggeDefi.getDefiOJ());
				String result = swaggerResult.getResultJO().toString(3);
				SwaggerFrame.aTextArea.setText(result);
				SwaggerMain.swaggerFrame.setVisible(true);
				CustomFrame.this.setVisible(false);
				System.out.println(swaggerResult.getResultJO().toString());
			}
		});
		
		
		JPanel panel = new JPanel();
		BoxLayout layout=new BoxLayout(panel, BoxLayout.Y_AXIS); 
		panel.setLayout(layout);
		panel.add(aPanel);
		panel.add(bPanel);
		panel.add(cPanel);
		panel.add(dPanel);
		panel.add(zButton);
		this.add(panel);
	}
	
	class myButton extends JButton{
		public myButton(String aName) {
			super(aName);
			this.setEnabled(false);
		}
	}
	
	class myText extends JTextField{
		public myText(int le) {
			super(le);
			this.setFont(new Font("Serif", 0, 16));
		}
	}
	
	private static void init(String aTitle,String aVer,String aHost,String aSche,String aBaph){
		swaggerResult.addPara("swagger", "2.0");
		JSONObject infoJO = new JSONObject();
		infoJO.put("title", aTitle);
		infoJO.put("description", "");
		infoJO.put("version", aVer);
		swaggerResult.addJO("info", infoJO);
		swaggerResult.addPara("host", aHost);
		JSONArray aJA = new JSONArray();
		aJA.add(aSche);
		swaggerResult.addJA("schemes", aJA);
		aJA.clear();
		swaggerResult.addPara("basePath", aBaph);
		aJA.add("application/json");
		swaggerResult.addJA("produces", aJA);
	}
	
	
}
