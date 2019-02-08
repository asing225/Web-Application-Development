package edu.asu.ser422;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * Servlet class to take language input from user while creating new programmer
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class LanguageInputServlet extends HttpServlet {
	
	public void init(ServletConfig paramServletConfig) throws ServletException {
		super.init(paramServletConfig);
	}
	
    public void doPost (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	
    	//get current session
    	HttpSession sess = req.getSession();
    	
    	//setting session attributes using request parameters
    	String firstName = req.getParameter("firstname");
    	String lastName = req.getParameter("lastname");
    	if(firstName != null) {
    		sess.setAttribute("firstname", firstName);
    	}
    	if(lastName != null) {
    		sess.setAttribute("lastname", lastName);
    	}
   
        res.setContentType("text/html");
        res.setStatus(res.SC_OK);
        PrintWriter out = res.getWriter();
        String languageEntered = (String)sess.getAttribute("languages");
        
        //rendering the response based on session attributes
        if(languageEntered == null) {
        	out.println("<html><body>\n<form action = \"./days\" method = \"post\">");
            out.println("<label>Languages: </label>");
            out.println("<input type = \"text\" name = \"languages\"><br><br>");
            out.println("<button type=\"submit\" formaction = \"./name\">previous</button>");
            out.println("<input type = \"submit\" value = \"next\">");
            out.println("</form></body></html>");
        }
        else {
        	out.println("<html><body>\n<form action = \"./days\" method = \"post\">");
            out.println("<label>Languages: </label>");
            out.println("<input type = \"text\" name = \"languages\" value = "+ languageEntered +" ><br><br>");
            out.println("<button type=\"submit\" formaction = \"./name\">previous</button>");
            out.println("<input type = \"submit\" value = \"next\">");
            out.println("</form></body></html>");
        }
    }
    
    public void doGet (HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	doPost(req, res);
    }
}