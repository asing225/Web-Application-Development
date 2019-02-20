package com.asu.ser422.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asu.ser422.model.WinningGuess;
import com.asu.ser422.model.Data;
import com.asu.ser422.model.GameLogic;
import com.asu.ser422.model.Guess;

/**
 * @author amanjotsingh
 * */

@SuppressWarnings("serial")
public class PlayerServlet extends HttpServlet{
	
	private String makeGuessPage = "makeGuess.jsp";
	private String correctGuessPage = "correctGuess.jsp";
	private String duplicateGuessPage = "duplicateGuess.jsp";
	private String incorrectGuessPage = "incorrectGuess.jsp";
	private String computerWins = "computerWins.jsp";
	private String InvalidAction = "invalidAction.jsp";
	private List<String> playerRooms;
    private List<String> playerSuspects;
    private List<String> playerWeapons;
    private List<String> computerRooms;
    private List<String> computerSuspects;
    private List<String> computerWeapons;
	
    public void init(ServletConfig config) throws ServletException { 
    	super.init(config);
   	}
    
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		res.setStatus(res.SC_OK);
		res.setContentType("text/html");
		req.getRequestDispatcher(makeGuessPage).forward(req, res);
	}
	
	// Use doPost to start a new game. When user enters name, submit the form to post and create new session
	@SuppressWarnings("unused")
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		// Creates a new session if one does not exist
        HttpSession session = req.getSession(true);
        if(req.getParameter("playerName") == null || req.getParameter("playerName") == "") {
        	res.setStatus(res.SC_BAD_REQUEST);
        	req.getRequestDispatcher(InvalidAction).forward(req, res);
        }
        session.setAttribute("playerName", req.getParameter("playerName"));
        
     // If a new session, generate player's cards and computer's cards and add them to the current session
        // Additionally, randomly generate a winning secret and add that to the current session
        if (session.isNew()){

            Guess winningSecret = new WinningGuess().getWinningSecret();
            Data data = new Data();
            GameLogic logic = new GameLogic(winningSecret, data);
            
            playerRooms = logic.getPlayerRooms();
            playerSuspects = logic.getPlayerSuspects();
            playerWeapons = logic.getPlayerWeapons();

            computerRooms = logic.getComputerRooms();
            computerSuspects = logic.getComputerSuspects();
            computerWeapons = logic.getComputerWeapons();

            session.setAttribute("playerRoomsList", playerRooms);
            session.setAttribute("computerRoomsList", computerRooms);
            session.setAttribute("playerSuspectsList", playerSuspects);
            session.setAttribute("computerSuspectsList", computerSuspects);
            session.setAttribute("playerWeaponsList", playerWeapons);
            session.setAttribute("computerWeaponsList", computerWeapons);
            session.setAttribute("winningSecret", winningSecret);
        }
        String action = req.getParameter("action");
        if(action.equals("Guess")) {
        	session = req.getSession(false);
        	if(session == null){
                res.sendRedirect(res.encodeRedirectURL("/"));
                return;
            }
        	// Create a new guess object which would represent the current guess the player has made
            Guess playerGuess = new Guess(req.getParameter("suspect"),
                    req.getParameter("weapon"),
                    req.getParameter("room"));
            
        	session.setAttribute("playerGuess", playerGuess);
            
        	Guess winningSecret = (Guess) session.getAttribute("winningSecret");
        	
            // Linked List to maintain the history of guesses in order
            LinkedList<Guess> guessHistory = (LinkedList<Guess>) session.getAttribute("guessHistory");
            if(guessHistory == null)
                guessHistory = new LinkedList<Guess>();

            // Compare the current player guess with guesses in the history to check for a duplicate guess
            if(guessHistory.contains(playerGuess)){
            	res.setStatus(res.SC_OK);
            	req.getRequestDispatcher(duplicateGuessPage).forward(req, res);
            }
            // If not duplicate, add the current guess to the history
            guessHistory.add(playerGuess);
            session.setAttribute("guessHistory", guessHistory);


            // Check if the current guess is the winning guess
            if(playerGuess.equals(winningSecret)) {
            	res.setStatus(res.SC_OK);
            	req.getRequestDispatcher(correctGuessPage).forward(req, res);
            }
            else {
            	session.setAttribute("wrongPlayerGuess", playerGuess.whichIsWrong(winningSecret));
            	List<String> computerRooms = (List<String>) session.getAttribute("computerRoomsList");
                List<String> computerSuspects = (List<String>) session.getAttribute("computerSuspectsList");
                List<String> computerWeapons = (List<String>) session.getAttribute("computerWeaponsList");

                // Randomly generate a computer guess and keep generating until the guess is unique by checking
                // against history
                GameLogic logic = new GameLogic();
                Guess computerGuess = logic.computerGuess(guessHistory, computerRooms, computerSuspects, computerWeapons);
                session.setAttribute("compGuess", computerGuess);
            	
                guessHistory.add(computerGuess);
                session.setAttribute("guessHistory", guessHistory);
                
            	// If computer's guess is correct. Responds back with a message and ends the game
                if(computerGuess.equals(winningSecret)){
                	res.setStatus(res.SC_OK);
                    req.getRequestDispatcher(computerWins).forward(req, res);
                }

                // Otherwise respond by telling the user the computer guess was incorrect and display one incorrect
                // component of the guess
                else{
                	session.setAttribute("wrongComputerGuess", computerGuess.whichIsWrong(winningSecret));
                	res.setStatus(res.SC_OK);
                	req.getRequestDispatcher(incorrectGuessPage).forward(req, res);
                }
            }
        }
        else if(action.equals("Submit")) {
        	res.setStatus(res.SC_OK);
        	req.getRequestDispatcher(makeGuessPage).forward(req, res);
        }
        else {
        	res.setStatus(res.SC_BAD_REQUEST);
        	req.getRequestDispatcher(InvalidAction).forward(req, res);
        }
	}
}
