package a;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Panel5 extends Panel
{
	JTextArea textArea=new JTextArea();

	JLabel label1=new JLabel("员工总数");
	JLabel label2=new JLabel("签到总数：0");
	JLabel label3=new JLabel("工资总数：0");
	JLabel label4=new JLabel("福利总数：0");
	
	public Panel5(String s)
	{
		super(s);
		
		JTextArea textArea=new JTextArea();
		
		textArea.setText("\n\n\n\n    员工总数："+employeeCount()+"\n    签到总数：0\n    工资总数：0\n    福利总数：0");
		textArea.setEditable(false);textArea.add(label1);
		
		panel.add(textArea);
	}
	
	public int employeeCount()
	{
		Panel2.loadDriver();
		int count=0;
		
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/INFOMANAGE"
					,"root","Qw1");
			Statement stmt=con.createStatement();
			
			ResultSet rs=stmt.executeQuery("SELECT COUNT(*) FROM EMPLOYEE");

			while(rs.next())
			{
				count=rs.getInt(1);
			}
			rs.close();
			stmt.close();
			con.close();
		}catch (SQLException e)
		{
			System.out.println("SQLException: "+e.getMessage());
		}finally {}
		return count;
	}
}
