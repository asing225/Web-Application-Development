package edu.asu.ser422;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet class to take firstname and lastname input from the user
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class NameInputServlet extends HttpServlet{
	
	public void init(ServletConfig paramServletConfig) throws ServletException {
		super.init(paramServletConfig);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		//create or get the existing session
		HttpSession sess = req.getSession(true);
		
		//write out the response
		res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        res.setStatus(res.SC_OK);

        //get session attributes
        String firstNameEntered = (String)sess.getAttribute("firstname");
        String lastNameEntered = (String)sess.getAttribute("lastname");
		
        //writing out response based on the user input
        if(firstNameEntered == null && lastNameEntered == null) {
			out.println("<html><body>\n<form action = \"./languages\" method = \"post\">");
			out.println("<label>First Name: </label>");
			out.println("<input type = \"text\" name = \"firstname\" ><br>");
			out.println("<label>Last Name: </label>");
			out.println("<input type = \"text\" name = \"lastname\" ><br><br>");
			out.println("<input type = \"submit\" value = \"next\" >");
			out.println("</form></body></html>");
		}
		else {	        
			out.println("<html><body>\n<form action = \"./languages\" method = \"post\">");
			out.println("<label>First Name: </label>");
			out.println("<input type = \"text\" name = \"firstname\" value = "+ firstNameEntered +" ><br>");
			out.println("<label>Last Name: </label>");
			out.println("<input type = \"text\" name = \"lastname\" value = "+ lastNameEntered + " ><br><br>");
			out.println("<input type = \"submit\" value = \"next\" >");
			out.println("</form></body></html>");
		}
	}
}