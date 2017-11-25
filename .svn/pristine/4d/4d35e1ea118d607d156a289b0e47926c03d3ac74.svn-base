package com.haaa.cloudmedical.common.util;



import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;



public class KeyDataUtil {

	public static void updateIOSandroid(String strkey) throws Exception {

		PreparedStatement pstmt = null;
		Connection conn = null;

		String[] wk = { "1", "2", "3", "4", "5" };
		String we = "WEB-INF";
		String sdir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println(sdir);
		sdir = sdir + "dbconfig.properties";
		File file = new File(sdir);
		wk = ReadFile.cs(ReadFile.readerFilecs(file));
		System.out.println("显示" + wk[1] + "    " + wk[2] + "   " + wk[3] + "     " + wk[4]);

		Class.forName("com.mysql.jdbc.Driver");
		conn = (Connection) DriverManager.getConnection(wk[1], wk[2], wk[3]);
		String sql = "update p_iosandroid set keyname=?";
		pstmt = (PreparedStatement) conn.prepareStatement(sql);
		pstmt.setString(1, strkey);
		int i = pstmt.executeUpdate();
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}

	}

	public static String getIOSandroid() {
			

			PreparedStatement pstmt = null;
			Connection conn = null;
			 ResultSet rs=null;
			 String strreturn="";

			String[] wk = { "1", "2", "3", "4", "5" };
			String we = "WEB-INF";
			String sdir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			System.out.println(sdir);
			sdir = sdir + "dbconfig.properties";
			File file = new File(sdir);
			try {
				wk = ReadFile.cs(ReadFile.readerFilecs(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("显示" + wk[1] + "    " + wk[2] + "   " + wk[3] + "     " + wk[4]);
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn = (Connection) DriverManager.getConnection(wk[1], wk[2], wk[3]);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sql = "select keyname from  p_iosandroid";
			try {
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			try {
				rs = pstmt.executeQuery();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				while (rs.next())
						{
					 strreturn=rs.getString("keyname");
						}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();

				}
			
			return strreturn;
	}

	public static void main(String[] args) throws Exception {
		String stre=KeyDataUtil.writeFs("2","yub.txt");
		System.out.println("ppp "+stre);
	}
	
	
	
	public static String writeFs(String content ,String spathfile)
	
	
	{
		
		String sdir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println(sdir);
		sdir = sdir + spathfile;
	  File file = new File(sdir);
	 

	  try (FileOutputStream fop = new FileOutputStream(file)) {

	   // if file doesn't exists, then create it
	   if (!file.exists()) {
	    file.createNewFile();
	   }

	   // get the content in bytes
	   byte[] contentInBytes = content.getBytes();

	   fop.write(contentInBytes);
	   fop.flush();
	   fop.close();

	   System.out.println("Done");

	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	return content;
}
}
