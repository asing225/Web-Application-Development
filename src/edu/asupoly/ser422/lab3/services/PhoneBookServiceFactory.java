package edu.asupoly.ser422.lab3.services;

import java.util.Properties;

import edu.asupoly.ser422.lab3.services.impl.RDBMPhoneBookServiceImpl;

// we'll build on this later
public class PhoneBookServiceFactory {
    private PhoneBookServiceFactory() {}

    public static PhoneBookService getInstance() {
	// should really read from a property here
	if (__phonebookservice == null) {
		__phonebookservice = new RDBMPhoneBookServiceImpl();
	}
	return __phonebookservice;
    }

    private static PhoneBookService __phonebookservice;
    
	// This class is going to look for a file named booktown.properties in the classpath
	// to get its initial settings
	static {
		try {
			Properties dbProperties = new Properties();
			Class<?> initClass = null;
			dbProperties.load(PhoneBookServiceFactory.class.getClassLoader().getResourceAsStream("/phonebook.properties"));
			String serviceImpl = dbProperties.getProperty("serviceImpl");
			if (serviceImpl != null) {
				initClass = Class.forName(serviceImpl);
			} else {
				initClass = Class.forName("edu.asupoly.ser422.lab3.services.impl.RDBMPhoneBookServiceImpl");
			}
			__phonebookservice = (PhoneBookService)initClass.newInstance();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
    
}
