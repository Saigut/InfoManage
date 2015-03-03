package a;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

public class Panel1 extends Panel
{

	JTextArea textArea=new JTextArea();
	JLabel labelL=new JLabel("                  ");
	JLabel labelR=new JLabel("                  ");
	
	public Panel1(String s)
	{
		super(s);
		
		textArea.setText("\n\n\n\n    xxxx公司信息管理软件");
		textArea.setEditable(false);
		textArea.setBackground(new Color(0xeeeeee));

		panel.add(textArea,BorderLayout.CENTER);

		panel.add(labelL,BorderLayout.WEST);
		panel.add(labelR,BorderLayout.EAST);
	}

	public JPanel rtn()
	{
		return panel;
	}
}
