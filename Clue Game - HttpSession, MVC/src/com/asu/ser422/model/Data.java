package com.asu.ser422.model;

/**
 * @author amanjotsingh
 * Class for all the data for the suspects, rooms and weapons.
 */

public class Data {
	private String suspects = "Miss Scarlet, Professor Plum, Mrs. Peacock, Reverend Green, Colonel Mustard, Mrs. White";
    private String rooms = "Kitchen, Ballroom, Conservatory, Dining room, Lounge, Hall, Study, Library, Billiard Room";
    private String weapons = "Candlestick, Dagger, Lead pipe, Revolver, Rope, Spanner";
    
    private String[] suspectList = {"Miss Scarlet", "Professor Plum", "Mrs. Peacock", "Reverend Green", "Colonel Mustard", "Mrs. White"};
    private String[] roomList = {"Kitchen", "Ballroom", "Conservatory", "Dining room", "Lounge", "Hall", "Study", "Library", "Billiard Room"};
    private String[] weaponList = {"Candlestick", "Dagger", "Lead pipe", "Revolver", "Rope", "Spanner"};
    
    public String getSuspects() {
    	return suspects;
    }
    
    public String getRooms() {
    	return rooms;
    }
    
    public String getWeapons() {
    	return weapons;
    }

	public String[] getSuspectList() {
		return suspectList;
	}

	public String[] getRoomList() {
		return roomList;
	}

	public String[] getWeaponList() {
		return weaponList;
	}
}
