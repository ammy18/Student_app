package com.uttara.jdbc.studentdb.amogh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class LoggerStudentRecords {
private static LoggerStudentRecords lsr=null;
private LoggerStudentRecords() {}
public static LoggerStudentRecords getInstance()
{
	if(lsr==null)
		lsr=new LoggerStudentRecords();
	return lsr;
}
public void log(String msg)
{
	Date dt=new Date();
	BufferedWriter bw=null;
	try {
		 bw=new BufferedWriter(new FileWriter("logger.txt",true));
		 //bw.newLine();
		 bw.write(msg+"  "+dt);
		 bw.newLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		if(bw!=null)
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	}
}
}
