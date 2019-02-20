package com.asu.ser422.model;

import java.util.Random;
import com.asu.ser422.model.Guess;

/**
 * @author Amanjot Singh
 * Class to generate the winning guess for each game.
 * */

public class WinningGuess {
	
	private Data dataObject = new Data();
	private String[] suspects = dataObject.getSuspectList();
	private String[] weapons = dataObject.getWeaponList();
	private String[] rooms = dataObject.getRoomList();
	
	//randomly generating a winning guess
	Random r = new Random();
    private Guess winningSecret = new Guess(suspects[r.nextInt(suspects.length)],
            weapons[r.nextInt(weapons.length)],
            rooms[r.nextInt(rooms.length)]);
    
    public Guess getWinningSecret() {
    	return winningSecret;
    }
}
