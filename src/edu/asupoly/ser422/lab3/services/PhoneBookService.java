package edu.asupoly.ser422.lab3.services;

import java.util.List;
import java.util.Map;

import edu.asupoly.ser422.lab3.model.PhoneEntry;

public interface PhoneBookService {
	
	// PhoneBook methods
	//public List<PhoneEntry> getBookEntries(int phonebookId);
	public List<PhoneEntry> getPhoneBookSubset(int bookId, String fname, String lname);
    public int addPhoneBookEntry(int bookNo, PhoneEntry newEntry);
    
    //PhoneEntry methods
    public Map<String, PhoneEntry> getPhoneEntry(int phone);
    public int createPhoneEntry(String firstname, String lastname, int phone);
    public int updatePhoneEntry(PhoneEntry updatedEntry);
    public int deletePhoneEntry(int toBeDeletedEntry);
    public List<PhoneEntry> getUnlistedEntries();
}
