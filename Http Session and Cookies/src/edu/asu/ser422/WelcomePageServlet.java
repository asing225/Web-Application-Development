package edu.asu.ser422;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet class for the home page with search option for user
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class WelcomePageServlet extends HttpServlet{
	
	public void init(ServletConfig paramServletConfig) throws ServletException {
		super.init(paramServletConfig);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		//processing the request cookies
		String[] processedCookies = processCookies(req);
		String firstName = processedCookies[0];
		String lastName = processedCookies[1];
		String languages = processedCookies[2];
		String days = processedCookies[3];
		String dreamJob = processedCookies[4];
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		res.setStatus(res.SC_OK);
		out.println("<html>\n<body>");
		out.println("<a href = \"./name\">Create a new programmer</a>");
		out.println("<form action = \"./programmers\" method = \"Get\">");
        out.println("<label for=\"firstName\">First Name: </label>");
        
        //writing response based on the client request
        if(firstName == null || firstName == "") {
        	out.println("<input type=\"text\" name=\"firstname\" /><br>");
        }
        else {
        	out.println("<input type=\"text\" name=\"firstname\" value=\""+ firstName +"\"/><br>");
        }
        
        out.println("<label for=\"lastName\">Last Name: </label>");
        if(lastName == null || lastName == "") {
        	out.println("<input type=\"text\" name=\"lastname\" /><br>");
        }
        else {
        	out.println("<input type=\"text\" name=\"lastname\" value=\""+ lastName +"\"/><br>");
        }
        
        out.println("<label for=\"languages\">Programming Languages: </label>");
        if(languages == null || languages == "") {
        	out.println("<input type=\"text\" name=\"languages\" /><br>");
        }
        else {
        	out.println("<input type=\"text\" name=\"languages\" value=\""+ languages +"\"/><br>");
        }
        
        out.println("<label for=\"availability\">Days available to meet: </label>");
        if(days == null || days == "") {
        	out.println("<input type=\"text\" name=\"availability\" /><br>");
        }
        else {
        	out.println("<input type=\"text\" name=\"availability\" value=\""+ days +"\"/><br>");
        }
        
        out.println("<label for=\"dreamJob\">Dream Job:</label>");
        if(dreamJob == null || dreamJob == "") {
        	out.println("<input type=\"text\" name=\"dreamjob\" /><br>");
        }
        else {
        	out.println("<input type=\"text\" name=\"dreamjob\" value=\""+ dreamJob +"\"/><br>");
        }
        
        out.println("<input type = \"submit\" value = \"search\"/>");
        out.println("</form>\n</body>\n</html>");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		//get the existing session and invalidate
		HttpSession sess = req.getSession();
		sess.invalidate();
		doGet(req, res);
	}
	
	//method to check if client sent any cookie
	private String[] processCookies(HttpServletRequest req) {
		Cookie[] requestCookies = req.getCookies();
		if(requestCookies == null || requestCookies.length == 1) {
			String[] resultCookies = {null, null, null, null, null};
			return resultCookies;
		}
		HashMap<String, String> cookieMap = new HashMap<String, String>();
		for(int i = 0; i < requestCookies.length; i++) {
			cookieMap.put(requestCookies[i].getName(), requestCookies[i].getValue());
		}
		String firstName = cookieMap.get("First");
		String lastName = cookieMap.get("Last");
		String dreamJob = cookieMap.get("Dream");
		String days = cookieMap.get("Days").replaceAll(":", ",");
		String languages = cookieMap.get("Languages").replaceAll(":", ","); 		
		
		String[] resultCookies = {firstName, lastName, languages, days, dreamJob};
		return resultCookies;
	}
}