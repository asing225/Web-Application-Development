package edu.asu.ser422;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Servlet for making a new entry into the XML file
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
	
	//POST method to handle the form submit request from client
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		//getting parameters from the request
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String[] languages = req.getParameterValues("languages");
		String[] days = req.getParameterValues("availability");
		String dreamJob = req.getParameter("dreamjob");
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/classes" + _fileName);
		
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
			//writing to XML file
			objectToXML(programmers, fileName);
			counter += 1;
			//writing out the response
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
			//response for exception scenario
			res.setStatus(res.SC_INTERNAL_SERVER_ERROR);
			res.setContentType("text/html");
			PrintWriter writer = res.getWriter();
			writer.println("<HTML><HEAD><TITLE>Failed transaction</TITLE></HEAD><BODY>");
			writer.println("<a>The last transaction failed because of JAXB error</a><br>");
			writer.println("The number of entries in the file: " + counter);
			writer.println("<a href = \"" + req.getServletContext().getContextPath() + "\">Home Page</a>");
			writer.println("</BODY></HTML>");
		}
	}
  
	//method to write to the XML file
	public void objectToXML(Programmers programmers, String fileName)
			throws JAXBException {
		JAXBContext localJAXBContext = JAXBContext.newInstance(Programmers.class);
		Marshaller localMarshaller = localJAXBContext.createMarshaller();
		localMarshaller.setProperty("jaxb.formatted.output", true);
		localMarshaller.marshal(programmers, new File(fileName));
	}
}