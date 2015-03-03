package a;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.*;

public class Panel2 extends Panel implements MouseListener,ActionListener
{

	JTable table;
	JScrollPane scrolltable;
	String[] titles={"序号","姓名","性别","年龄","入职日期"}; 
	Vector<String[]> records;

	JButton buttonNew=new JButton("新增");
	JButton buttonModify=new JButton("修改");
	JButton buttonDelete=new JButton("删除");
	JButton buttonOk=new JButton("确定");
	JButton buttonCancel=new JButton("取消");
	
	boolean bDeletePermit=true;
	
	JPanel subPanel=new JPanel();
	JPanel subPanelL=new JPanel();
	JPanel subPanelC=new JPanel();
	GridLayout grid;

	JLabel subpLabel=new JLabel("");
	JLabel labelName=new JLabel("姓名");
	JLabel labelGender=new JLabel("性别");
	JLabel labelAge=new JLabel("年龄");
	JLabel labelEmploydate=new JLabel("入职日期");
	JTextField tFieldName=new JTextField(8);
	JTextField tFieldGender=new JTextField(3);
	JTextField tFieldAge=new JTextField(3);
	JTextField tFieldEmploydate=new JTextField(10);

	String selectedId,selectedName,selectedGender,selectedAge,selectedEmploydate;
	String sqlPhrase;
	String maxId;
	
	boolean addFlag;
	
	public Panel2(String s)
	{
		super(s);
		labelR=new JLabel("   ");
		panel.add(labelR,BorderLayout.EAST);
		
		generateTable();
		
		buttonModify.setEnabled(false);
		buttonDelete.setEnabled(false);
		
		subPanelC.setLayout(new BorderLayout());
		
		subPanel.add(subpLabel);
		subPanel.add(labelName);
		subPanel.add(tFieldName);
		subPanel.add(labelGender);
		subPanel.add(tFieldGender);
		subPanel.add(labelAge);
		subPanel.add(tFieldAge);
		subPanel.add(labelEmploydate);
		subPanel.add(tFieldEmploydate);
		subPanel.add(buttonOk);
		subPanel.add(buttonCancel);
		
		grid=new GridLayout(0,1,0,3);
		subPanelL.setLayout(grid);
		subPanelL.add(buttonNew);
		subPanelL.add(buttonModify);
		subPanelL.add(buttonDelete);
		
		buttonNew.addActionListener(this);
		buttonModify.addActionListener(this);
		buttonDelete.addActionListener(this);
		buttonOk.addActionListener(this);
		buttonCancel.addActionListener(this);

		scrolltable=new JScrollPane(table);
		subPanelC.add(scrolltable,BorderLayout.CENTER);
		panel.add(subPanelC,BorderLayout.CENTER);
		panel.add(subPanelL,BorderLayout.WEST);
		

	}
	
	public void generateTable()
	{
		loadDriver();
		
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INFOMANAGE"
					,"root","Qw1");
			Statement stmt=con.createStatement();
			
			ResultSet mid=stmt.executeQuery("SELECT MAX(ID) FROM EMPLOYEE");
			if(mid.next())
				maxId=mid.getString("MAX(ID)");
			if(maxId==null)
				maxId="10000";
			
			records=new Vector<String[]>();
			ResultSet rs=stmt.executeQuery("SELECT * FROM EMPLOYEE");
			while(rs.next())
			{
				records.add(new String[]{rs.getString("ID"),rs.getString("NAME"),rs.getString("GENDER"),rs.getString("AGE"),rs.getString("EMPLOYDATE")});
			}
			rs.close();
			mid.close();
			stmt.close();
			con.close();
		}catch (SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
		}finally {}

		table=InfoManager.createTable(titles,records);
		table.setBackground(Color.green);
		table.addMouseListener(this);
	}
	
	public void execSql(String s)
	{
		loadDriver();
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INFOMANAGE"
					,"root","Qw1");
			Statement stmt=con.createStatement();
			
			stmt.executeUpdate(s);
			stmt.close();
			con.close();
		}catch (SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
		}finally {}
	}
	
	public static void loadDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}catch (ClassNotFoundException e)
		{
			System.out.println("ClassNotFoundException: "+e.getMessage());
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		buttonModify.setEnabled(true);
		if(bDeletePermit)
			buttonDelete.setEnabled(true);
		int row = table.getSelectedRow();
		selectedId=((String)table.getValueAt(row,0));
		selectedName=((String)table.getValueAt(row,1));
		selectedGender=((String)table.getValueAt(row,2));
		selectedAge=((String)table.getValueAt(row,3));
		selectedEmploydate=((String)table.getValueAt(row,4));
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
			case "新增":
				buttonDelete.setEnabled(false);
				buttonModify.setEnabled(false);
				bDeletePermit=true;
				subpLabel.setText("新增：");
				tFieldName.setText("");
				tFieldGender.setText("");
				tFieldAge.setText("");
				tFieldEmploydate.setText("");
				subPanelC.add(subPanel,BorderLayout.SOUTH);
				addFlag=true;
				break;

			case "修改":
				buttonDelete.setEnabled(false);
				buttonModify.setEnabled(false);
				bDeletePermit=false;
				subpLabel.setText("修改：");
				tFieldName.setText(selectedName);
				tFieldGender.setText(selectedGender);
				tFieldAge.setText(selectedAge);
				tFieldEmploydate.setText(selectedEmploydate);
				subPanelC.add(subPanel,BorderLayout.SOUTH);
				addFlag=false;
				break;
				
			case "删除":
				buttonDelete.setEnabled(false);
				buttonModify.setEnabled(false);
				sqlPhrase="DELETE FROM EMPLOYEE WHERE ID="+selectedId;
				execSql(sqlPhrase);
 				break;

			case "确定":
				buttonDelete.setEnabled(false);
				buttonModify.setEnabled(false);
				bDeletePermit=true;
				if(addFlag)
					sqlPhrase="INSERT INTO EMPLOYEE VALUES('"+String.valueOf(Integer.parseInt(maxId)+1)+"','"+tFieldName.getText()+"','"+tFieldGender.getText()+"','"+tFieldAge.getText()+"','"+tFieldEmploydate.getText()+"')";
				else
					sqlPhrase="UPDATE EMPLOYEE SET NAME='"+tFieldName.getText()+"',GENDER='"+tFieldGender.getText()+"',AGE='"+tFieldAge.getText()+"',EMPLOYDATE='"+tFieldEmploydate.getText()+"' WHERE ID='"+selectedId+"'";
				System.out.println(sqlPhrase);
				execSql(sqlPhrase);
				subPanelC.remove(subPanel);
				break;
			
			case "取消":
				buttonDelete.setEnabled(false);
				buttonModify.setEnabled(false);
				bDeletePermit=true;
				subPanelC.remove(subPanel);
				break;
		}

		subPanelC.remove(scrolltable);
		
		generateTable();
		
		scrolltable=new JScrollPane(table);
		subPanelC.add(scrolltable,BorderLayout.CENTER);

		panel.updateUI();

	}
}
