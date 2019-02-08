package edu.asu.ser422;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * Servlet class to enter dream job while creating new programmer
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class DreamJobServlet extends HttpServlet {
	
	public void init(ServletConfig paramServletConfig) throws ServletException {
		super.init(paramServletConfig);
	}
	
	public void doGet (HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		doPost(req, res);
	}

    public void doPost (HttpServletRequest req, HttpServletResponse res)
        throws javax.servlet.ServletException, java.io.IOException {
    	
    	//get the current session
    	HttpSession sess = req.getSession();
        
    	//get request parameters and set in session attributes
    	String daysEntered = req.getParameter("days");
    	if(daysEntered != null) {
    		sess.setAttribute("days", daysEntered.replaceAll("\\s+",""));
    	}
    	
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        res.setStatus(res.SC_OK);
        String jobEntered = (String)sess.getAttribute("dreamjob");
        
        //writing out response based on the user input
        if(jobEntered == null) {
        	out.println("<html><body>\n<form action = \"./final\" method = \"post\">");
            out.println("<label>Dream Job: </label>");
            out.println("<input type = \"text\" name = \"dreamjob\"><br><br>");
            out.println("<button type=\"submit\" formaction = \"./days\">previous</button>");
            out.println("<input type = \"submit\" value = \"next\">");
            out.println("</form></body></html>");
        }
        else {
        	out.println("<html><body>\n<form action = \"./final\" method = \"post\">");
            out.println("<label>Dream Job: </label>");
            out.println("<input type = \"text\" name = \"dreamjob\" value = "+ jobEntered +" ><br><br>");
            out.println("<button type=\"submit\" formaction = \"./days\">previous</button>");
            out.println("<input type = \"submit\" value = \"next\">");
            out.println("</form></body></html>");
        }
    }
}
