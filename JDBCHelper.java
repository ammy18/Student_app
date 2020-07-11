package com.uttara.jdbc.studentdb.amogh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCHelper {
private static Connection con=null;
static int count=0;
public static Connection getConnection()
{
	try {
		if(con==null&&count<=1)
			{con=DriverManager.getConnection(JDBCConstants.URL);
			 count++;
			}
			
		return con;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
}
public static PreparedStatement getStatement(String sql)
{
	try {
		PreparedStatement ps=JDBCHelper.getConnection().prepareStatement(sql);
		return ps;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
}
public static void close(ResultSet rs)
{
	try {
		if(rs!=null)
			rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static void close(PreparedStatement ps)
{
	try {
		if(ps!=null)
			ps.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static void close(Connection con)
{
	try {
		if(con!=null)
			con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
