package edu.asu.ser422.phone.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.asu.ser422.phone.model.PhoneEntry;
import edu.asu.ser422.phone.services.PhoneBookServiceFactory;
import edu.asu.ser422.phone.services.impl.MongoDBPhoneBookServiceImpl;
import edu.asu.ser422.phone.services.impl.RDBMPhoneBookServiceImpl;
import edu.asu.ser422.phone.services.impl.SimplePhoneBookServiceImpl;

/**
 * @author amanjotsingh
 * Controller class to route requests based on the user inputs
 * */

@SuppressWarnings("serial")
public class PhoneBookServlet extends HttpServlet {
	
	private String invalidAction = "invalidAction.jsp";
	private String noEntry = "noEntry.jsp";
	private String success = "success.html";
	private String listAll = "listAll.jsp";
	private String remove = "remove.jsp";
	private String error = "error.html";
	private String noSuchNum = "noSuchNum.html";
	private static SimplePhoneBookServiceImpl _pbook = null;
//	private Logger log = Logger.getLogger(PhoneBookServlet.class.getName());
    
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	String filename = config.getInitParameter("phonebook");
    	if (filename == null || filename.length() == 0) {
    	    throw new ServletException();
    	}
    	InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
    	try {
    	    _pbook = new SimplePhoneBookServiceImpl(is);
    	} catch (IOException exc) {
    	    exc.printStackTrace();
    	    throw new ServletException(exc);
    	}
	}
    
    //doget method is not implemented
    @SuppressWarnings("static-access")
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
    	res.setStatus(res.SC_NOT_IMPLEMENTED);
    }
        
    //doPost method to cater to all client requests
    @SuppressWarnings({ "rawtypes", "static-access" })
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
    		throws ServletException, IOException {
    	
    	//get the class selected by the user from property file
    	Class phonestore = null;
    	try {
    		phonestore = PhoneBookServiceFactory.getInstance();
    		if(phonestore == null) {
    			res.setStatus(res.SC_BAD_REQUEST);
    			req.getRequestDispatcher(error).forward(req, res);
    		}
    	}
    	catch(Throwable t) {
    		t.printStackTrace();
    	}
    	Object object = null;
    	if(phonestore.getCanonicalName().contains("Simple")) {
			 object = _pbook;
    	}
    	else {
			 object = new RDBMPhoneBookServiceImpl();
    	}
    	
    	String action = req.getParameter("Action");
    	
    	//error handling for null action
    	if(action.equals(null) || action.length() == 0) {
    		res.setStatus(res.SC_BAD_REQUEST);
    		req.getRequestDispatcher(invalidAction).forward(req, res);
    	}
    	try {
    		if(action != null) {
    			String firstName = req.getParameter("firstname");
    			String lastName = req.getParameter("lastname");
    			String number = req.getParameter("phone");
    			if(action.equals("Add")) {
    				if(firstName == "" && lastName == "" && number == "") {
        				res.setStatus(res.SC_BAD_REQUEST);
        				req.getRequestDispatcher(noEntry).forward(req, res);
        			}
    				else {
    					if(object instanceof SimplePhoneBookServiceImpl) {
        					((SimplePhoneBookServiceImpl) object).addEntry(firstName, lastName, number);
        					((SimplePhoneBookServiceImpl) object).savePhoneBook(getServletContext().getRealPath(
        							"/WEB-INF/classes/resources/" + SimplePhoneBookServiceImpl.DEFAULT_FILENAME));
        				}
        				else if(object instanceof RDBMPhoneBookServiceImpl) {
        					((RDBMPhoneBookServiceImpl) object).addEntry(firstName, lastName, number);
        				}
        				else if(object instanceof MongoDBPhoneBookServiceImpl) {
        					((MongoDBPhoneBookServiceImpl) object).addEntry(firstName, lastName, number);
        				}
        				res.setStatus(res.SC_OK);
        				req.getRequestDispatcher(success).forward(req, res);
    				}
    			}
    			else if(action.equals("List")) {
    				List<PhoneEntry> allEntries = null ;
					if(object instanceof SimplePhoneBookServiceImpl) {
    					allEntries = ((SimplePhoneBookServiceImpl) object).listEntries();
    					   }
    				else if(object instanceof RDBMPhoneBookServiceImpl) {
    					allEntries = ((RDBMPhoneBookServiceImpl) object).listEntries();
    				}
    				else if(object instanceof MongoDBPhoneBookServiceImpl) {
    					allEntries = ((MongoDBPhoneBookServiceImpl) object).listEntries();
    				}
    				req.setAttribute("entries", allEntries);
    				res.setStatus(res.SC_OK);
    				req.getRequestDispatcher(listAll).forward(req, res);
    			}
    			else if(action.equals("Remove")) {
    				if(firstName == "" && lastName == "" && number == "") {
        				res.setStatus(res.SC_BAD_REQUEST);
        				req.getRequestDispatcher(noEntry).forward(req, res);
        			}
    				else {
    					int result = 0;
        				if(object instanceof SimplePhoneBookServiceImpl) {
        					result = ((SimplePhoneBookServiceImpl) object).removeEntry(number);
        					((SimplePhoneBookServiceImpl) object).savePhoneBook(getServletContext().getRealPath(
        							"/WEB-INF/classes/resources/" + SimplePhoneBookServiceImpl.DEFAULT_FILENAME));
        				}
        				else if(object instanceof RDBMPhoneBookServiceImpl) {
        					result = ((RDBMPhoneBookServiceImpl) object).removeEntry(number);
        				}
        				else if(object instanceof MongoDBPhoneBookServiceImpl) {
        					result = ((MongoDBPhoneBookServiceImpl) object).removeEntry(number);
        				}
        				if(result == 0) {
        					res.setStatus(res.SC_OK);
            				req.getRequestDispatcher(noSuchNum).forward(req, res);
        				}
        				else {
        					res.setStatus(res.SC_OK);
            				req.getRequestDispatcher(remove).forward(req, res);
        				}
    				}
    			}
    		}
    	}
    	catch(Exception exc) {
    		exc.printStackTrace();
    	}
    }
}
