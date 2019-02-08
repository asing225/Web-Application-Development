package edu.asu.ser422;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Servlet class to search the XML file.
 * Uses JAXB
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class ProgrammersServlet extends HttpServlet
{
	private static String _fileName = null;
	private static final char COOKIE_DELIMITER = ':';

	public void init(ServletConfig paramServletConfig) throws ServletException {
		
		super.init(paramServletConfig);
		_fileName = paramServletConfig.getInitParameter("programmersFile");
		if ((_fileName == null) || (_fileName.length() == 0)) {
			throw new ServletException();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		//read request parameters
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String dreamJob = req.getParameter("dreamjob");
		
		//checking if languages query is empty or not
		String languages[] = null;
		if(req.getParameter("languages") == null || req.getParameter("languages") == "") {
			languages = null;
		}
		else {
			languages = req.getParameter("languages").replaceAll("\\s+","").split(",");
		}
		
		//checking if days query is empty or not
		String days[] = null;
		if(req.getParameter("availability") == null || req.getParameter("availability") == "") {
			days = null;
		}
		else {
			days = req.getParameter("availability").replaceAll("\\s+","").split(",");
		}
		
		//getting file path for XML for local machine
		String file = req.getServletContext().getRealPath("/WEB-INF/classes" + _fileName);
		File localFile = new File(file);
		
		//start writing response
		res.setContentType("text/html; charset=utf-8");
		PrintWriter out = res.getWriter();
		out.println("<HTML>\n<BODY>");
		
		//check for google user
		String userAgent = req.getHeader("User-Agent");
		if (userAgent.toLowerCase().contains("chrome")) {
			out.println("<a>Welcome Google User!</a><br><br>");
		}
		
		int noOfProgrammers = 0;
		Programmers programmers = new Programmers();
		try {
			//read XML file
			programmers = XmlToObject(localFile);
			noOfProgrammers = programmers.getProgrammers().size();
			
			//Setting response cookie
			Cookie firstNameCookie = new Cookie("First",firstName);
			Cookie lastNameCookie = new Cookie("Last",lastName);
			Cookie languagesCookie = new Cookie("Languages",req.getParameter("languages").replaceAll("\\s+","").replace(',', COOKIE_DELIMITER));
			Cookie daysCookie = new Cookie("Days",req.getParameter("availability").replaceAll("\\s+","").replace(',', COOKIE_DELIMITER));
			Cookie dreamJobCookie = new Cookie("Dream",dreamJob);
			
			//setting up expire time for cookies
			firstNameCookie.setMaxAge(300);
			lastNameCookie.setMaxAge(300);
			languagesCookie.setMaxAge(300);
			daysCookie.setMaxAge(300);
			dreamJobCookie.setMaxAge(300);
			
			//add cookies to the response
			res.addCookie(firstNameCookie);
			res.addCookie(lastNameCookie);
			res.addCookie(languagesCookie);
			res.addCookie(daysCookie);
			res.addCookie(dreamJobCookie);

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
				
			  	//writing response if all conditions are true
			  	if (flagFirstName && flagLastName && flagDays && flagLanguage && flagDreamJob)
			  	{
			  		out.println("<a>First Name: " + (programmers.getProgrammers().get(j)).getFirstName() + "</a><br>");
			  		out.println("<a>Last Name: " + (programmers.getProgrammers().get(j)).getLastName() + "</a><br>");
			  		for (int n = 0; n < noOfDays; n++) {
			  			out.println("<a>Day: " + (programmers.getProgrammers().get(j)).getDays().getDay().get(n) + "</a><br>");
			  		}
			  		for (int n = 0; n < noofLanguages; n++) {
			  			out.println("<a>Language: " + (programmers.getProgrammers().get(j)).getLanguages().getLanguage().get(n) + "</a><br>");
			  		}
			  		out.println("<a>Dream Job: " + (programmers.getProgrammers().get(j)).getDreamJob() + "</a><br><br>");
			  	}
	    	}
			res.setStatus(res.SC_OK);
		}
		catch (JAXBException localJAXBException) {
			localJAXBException.printStackTrace();
			res.setStatus(res.SC_INTERNAL_SERVER_ERROR);
			out.println("<a>Error occured in reading XML file</a>");
		}
		out.println("<a href = \"./\">Go to home page</a>");
		out.println("</BODY>\n</HTML>");
  	}
	
	//method to read XML file
	private Programmers XmlToObject(File fileName) throws JAXBException {
		Programmers prog = new Programmers();
		JAXBContext localJAXBContext = JAXBContext.newInstance(Programmers.class);
		Unmarshaller localUnmarshaller = localJAXBContext.createUnmarshaller();
		prog = ((Programmers)localUnmarshaller.unmarshal(fileName));
		return prog;
	}
	
	//method to check if 1 string is a substring of other
	@SuppressWarnings("unused")
	private Boolean compareStrings(String paramString1, String paramString2) {
		if (paramString1.toLowerCase().contains(paramString2.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	//method to compare 2 lists to check for common data
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