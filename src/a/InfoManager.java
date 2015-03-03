package a;

import java.awt.*;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class InfoManager
{

	SubJFrame frame=new SubJFrame("公司信息管理软件");
	Container ctnr=frame.getContentPane();
	JTabbedPane jtp=new JTabbedPane();
	
	Panel1 panel1=new Panel1("主界面");
	Panel2 panel2=new Panel2("员工信息");
	Panel3 panel3=new Panel3("薪资福利");
	Panel4 panel4=new Panel4("考勤");
	Panel5 panel5=new Panel5("报表");

	public InfoManager()
	{
		jtp.add(panel1.rtn().getName(), panel1.rtn());
		jtp.add(panel2.rtn().getName(), panel2.rtn());
		jtp.add(panel3.rtn().getName(), panel3.rtn());
		jtp.add(panel4.rtn().getName(), panel4.rtn());
		jtp.add(panel5.rtn().getName(), panel5.rtn());

		ctnr.add(jtp);
		
		frame.setSize(600,300);
		frame.setVisible(true);
	}
	
	public static JTable createTable(final String[] titles, final Vector<String[]> records)
	{
		JTable table;
		
		AbstractTableModel atm=new AbstractTableModel()
		{
			public int getColumnCount()
			{
				return titles.length;
			}

			public int getRowCount()
			{
				return records.size();
			}

			public Object getValueAt(int row, int column)
			{
				if(!records.isEmpty())
					return records.elementAt(row)[column];
				else
					return null;
			}
			
			public void setValueAt(Object value, int row, int cloumn)
			{
				//none
			}
			
			public String getColumnName(int column)
			{
				return titles[column];
			}
			
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		
		table=new JTable(atm);
		
		return table;
	}
		
	public static void main(String args[])
	{
		new InfoManager();

	}
}