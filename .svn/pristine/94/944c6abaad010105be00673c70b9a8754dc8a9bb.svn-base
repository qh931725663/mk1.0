package com.haaa.cloudmedical.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.haaa.cloudmedical.interceptor.util.AESUtil;

public class ReadFile {

	public static void main(String[] args) throws FileNotFoundException {

		File newfile = new File("D://work//cloudmedical//source//web//mkh1.0//WebRoot//WEB-INF//classes//yub.txt");
		
		if( readerFilecs(newfile).equals("1"))
		{
			System.out.println("ok");
		}
		else
		{
			System.out.println("yub");
			
		}
		/*
		String[][] as = csad(readerFilecs(newfile));
		for (int i=0;i<40;i++)
		{
		System.out.println(as[i][0]+"  "+as[i][1]);
		}
*/
	}

	public static String readerFilecs(File file) {
		StringBuffer sb = new StringBuffer();

		if (file.isFile()) {
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
				// fileInputStream.available()获取文件的字节数
				byte[] b = new byte[fileInputStream.available()];

				int read = fileInputStream.read(b);

				while (read != -1) {
					String utfString = new String(b);//
					// String utfString = new String(b, "UTF-8");
					sb.append(utfString);
					read = fileInputStream.read(b);
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
		return null;
	}

	/*
	 * 
	 * 
	 * 获取文本框的中各分行的内容写入到string 数组中
	 * 
	 * 
	 */

	public static String[] cs(String utfString) throws FileNotFoundException {

		String[] ss = null;
		String[] sr = { "1", "2", "3", "4", "5"};
		try {

			ss = utfString.split("\n");

			for (int i=0;i<4;i++)
			{
			String bb = ss[i];
			int tbb = bb.indexOf("=", 1);
			bb = bb.substring(tbb + 1);
			bb = bb.trim();
			if (i==3)
					{
					bb= AESUtil.decryptAES(bb);
					}
			sr[i]=bb;
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return sr;
		}
	}
	
	
	public static  String[][] csad(String utfString) throws FileNotFoundException {

		String[] ss = null;
		String[][] sr =new String[40][2];
		 
	
		try {

			ss = utfString.split("\n");

			for (int i=0;i<ss.length;i++)
			{
				
			String bb = ss[i];
			
			
			String tmpbb=bb;
			int tbb = bb.indexOf("=", 1);
			if (tbb>0)
			{
			
			bb = bb.substring(tbb + 1);
			tmpbb=tmpbb.substring(0,tbb);
			bb = bb.trim();
			
			sr[i][0]=tmpbb;
			
			sr[i][1]=bb;
			}
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return sr;
		}
	}

}
