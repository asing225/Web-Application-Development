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
 * Servlet class to submit the user input details to write on XML file.
 * Uses JAXB to write on XML
 * @author Amanjot Singh
 * */

@SuppressWarnings("serial")
public class SubmitDetailsServlet extends HttpServlet{
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		
		//get the current http session
		HttpSession sess = req.getSession();
		
		//get request parameter and set in the session attributes
		String dreamJobEntered = req.getParameter("dreamjob");
		if(dreamJobEntered != null) {
			sess.setAttribute("dreamjob", dreamJobEntered);
		}
		
		res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        //get session attributes
        String firstNameInput = (String)sess.getAttribute("firstname");
        String lastNameInput = (String)sess.getAttribute("lastname");
        String languagesInput= (String)sess.getAttribute("languages");
        String daysInput = (String)sess.getAttribute("days");
        String dreamJobInput = (String)sess.getAttribute("dreamjob");
        
        res.setStatus(res.SC_OK);
        out.println("<html><body>\n<form action = \"./newprogrammer\" method = \"POST\">");
        
        //write response based on the user input
        if(firstNameInput != "" && lastNameInput != "") {
        	out.println("<b>Programmer data entered: </b><br><br>");
        	out.println("<label>Firstname: </label>");
        	out.println("<input type = \"text\" value = \"" + firstNameInput + "\" name=\"firstname\" /><br>");
        	out.println("<label>Lastname: </label>");
        	out.println("<input type = \"text\" value = \"" + lastNameInput + "\" name=\"lastname\" /><br>");
        }
        else {
        	out.println("<a href = \"./name\">Go back to enter name</a><br>");
        }
        if(languagesInput != "") {
        	out.println("<label>Languages: </label>");
        	out.println("<input type = \"text\" value = \"" + languagesInput + "\" name=\"languages\" /><br>");
        }
        else {
        	out.println("<a href = \"./languages\">Go back to enter languages</a><br>");
        }
        if(daysInput != "") {
        	out.println("<label>Days: </label>");
        	out.println("<input type = \"text\" value = \"" + daysInput + "\" name=\"availability\" /><br>");
        }
        else {
        	out.println("<a href = \"./days\">Go back to enter days</a><br>");
        }
        if(dreamJobInput != "") {
        	out.println("<label>Dream Job: </label>");
        	out.println("<input type = \"text\" value = \"" + dreamJobInput + "\" name=\"dreamjob\" /><br><br>");
        }
        else {
        	out.println("<a href = \"./dream\">Go back to enter dream job</a><br><br>");
        }
        out.println("<button type=\"submit\" formaction = \"./dream\">previus</button>");
        out.println("<button type=\"submit\" formaction = \"./\">cancel</button>");
        out.println("<input type =\"submit\" value = \"submit\" />");
        out.println("</form></body></html>");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		res.setStatus(res.SC_METHOD_NOT_ALLOWED);
	}
}
