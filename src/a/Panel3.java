package a;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Panel3 extends Panel implements MouseListener,ActionListener
{
	GridLayout grid;
	JPanel subPanel=new JPanel();
	JPanel subPanelL=new JPanel();
	JPanel subPanelC=new JPanel();
	JPanel subPanelCi=new JPanel();
	JPanel subAddnewT=new JPanel();
	
	JTable table;
	JScrollPane scrolltable;
	String[] titles={"序号","姓名","工资","福利"};

	JTable tList;
	JScrollPane scrolltableList;
	String[] lTitle={"选择查看:"};
	Vector<String[]> months;

	JLabel nTitle=new JLabel("薪资表");
	JButton buttonNew=new JButton("刷新列表");
	JButton buttonModify=new JButton("修改");
	JButton buttonOk1=new JButton("确定");
	JButton buttonOk2=new JButton("确定");
	JButton buttonCancel2=new JButton("取消");

	JLabel labelCi=new JLabel("aa");
	
	JLabel labelSalary=new JLabel("工资:");
	JLabel labelWelfare=new JLabel("福利:");
	JTextField tFieldSalary=new JTextField(6);
	JTextField tFieldWelfare=new JTextField(6);
	

	String selectedId,selectedName,selectedSalary,selectedWelfare;

	Vector<String[]> checkins;
	
	String sqlPhrase;
	String maxDate;
	
	public Panel3(String s)
	{
		super(s);
		
		generateList();
		
		grid=new GridLayout(0,1,0,3);
		subPanelL.setLayout(grid);

		subPanelL.add(buttonNew);
		subPanelL.add(scrolltableList);

		panel.add(subPanelL,BorderLayout.WEST);
		
		subPanelC.setLayout(new BorderLayout());
		subPanelCi.setLayout(grid);

		subAddnewT.add(nTitle);
		
		buttonOk1.setActionCommand("Ok1");
		buttonOk2.setActionCommand("Ok2");
		buttonCancel2.setActionCommand("Cancel2");
	
		subPanelC.add(subAddnewT,BorderLayout.NORTH);
		panel.add(subPanelC,BorderLayout.CENTER);
		
		subPanelCi.add(labelCi);
		subPanelCi.add(buttonModify);
		
		subPanel.add(labelSalary);
		subPanel.add(tFieldSalary);
		subPanel.add(labelWelfare);
		subPanel.add(tFieldWelfare);
		subPanel.add(buttonOk2);
		subPanel.add(buttonCancel2);
		
		buttonNew.addActionListener(this);
		buttonModify.addActionListener(this);
		buttonOk1.addActionListener(this);
		buttonOk2.addActionListener(this);
		buttonCancel2.addActionListener(this);
	}
	
	public void generateList()
	{
		Panel2.loadDriver();
		
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INFOMANAGE"
					,"root","Qw1");
			Statement stmt=con.createStatement();
			
			months=new Vector<String[]>();
			ResultSet rs=stmt.executeQuery("SELECT * FROM CHECKINMONTHS");
			while(rs.next())
			{
				months.add(new String[]{rs.getString("MONTH")});
			}

			rs.close();
			stmt.close();
			con.close();
		}catch (SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
		}finally {}

		tList=InfoManager.createTable(lTitle,months);
		scrolltableList=new JScrollPane(tList);
		scrolltableList.setPreferredSize(new Dimension(68,0));
		tList.addMouseListener(this);
		
	}
	
	public void generateTable(String s)
	{
		Panel2.loadDriver();
		
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INFOMANAGE"
					,"root","Qw1");
			Statement stmt=con.createStatement();

			checkins=new Vector<String[]>();
			ResultSet rs=stmt.executeQuery("SELECT * FROM "+s);
			while(rs.next())
			{
				checkins.add(new String[]{rs.getString("ID"),rs.getString("NAME"),rs.getString("SALARY"),rs.getString("WELFARE")});
			}

			rs.close();
			stmt.close();
			con.close();
		}catch (SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
		}finally {}

		table=InfoManager.createTable(titles,checkins);
		table.setBackground(Color.green);
	}	
	
	public void mouseClicked(MouseEvent e)
	{
		int row = tList.getSelectedRow();
		subPanelC.removeAll();
		panel.remove(subPanelCi);
		generateTable("SL"+((String)tList.getValueAt(row,0)).replace(".", ""));
		scrolltable=new JScrollPane(table);
		labelCi.setText((String)tList.getValueAt(row,0)+"薪资表");
		panel.add(subPanelCi,BorderLayout.EAST);
		subPanelC.add(scrolltable,BorderLayout.CENTER);
		panel.updateUI();
	}

	public void mouseEntered(MouseEvent e)
	{
		
	}

	public void mouseExited(MouseEvent e)
	{
		 
	}

	public void mousePressed(MouseEvent e)
	{
		 
	}

	public void mouseReleased(MouseEvent e)
	{
		
	}
	public void actionPerformed(ActionEvent e)
	{
		String cmd=e.getActionCommand();
		switch (cmd)
		{
			case "刷新列表":
				subPanelL.remove(scrolltableList);
				generateList();
				subPanelL.add(scrolltableList);
				break;
				
			case "修改":
				subPanelC.add(subPanel,BorderLayout.SOUTH);
				break;
				
			case "Ok2":
				subPanelC.remove(subPanel);
				break;
				
			case "Cancel2":
				subPanelC.remove(subPanel);
				break;
		}
		panel.updateUI();
	}
}
