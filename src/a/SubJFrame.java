package a;

import javax.swing.*;

public class SubJFrame extends JFrame
{
	public SubJFrame(){}
	public SubJFrame(String title)
	{
		super(title);
	}
	
	protected void frameInit()
	{
		super.frameInit();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}