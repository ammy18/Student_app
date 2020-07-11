package com.uttara.jdbc.studentdb.amogh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Student {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc1=new Scanner(System.in);
		Scanner sc2=new Scanner(System.in);
		Connection con=null;
		PreparedStatement ps_insst=null,ps_insstm=null,ps_upd=null,ps_del=null,ps_sel=null,ps_drop=null;
		ResultSet rs=null;
		LoggerStudentRecords lsr=null;
		try
		{
			Class.forName(JDBCConstants.DRIVER);
			con=JDBCHelper.getConnection();
			//con.setAutoCommit(false);
			int ch1=0;
			lsr=LoggerStudentRecords.getInstance();
			while(ch1!=5)
			{
				
				System.out.println("Press 1 to insert new student record");
				System.out.println("Press 2 to update student record");
				System.out.println("Press 3 to delete student record");
				System.out.println("Press 4 to list student record");
				System.out.println("Press 5 to exit");
				System.out.println("press 6 to drop either of the table ");
				while(!sc2.hasNextInt())
				{
					System.out.println("Enter the number properly");
					sc1.next();
				}
				ch1=sc2.nextInt();
				switch(ch1)
				{
				case 1:ps_insst=JDBCHelper.getStatement("insert into student(student_id,name,email_id,dob) values(?,?,?,?)");
				       System.out.println("Enter student_id");
				       String stdId=sc1.nextLine();
				       ps_insst.setString(1, stdId);
					   System.out.println("Enter name of the student\n*allcaps");
					   String name=sc1.nextLine();
					   ps_insst.setString(2, name);
					   System.out.println("Enter email_id of the student");
					   String email=sc1.nextLine();
					   ps_insst.setString(3, email);
					   System.out.println("Enter dob of the student(dd/MM/yyyy)");
					   String dt=sc1.nextLine();
					   Date date=new Date();
					   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					   date=sdf.parse(dt);
					   java.sql.Date dt1=new java.sql.Date(date.getTime());
					   ps_insst.setDate(4, dt1);
					   ps_insst.execute();
					   //con.commit();
					   lsr.log("student record of entered successfully at ");
					   int i=0;
					   while(i<4)
					   {
						          ps_sel=JDBCHelper.getStatement("select slno from student where student_id=?");
						          ps_sel.setString(1, stdId);
						          rs=ps_sel.executeQuery();
						          rs.next();
						          int studentSl=rs.getInt("slno");
						          ps_insstm=JDBCHelper.getStatement("insert into student_marks(student_sl,standard,percentage) values(?,?,?)");
						          ps_insstm.setInt(1, studentSl);
						          System.out.println("Enter the standard of the student "+name);
						          String std=sc1.nextLine();
						          ps_insstm.setString(2, std);
						          System.out.println("Enter percentage of the student "+name);
						          int percentage=sc2.nextInt();
						          ps_insstm.setInt(3, percentage);
						          ps_insstm.execute();
						          lsr.log("student marks entered successfully at ");
						          i++;
							   
						   
						 }
					   
					   break;
				case 2:ps_upd=JDBCHelper.getStatement("update student set email_id=? where name=?");
				       System.out.println("whose email_id you want to update");
				       name=sc1.nextLine();
				       ps_upd.setString(2, name);
				       System.out.println("Enter the new email_id");
				       email=sc1.nextLine();
				       ps_upd.setString(1, email);
				       ps_upd.execute();
				       lsr.log("student details updated at ");
				       int ch2=0;
				       while(ch2!=0)
				       {
				    	   System.out.println("Press 1 to update student marks");
				    	   System.out.println("Press 2 to exit");
				    	   while(!sc2.hasNextInt())
				    	   {
				    		   System.out.println("Enter an integer no");
				    		   sc2.next();
				    	   }
				    	   ch2=sc1.nextInt();
				    	   switch(ch2)
				    	   {
				    	   case 1:ps_upd=JDBCHelper.getStatement("update student_marks set percentage=? where student_sl=?");
				    	          ps_sel=JDBCHelper.getStatement("select slno from student where name='"+name+"'");
				    	          rs=ps_sel.executeQuery();
				    	          rs.next();
				    	          int studentSl=rs.getInt("slno");
				    	          ps_upd.setInt(2, studentSl);
				    	          System.out.println("Enter the new percentage");
				    	          int percentage=sc1.nextInt();
				    	          ps_upd.setInt(1, percentage);
				    	          ps_upd.execute();
				    	          lsr.log("student marks updated at ");
				    		   break;
				    	   default:
				    		   break;
				    	   }
				       }
					   break;
				case 3:ps_del=JDBCHelper.getStatement("delete from student_marks where student_sl=(select slno from student where name=?)");
				       System.out.println("Enter the name of the student whose records are to be deleted ");
				       name=sc1.nextLine();
				       ps_del.setString(1, "name");
				       
					   System.out.println("Deleted from student_marks "+ps_del.executeUpdate());
					   ps_del.close();
					   ps_del=JDBCHelper.getStatement("delete from student where name=?");
					   ps_del.setString(1, name);
					   if(ps_del.execute())
					   lsr.log("deleted successfully on ");				    		  				    		
					   else
						 	   System.out.println("please try again the name you entered is in matching our records");				    					       
					   break;
				case 4:ps_sel=JDBCHelper.getStatement("select student_id,name,dob,standard,percentage from student s,student_marks sm where s.slno=sm.student_sl");
				       rs=ps_sel.executeQuery();
				       while(rs.next())
				       {
				    	   System.out.println(rs.getString("student_id")+"  "+rs.getString("name")+"  "+rs.getDate("dob")+"  "+rs.getString("standard")+"  "+rs.getInt("percentage"));
				       }
					   break;
				case 6:ps_drop=JDBCHelper.getStatement("drop table actors");
				       System.out.println(ps_drop.execute());
				default:
					   break;
				}
				
			}
			sc1.close();
			sc2.close();
			//con.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally
		{
			JDBCHelper.close(rs);
			JDBCHelper.close(ps_insst);
			JDBCHelper.close(ps_insstm);
			JDBCHelper.close(ps_upd);
			JDBCHelper.close(ps_del);
			JDBCHelper.close(ps_sel);
			JDBCHelper.close(con);
		}
	}

}
