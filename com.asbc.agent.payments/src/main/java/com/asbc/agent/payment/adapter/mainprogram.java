package com.asbc.agent.payment.adapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.apbc.service.ApplicationBean;

public class mainprogram {final static Logger logger = Logger.getLogger(mainprogram.class);

public static void main(String[] args) 
{
	
	logger.info("Starting the Commissions Processing");
	System.out.println("Starting the Commissions Processing");

	InputStream istream = null;
	logger.info("Program Arguments:"+args);
	if (args.length != 4)
	{
		logger.info("Can not run payroll by pay period program, It requires 3 params, 1)appcontext, 2)config prop, 3)run type (main/trail), 4. payperiodid");
		return;
	}
    
	ApplicationBean ab = new ApplicationBean();
	String fileLocation = null;
	try (FileInputStream in = new FileInputStream(args[1])) 
	{
		Properties prop = new Properties();
		prop.load(in);
		logger.info(prop.getProperty("storagelocation"));
        fileLocation = prop.getProperty("storagelocation");
	}
	catch (IOException fnf)
	{
		logger.info("Properties file not found, Exit");
	}

	// load a properties file
	ab.processPayroll(args[0],fileLocation, args[2], args[3]);
	System.out.println("Completed Commission Processing program");
	logger.info("Completed Commission Processing program");
}
}
