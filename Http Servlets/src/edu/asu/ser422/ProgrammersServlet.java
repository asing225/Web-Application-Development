package edu.asu.ser422;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Servlet for reading the XML file
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class ProgrammersServlet extends HttpServlet
{
	private static String _fileName = null; 
  
	public void init(ServletConfig paramServletConfig) throws ServletException {
		
		super.init(paramServletConfig);
		_fileName = paramServletConfig.getInitParameter("programmersFile");
		if ((_fileName == null) || (_fileName.length() == 0)) {
			throw new ServletException();
		}
	}
	
	//method to handle search requests from client
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		//getting request parameters
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String languages[] = req.getParameter("languages") == null ? null : req.getParameter("languages").split(",");
		String days[] = req.getParameter("days") == null ? null : req.getParameter("days").split(",");
		String dreamJob = req.getParameter("dreamjob");
		
		String file = req.getServletContext().getRealPath("/WEB-INF/classes" + _fileName);
		File localFile = new File(file);
		
		//processing
		res.setContentType("text/html; charset=utf-8");
		PrintWriter localPrintWriter = res.getWriter();
		localPrintWriter.println("<HTML>\n<BODY>");
		String userAgent = req.getHeader("User-Agent");
    
		if (userAgent.toLowerCase().contains("chrome")) {
			localPrintWriter.println("<a>Welcome Google User!</a><br><br>");
		}
		
		int noOfProgrammers = 0;
		Programmers programmers = new Programmers();
		try {
			programmers = XmlToObject(localFile);
			noOfProgrammers = programmers.getProgrammers().size();
			res.setStatus(res.SC_OK);
			//Iterating over the list of programmers
			for (int j = 0; j < noOfProgrammers; j++) {
				Boolean flagFirstName = false;
				Boolean flagLastName = false;
				Boolean flagLanguage = false;
				Boolean flagDays = false;
				Boolean flagDreamJob = false;
				int noOfDays = programmers.getProgrammers().get(j).getDays().getDay().size();
				int noofLanguages = programmers.getProgrammers().get(j).getLanguages().getLanguage().size();
				
				if (firstName == null || compareStrings(programmers.getProgrammers().get(j).getFirstName(), firstName)) {
		    		flagFirstName = true;
		    	}
			  	if (lastName == null || compareStrings(programmers.getProgrammers().get(j).getLastName(), lastName)) {
					flagLastName = true;
			  	}
			  	if (languages == null || compareLists(programmers.getProgrammers().get(j).getLanguages().getLanguage(), languages)) {
					flagLanguage = true;
			  	}
			  	if (days== null || compareLists(programmers.getProgrammers().get(j).getDays().getDay(), days)) {
			  		flagDays = true;
			  	}
			  	if (dreamJob == null || compareStrings(programmers.getProgrammers().get(j).getDreamJob(), dreamJob)) {
			  		flagDreamJob = true;
			  	}

			  	//conditional output on response
			  	if (flagFirstName && flagLastName && flagDays && flagLanguage && flagDreamJob)
			  	{
			  		localPrintWriter.println("<a>First Name: " + (programmers.getProgrammers().get(j)).getFirstName() + "</a><br>");
			  		localPrintWriter.println("<a>Last Name: " + (programmers.getProgrammers().get(j)).getLastName() + "</a><br>");
			  		for (int n = 0; n < noOfDays; n++) {
			  			localPrintWriter.println("<a>Day: " + (programmers.getProgrammers().get(j)).getDays().getDay().get(n) + "</a><br>");
			  		}
			  		for (int n = 0; n < noofLanguages; n++) {
			  			localPrintWriter.println("<a>Language: " + (programmers.getProgrammers().get(j)).getLanguages().getLanguage().get(n) + "</a><br>");
			  		}
			  		localPrintWriter.println("<a>Dream Job: " + (programmers.getProgrammers().get(j)).getDreamJob() + "</a><br><br>");
			  	}
	    	}
		}
		catch (JAXBException localJAXBException) {
			localJAXBException.printStackTrace();
			res.setStatus(res.SC_INTERNAL_SERVER_ERROR);
			localPrintWriter.println("<a>Error occured in reading XML file</a>");
			localPrintWriter.println("<a href = \"" + req.getServletContext().getContextPath() + "\">Home Page</a>");
		}
		localPrintWriter.println("</BODY>\n</HTML>");
  	}
  
	private Programmers XmlToObject(File fileName) throws JAXBException {
		Programmers prog = new Programmers();
		JAXBContext localJAXBContext = JAXBContext.newInstance(Programmers.class);
		Unmarshaller localUnmarshaller = localJAXBContext.createUnmarshaller();
		prog = ((Programmers)localUnmarshaller.unmarshal(fileName));
		return prog;
	}
	
	@SuppressWarnings("unused")
	private Boolean compareStrings(String paramString1, String paramString2) {
		if (paramString1.toLowerCase().contains(paramString2.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private Boolean compareLists(ArrayList<String> list, String[] query) {
		for(int i=0; i<query.length; i++) {
			for(int j=0; j<list.size(); j++) {
				if(list.get(j).toLowerCase().contains(query[i].toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
}