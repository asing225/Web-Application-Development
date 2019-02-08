package edu.asu.ser422;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.logging.*;

/**
 * Servlet class to add new programmer in XML file by taking user input.
 * Uses JAXB
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class NewProgrammerServlet extends HttpServlet {
	
	private static String _fileName = null;
	private Programmers programmers = new Programmers();
	private int counter = 0;
	
	public NewProgrammerServlet() {}
  
	public void init(ServletConfig paramServletConfig)
			throws ServletException {
		
		super.init(paramServletConfig);
		_fileName = paramServletConfig.getInitParameter("programmersFile");
		if ((_fileName == null) || (_fileName.length() == 0)) {
			throw new ServletException();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setStatus(res.SC_METHOD_NOT_ALLOWED);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		//get request parameters from the request
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String[] languages = req.getParameter("languages").split(",");
		String[] days = req.getParameter("availability").split(",");
		String dreamJob = req.getParameter("dreamjob");
		
		//getting the absolute path of the XML file for machine
		String fileName = req.getServletContext().getRealPath("/WEB-INF/classes" + _fileName);
		
		//creating java objects with request data to write on XML
		Language languageListed = new Language();
		for (int i = 0; i < languages.length; i++) {
			languageListed.add(languages[i]);
		}
		
		Days daysListed = new Days();
		for (int j = 0; j < days.length; j++) {
			daysListed.add(days[j]);
		}
		Programmer localProgrammer = new Programmer(firstName, lastName, languageListed, daysListed, dreamJob);
		programmers.add(localProgrammer);
		try
		{
			//write to XML
			objectToXML(programmers, fileName);
			counter += 1;
			
			//writing out response
			res.setStatus(res.SC_OK);
			res.setContentType("text/html");
			PrintWriter writer = res.getWriter();
			writer.println("<HTML><HEAD><TITLE>Successful transaction</TITLE></HEAD><BODY>");
			writer.println("<a>The last transaction was successful</a><br>");
			writer.println("The number of entries in the file: " + counter + "<br>");
			writer.println("<a href = \"" + req.getServletContext().getContextPath() + "\">Home Page</a>");
			writer.println("</BODY></HTML>");
		}
		catch (JAXBException localJAXBException) {
			localJAXBException.printStackTrace();
			res.setStatus(res.SC_INTERNAL_SERVER_ERROR);
			res.setContentType("text/html");
			PrintWriter writer = res.getWriter();
			writer.println("<HTML><HEAD><TITLE>Failed transaction</TITLE></HEAD><BODY>");
			writer.println("<a>The last transaction failed because of JAXB error</a><br>");
			writer.println("The number of entries in the file: " + counter);
			writer.println("<a href = \"" + req.getServletContext().getContextPath() + "\">Home Page</a>");
			writer.println("</BODY></HTML>");
		}
		//get the current session and invalidate it, if not new.
		HttpSession sess = req.getSession();
		if(!sess.isNew()) {
			sess.invalidate();
		}
	}
	
	//method to write java objects to XML
	private void objectToXML(Programmers programmers, String fileName)
			throws JAXBException {
		
		JAXBContext localJAXBContext = JAXBContext.newInstance(Programmers.class);
		Marshaller localMarshaller = localJAXBContext.createMarshaller();
		localMarshaller.setProperty("jaxb.formatted.output", true);
		localMarshaller.marshal(programmers, new File(fileName));
	}
}