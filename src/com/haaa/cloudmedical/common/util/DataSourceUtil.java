package com.haaa.cloudmedical.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/*
 * datasource
 * 被配置文件取代，可有可无
 */
public class DataSourceUtil {

	private static String configFile = "dbconfig.properties";
	private static Properties properties = null;

	static {
		properties = new Properties();
		InputStream inputStream = null;
		try {
			configFile = DataSourceUtil.class.getClassLoader().getResource("").getPath() + configFile;
			File file = new File(configFile);
			inputStream = new BufferedInputStream(new FileInputStream(file));
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static final DataSource getDataSource() throws Exception {
		DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
		return dataSource;
	}
	public static void main(String[] args) {
		System.out.println(DataSourceUtil.properties);
	}
}
