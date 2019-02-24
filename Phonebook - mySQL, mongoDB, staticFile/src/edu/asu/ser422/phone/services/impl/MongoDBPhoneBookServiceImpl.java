package edu.asu.ser422.phone.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import edu.asu.ser422.phone.model.PhoneEntry;
import edu.asu.ser422.phone.services.PhoneBookService;

public class MongoDBPhoneBookServiceImpl implements PhoneBookService {
	
	public List<PhoneEntry> listEntries(){
		MongoClient client = null;
		List<PhoneEntry> list = null;
		try {
			client = new MongoClient("localhost", 27017);
			MongoDatabase db = client.getDatabase("phonebook_app");
			MongoCollection<Document> doc = db.getCollection("phonebook");
			list = new ArrayList<PhoneEntry>();
			FindIterable<Document> document = doc.find();
			MongoCursor<Document> cursor = doc.find().iterator();
			try {
				while(cursor.hasNext()) {
					Document d = cursor.next();
					String fname = (String) d.get("firstname");
					String lname = (String) d.get("lastname");
					String num = (String) d.get("number");
					list.add(new PhoneEntry(fname, lname, num));
				}
			}
			finally {
				cursor.close();
			}
			return list;
		}
		catch(MongoException exc) {
			exc.printStackTrace();
		}
		finally {
			client.close();
		}
		return list;
	}
	
    public int removeEntry(String num) {
    	return 1;
    }
    
    public int addEntry(String fname, String lname, String num) {
    	return 1;
    }
}
