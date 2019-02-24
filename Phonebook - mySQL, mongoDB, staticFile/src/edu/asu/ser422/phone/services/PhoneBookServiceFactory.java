package edu.asu.ser422.phone.services;

import java.io.IOException;
import java.util.Properties;

import edu.asu.ser422.phone.services.impl.SimplePhoneBookServiceImpl;

/**
 * @author amanjotsingh
 * Factory class to read from property file and select a class implementation
 * for the data store.
 * */

// we'll build on this later
public class PhoneBookServiceFactory {
    private PhoneBookServiceFactory() {}

    @SuppressWarnings("rawtypes")
	public static Class getInstance() throws IOException {
	// should really read from a property here
	if (_phonebookservice == null) {
		_phonebookservice = SimplePhoneBookServiceImpl.class;
	}
	return _phonebookservice;
    }

    @SuppressWarnings("rawtypes")
	private static Class _phonebookservice;
    
	// This class is going to look for a file named booktown.properties in the classpath
	// to get its initial settings
	static {
		try {
			Properties dbProperties = new Properties();
			Class<?> initClass = null;
			dbProperties.load(PhoneBookServiceFactory.class.getClassLoader().getResourceAsStream("/resources/phonebook.properties"));
			String serviceImpl = dbProperties.getProperty("serviceImpl");
			if (serviceImpl != null) {
				initClass = Class.forName(serviceImpl);
			} else {
				initClass = Class.forName("edu.asu.ser422.phone.services.impl.SimplePhoneBookServiceImpl");
			}
			_phonebookservice = initClass;
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}
    
}
