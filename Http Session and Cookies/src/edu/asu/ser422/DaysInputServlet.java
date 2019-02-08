package edu.asu.ser422;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * Servlet class to take days input while creating new programmer
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class DaysInputServlet extends HttpServlet {
	
	public void init(ServletConfig paramServletConfig) throws ServletException {
		super.init(paramServletConfig);
	}
	
	public void doGet (HttpServletRequest req, HttpServletResponse res)
	        throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
		
		//get the current session
        HttpSession sess = req.getSession();
        
        //get request parameter
        String languageEntered = req.getParameter("languages");
        
        //set session attribute
        if(languageEntered != null) {
        	sess.setAttribute("languages", languageEntered.replaceAll("\\s+",""));
        }

        //write out the response
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        res.setStatus(res.SC_OK);
        String daysEntered = (String)sess.getAttribute("days");
        
        //rendering the page based on user input
        if(daysEntered == null) {
        	out.println("<html><body>\n<form action = \"./dream\" method = \"post\">");
            out.println("<label>Days: </label>");
            out.println("<input type = \"text\" name = \"days\"><br><br>");
            out.println("<button type=\"submit\" formaction = \"./languages\">previous</button>");
            out.println("<input type = \"submit\" value = \"next\">");
            out.println("</form></body></html>");
        }
        else {
        	out.println("<html><body>\n<form action = \"./dream\" method = \"post\">");
            out.println("<label>Days: </label>");
            out.println("<input type = \"text\" name = \"days\" value = "+ daysEntered +" ><br><br>");
            out.println("<button type=\"submit\" formaction = \"./languages\">previous</button>");
            out.println("<input type = \"submit\" value = \"next\">");
            out.println("</form></body></html>");
        }
    }
}
