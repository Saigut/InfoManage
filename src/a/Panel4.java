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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Panel4 extends Panel implements MouseListener,ActionListener
{
	GridLayout grid;
	JPanel subPanel=new JPanel();
	JPanel subPanelL=new JPanel();
	JPanel subPanelC=new JPanel();
	JPanel subPanelCi=new JPanel();
	JPanel subAddnewT=new JPanel();
	JPanel subAddnewC=new JPanel();
	
	JTable table;
	JScrollPane scrolltable;
	String[] titles={"序号","姓名","签到"};

	JTable tList;
	JScrollPane scrolltableList;
	String[] lTitle={"选择查看:"};
	Vector<String[]> months;

	JLabel nTitle=new JLabel("新建考勤表");
	JLabel nDate=new JLabel("日期：");
	JLabel nYear=new JLabel("年");
	JLabel nMonth=new JLabel("月");
	
	Vector<String> yearList=new Vector<String>();
	Vector<String> monthList=new Vector<String>();;
	JComboBox<String> bYear;
	JComboBox<String> bMonth;

	JButton buttonNew=new JButton("新建");
	JButton buttonDelete=new JButton("删除该表");
	JButton buttonModify=new JButton("修改");
	JButton buttonOk1=new JButton("确定");
	JButton buttonOk2=new JButton("确定");
	JButton buttonCancel2=new JButton("取消");

	JLabel labelCi=new JLabel("aa");
	
	JLabel labelDays=new JLabel("签到日数:");
	JTextField tFieldDays=new JTextField(3);
	

	String selectedId,selectedName,selectedChindays;

	Vector<String[]> checkins;
	
	String sqlPhrase;
	String maxDate;
	
	boolean newTable;

	public Panel4(String s)
	{
		super(s);
		
		generateList();

		
		grid=new GridLayout(0,1,0,3);
		subPanelL.setLayout(grid);

		subPanelL.add(buttonNew);
		subPanelL.add(scrolltableList);

		panel.add(subPanelL,BorderLayout.WEST);
		
		for(int year=1990;year<2051;year++)
			yearList.add(String.valueOf(year));
		for(int month=01;month<13;month++)
			monthList.add(String.valueOf(month));

		bYear=new JComboBox<String>(yearList);
		bMonth=new JComboBox<String>(monthList);
		

		subPanelC.setLayout(new BorderLayout());
		subPanelCi.setLayout(grid);
		subAddnewC.setBackground(Color.green);

		subAddnewT.add(nTitle);
		
		buttonOk1.setActionCommand("Ok1");
		buttonOk2.setActionCommand("Ok2");
		buttonCancel2.setActionCommand("Cancel2");
		subAddnewC.add(nDate);
		subAddnewC.add(bYear);
		subAddnewC.add(nYear);
		subAddnewC.add(bMonth);
		subAddnewC.add(nMonth);
		subAddnewC.add(buttonOk1);
		
		subPanelC.add(subAddnewT,BorderLayout.NORTH);
		subPanelC.add(subAddnewC,BorderLayout.CENTER);
		panel.add(subPanelC,BorderLayout.CENTER);
		
		subPanelCi.add(labelCi);
		subPanelCi.add(buttonModify);
		subPanelCi.add(buttonDelete);
		subPanel.add(labelDays);
		subPanel.add(tFieldDays);
		subPanel.add(buttonOk2);
		subPanel.add(buttonCancel2);
		
		buttonNew.addActionListener(this);
		buttonDelete.addActionListener(this);
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
				checkins.add(new String[]{rs.getString("ID"),rs.getString("NAME"),rs.getString("CHECKINDAYS")});
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
	
	public void initSqlTable(String s)
	{
		Panel2.loadDriver();
		
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INFOMANAGE"
					,"root","Qw1");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM EMPLOYEE");
			while(rs.next())
			{
				execSql("INSERT INTO CI"+s+" VALUES('"+rs.getString("ID")+"','"+rs.getString("NAME")+"','0')");
				execSql("INSERT INTO SL"+s+" VALUES('"+rs.getString("ID")+"','"+rs.getString("NAME")+"','0','0')");
			}
			rs.close();
			stmt.close();
			con.close();
		}catch (SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
		}finally {}
	}
	
	public void execSql(String s)
	{
		Panel2.loadDriver();
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INFOMANAGE"
					,"root","Qw1");
			Statement stmt=con.createStatement();
			
			stmt.execute(s);
			stmt.close();
			con.close();
			newTable=true;
		}catch (SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
			if(e.getErrorCode()==1050)
				newTable=false;
		}finally {}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		int row = tList.getSelectedRow();
		subPanelC.removeAll();
		panel.remove(subPanelCi);
		generateTable("CI"+((String)tList.getValueAt(row,0)).replace(".", ""));
		scrolltable=new JScrollPane(table);
		labelCi.setText((String)tList.getValueAt(row,0)+"考勤表");
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
			case "新建":
				subPanelC.removeAll();
				panel.remove(subPanelCi);
				subPanelC.add(subAddnewT,BorderLayout.NORTH);
				subPanelC.add(subAddnewC,BorderLayout.CENTER);
				break;
				
			case "修改":
				subPanelC.add(subPanel,BorderLayout.SOUTH);
				break;

			case "Ok1":
				sqlPhrase="CREATE TABLE CI"+bYear.getSelectedItem()+bMonth.getSelectedItem()+" (ID CHAR(5) NOT NULL, NAME CHAR(20) NOT NULL, CHECKINDAYS CHAR(2) NOT NULL)";
				execSql(sqlPhrase);
				sqlPhrase="CREATE TABLE SL"+bYear.getSelectedItem()+bMonth.getSelectedItem()+" (ID CHAR(5) NOT NULL, NAME CHAR(20) NOT NULL, SALARY CHAR(6) NOT NULL, WELFARE CHAR(6) NOT NULL)";
				execSql(sqlPhrase);
				if(newTable)
				{
					sqlPhrase="INSERT INTO CHECKINMONTHS VALUES('"+bYear.getSelectedItem()+"."+bMonth.getSelectedItem()+"')";
					subPanelL.remove(scrolltableList);
					execSql(sqlPhrase);
					generateList();
					subPanelL.add(scrolltableList);
					
					panel.remove(subPanelCi);
					labelCi.setText(bYear.getSelectedItem()+"."+bMonth.getSelectedItem()+"考勤表");
					panel.add(subPanelCi,BorderLayout.EAST);
					initSqlTable((String)bYear.getSelectedItem()+(String)bMonth.getSelectedItem());
					subPanelC.removeAll();
					generateTable("CI"+bYear.getSelectedItem()+bMonth.getSelectedItem());
					scrolltable=new JScrollPane(table);
					subPanelC.add(scrolltable,BorderLayout.CENTER);
					System.out.println(sqlPhrase);
				}
				else
					JOptionPane.showMessageDialog(null, "该月考勤表已存在！");
				break;
				
			case "Ok2":
				subPanelC.remove(subPanel);
				break;
				
			case "Cancel2":
				subPanelC.remove(subPanel);
				break;
				
			case "删除该表":
				sqlPhrase="DROP TABLE CI"+labelCi.getText().replace(".", "").replace("考勤表", "");
				execSql(sqlPhrase);
				sqlPhrase="DROP TABLE SL"+labelCi.getText().replace(".", "").replace("考勤表", "");
				execSql(sqlPhrase);
				sqlPhrase="DELETE FROM CHECKINMONTHS WHERE MONTH="+labelCi.getText().replace("考勤表", "");
				execSql(sqlPhrase);
				JOptionPane.showMessageDialog(null, "删除成功！");
				panel.remove(subPanelCi);
				subPanelL.remove(scrolltableList);
				subPanelC.removeAll();
				generateList();
				subPanelL.add(scrolltableList);
				subPanelC.add(subAddnewT,BorderLayout.NORTH);
				subPanelC.add(subAddnewC,BorderLayout.CENTER);
				panel.add(labelR,BorderLayout.EAST);
				break;
		}
		
		panel.updateUI();
	}
}
