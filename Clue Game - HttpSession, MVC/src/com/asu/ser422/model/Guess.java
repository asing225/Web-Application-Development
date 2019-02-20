package com.asu.ser422.model;

import java.util.Random;
import java.util.Vector;

/**
 * @author amanjotsingh
 * Class for player and computer guess object with methods to compare
 * guesses.
 * */

public class Guess {
    public String suspect;
	public String weapon;
    public String room;

    public Guess() {}

    public Guess(String suspect, String weapon, String room) {
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }
    
    //compring 2 guess objects
    @Override
    public boolean equals(Object obj) {
        Guess tempGuess = (Guess) obj;
        if(this.suspect.equals(tempGuess.suspect) &&
                this.room.equals(tempGuess.room) &&
                this.weapon.equals(tempGuess.weapon)){
            tempGuess = null;
            return true;
        }
        else{
            tempGuess = null;
            return false;
        }
    }

    @Override
    public String toString() {
        return "Guess {" +
                "suspect='" + suspect + '\'' +
                ", weapon='" + weapon + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
    
    //comparing current guess with winning guess
    public String whichIsWrong(Guess winningSecret){
        Random r = new Random();
        Vector<String> wrongItems = new Vector<>();
        if(!winningSecret.suspect.equals(this.suspect))
            wrongItems.add(this.suspect);
        if(!winningSecret.room.equals(this.room))
            wrongItems.add(this.room);
        if(!winningSecret.weapon.equals(this.weapon))
            wrongItems.add(this.weapon);

        String thisIsWrong = wrongItems.elementAt(r.nextInt(wrongItems.size()));
        wrongItems = null;
        return thisIsWrong;
    }
}
