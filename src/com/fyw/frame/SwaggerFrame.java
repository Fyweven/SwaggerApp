package com.fyw.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.fyw.weiboir.WeiboIR;
import com.fyw.youkuir.YoukuIR;
import com.fyw.youtubeir.YoutubeIR;


@SuppressWarnings("serial")
public class SwaggerFrame extends JFrame{
	
	public static JTextArea aTextArea;
	final CustomFrame customFrame = new CustomFrame();
	
	public SwaggerFrame(String title) {
		super(title);
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		aTextArea = new JTextArea(); 
		aTextArea.setFont(new Font("Serif", 0, 20));
		JScrollPane scrollPane = new JScrollPane(aTextArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane,BorderLayout.CENTER);
		
		final JTextField aTextField = new JTextField("请选择你要获取的Swagger API描述");
		aTextField.setFont(new Font("Serif", 0, 20));
		JPanel textPanel = new JPanel(new GridLayout());
		textPanel.add(aTextField);
		this.add(textPanel,BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton aButton = new JButton("Weibo");
		aButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					WeiboIR.wbIR();
					aTextField.setText("成功获取微博API");
				} catch (IOException e1) {
					aTextField.setText("获取微博API失败。");
				}
			}
		});
		JButton bButton = new JButton("Youku");
		bButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					YoukuIR.ykIR();
					aTextField.setText("成功获取优酷API");
				} catch (Exception e1) {
					aTextArea.setText("获取优酷API失败。");
				}
			}
		});
		JButton dButton = new JButton("Youtube");
		dButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					YoutubeIR.ytIR();
					aTextField.setText("成功获取Youtube API");
				} catch (Exception e1) {
					aTextArea.setText("获取Youtube API失败。");
				}
			}
		});
		JButton cButton = new JButton("自定义");
		cButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				SwaggerFrame.this.setVisible(false);
				customFrame.setVisible(true);
			}
		});
		
		buttonPanel.add(aButton);
		buttonPanel.add(bButton);
		buttonPanel.add(dButton);
		buttonPanel.add(cButton);
		this.add(buttonPanel,BorderLayout.SOUTH);
		this.setVisible(false);
	}
	
}
