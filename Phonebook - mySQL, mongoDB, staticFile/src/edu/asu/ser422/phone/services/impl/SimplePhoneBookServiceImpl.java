package edu.asu.ser422.phone.services.impl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.asu.ser422.phone.model.PhoneEntry;
import edu.asu.ser422.phone.services.PhoneBookService;

/**
 * @author amanjotsingh
 * Class to implement the phone entry add, delete and list methods for the
 * static text file as data store.
 * */

public class SimplePhoneBookServiceImpl implements PhoneBookService{
	public static final String DEFAULT_FILENAME = "phonebook.txt";
	private Map<String, PhoneEntry> _pbook = new HashMap<String, PhoneEntry>();
	
	public SimplePhoneBookServiceImpl() throws IOException {
		this(DEFAULT_FILENAME); 
	}
	public SimplePhoneBookServiceImpl(String fname) throws IOException {
		this(SimplePhoneBookServiceImpl.class.getClassLoader().getResourceAsStream(fname));
	}
	public SimplePhoneBookServiceImpl(InputStream is) throws IOException {
		this(new BufferedReader(new InputStreamReader(is)));
	}
	private SimplePhoneBookServiceImpl(BufferedReader br) throws IOException {	
		String name = null;
		String lname = null;
		String phone = null;

		try {
			String nextLine = null;
			while ( (nextLine=br.readLine()) != null)
			{
				name  = nextLine;
				lname = br.readLine();
				phone = br.readLine();
				addEntry(name, lname, phone);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error process phonebook");
			throw new IOException("Could not process phonebook file");
		}
	}
	public void savePhoneBook(String fname)
	{
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(fname));
			List<PhoneEntry> entries = listEntries();
			for (int i = 0; i < entries.size(); i++)
				pw.println(entries.get(i));

			pw.close();
		}
		catch (Exception exc)
		{ 
			exc.printStackTrace(); 
			System.out.println("Error saving phone book");
		}
	}

	public boolean editEntry(String phone, String fname, String lname) {
		System.out.println("Trying to edit entries");
		PhoneEntry pentry = _pbook.get(phone);
		System.out.println("not null------------" + fname + lname);
		pentry.changeName(fname, lname);
		System.out.println("called changeName");
		return true;
	}

	public int addEntry(String fname, String lname, String phone)
	{ 
		addEntry(phone, new PhoneEntry(fname, lname, phone));
		return 1;
	}

	public void addEntry(String number, PhoneEntry entry)
	{ _pbook.put(number, entry); }

	public int removeEntry(String phone) {
		if(_pbook.containsKey(phone)) {
			_pbook.remove(phone);
			return 1;
		}
		return 0;
	}

	public List<PhoneEntry> listEntries()
	{
		List<PhoneEntry> rval = new ArrayList<PhoneEntry>();
		int i = 0;
		PhoneEntry nextEntry = null;
		for (Iterator<PhoneEntry> iter = _pbook.values().iterator(); iter.hasNext();) {
			nextEntry = iter.next();
			rval.add(i, nextEntry);
		}
		return rval;
	}
}