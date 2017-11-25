package com.haaa.cloudmedical.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogPrinter {
	private static Properties properties = new Properties();

	public synchronized static void info(String info) {
		properties = getProperties();
		PropertyConfigurator.configure(properties);
		Logger.getLogger(LogPrinter.class).info(info);
	}

	public synchronized static void debug(String debug) {
		properties = getProperties();
		PropertyConfigurator.configure(properties);
		Logger.getLogger(LogPrinter.class).debug(debug);
	}

	public synchronized static void fatal(String fatal) {
		properties = getProperties();
		PropertyConfigurator.configure(properties);
		Logger.getLogger(LogPrinter.class).fatal(fatal);
	}

	public synchronized static void error(String error) {
		properties = getProperties();
		PropertyConfigurator.configure(properties);
		Logger.getLogger(LogPrinter.class).error(error);
	}

	private static Properties getProperties() {
		Properties properties = new Properties();
		try {
			InputStream in = LogPrinter.class.getResourceAsStream("/log4j.properties");
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}
}
