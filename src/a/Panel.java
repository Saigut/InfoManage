package a;

import java.awt.*;

import javax.swing.*;

public class Panel
{

	JPanel panel=new JPanel();
	JLabel labelL=new JLabel("       ");
	JLabel labelR=new JLabel("       ");
	
	public Panel(String s)
	{
		panel.setLayout(new BorderLayout());
		panel.setName(s);

		panel.add(labelL,BorderLayout.WEST);
		panel.add(labelR,BorderLayout.EAST);
	}

	public JPanel rtn()
	{
		return panel;
	}
	
	
}
