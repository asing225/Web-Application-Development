package com.asu.ser422.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author amanjotsingh
 * Contains game logic for distributing items between player and computer
 * and logic to calculate computer guess randomly.
 * */

public class GameLogic {
	
	List<String> playerRooms = new ArrayList<>();
    List<String> playerSuspects = new ArrayList<>();
    List<String> playerWeapons = new ArrayList<>();
    List<String> computerRooms = new ArrayList<>();
    List<String> computerSuspects = new ArrayList<>();
    List<String> computerWeapons = new ArrayList<>();
    
    public GameLogic() {}
    
    //calculating the winning guess for the game
    public GameLogic(Guess winningSecret, Data data) {
    	
    	String[] suspects = data.getSuspectList();
        String[] rooms = data.getRoomList();
        String[] weapons = data.getWeaponList();
    	
    	for(int i = 0;i<suspects.length;i++){
            if(winningSecret.suspect.equals(suspects[i])){
                playerSuspects.add(suspects[i]);
                computerSuspects.add(suspects[i]);
            } else if(i%2 == 0)
                playerSuspects.add(suspects[i]);
            else
                computerSuspects.add(suspects[i]);
        }
        for(int i = 0;i<rooms.length;i++){
            if(winningSecret.room.equals(rooms[i])){
                playerRooms.add(rooms[i]);
                computerRooms.add(rooms[i]);
            }else if(i%2 == 0)
                playerRooms.add(rooms[i]);
            else
                computerRooms.add(rooms[i]);
        }
        for(int i = 0;i<weapons.length;i++){
            if(winningSecret.weapon.equals(weapons[i])){
                playerWeapons.add(weapons[i]);
                computerWeapons.add(weapons[i]);
            }else if(i%2 == 0)
                playerWeapons.add(weapons[i]);
            else
                playerWeapons.add(weapons[i]);
        }
	}
    
    //generating random guess for computer player
	public Guess computerGuess(LinkedList<Guess> guessHistory, List<String> compRooms, List<String> compSuspects, List<String> compWeapons) {
    	Guess computerGuess = new Guess();
        Random r = new Random();
        do {
            computerGuess.room = compRooms.get(r.nextInt(compRooms.size()));
            computerGuess.weapon = compWeapons.get(r.nextInt(compWeapons.size()));
            computerGuess.suspect = compSuspects.get(r.nextInt(compSuspects.size()));

        }while (guessHistory.contains(computerGuess));
        return computerGuess;
    }
    
    public List<String> getPlayerRooms() {
		return playerRooms;
	}

	public List<String> getPlayerSuspects() {
		return playerSuspects;
	}

	public List<String> getPlayerWeapons() {
		return playerWeapons;
	}

	public List<String> getComputerRooms() {
		return computerRooms;
	}

	public List<String> getComputerSuspects() {
		return computerSuspects;
	}

	public List<String> getComputerWeapons() {
		return computerWeapons;
	}

}
