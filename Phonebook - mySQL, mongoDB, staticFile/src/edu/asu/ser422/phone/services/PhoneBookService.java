package edu.asu.ser422.phone.services;

import java.util.List;

import edu.asu.ser422.phone.model.PhoneEntry;

/**
 * @author amanjotsingh
 * Java Interface with method initialization.
 * */

public interface PhoneBookService {
	public List<PhoneEntry> listEntries();
    public int removeEntry(String num);
    public int addEntry(String fname, String lname, String num);
}