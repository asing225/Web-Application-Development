package edu.asu.ser422.phone.model;

/**
 * @author amanjotsingh
 * Class with the attributes of phone entry.
 * */

public class PhoneEntry {
	private String firstName;
	private String lastName;
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}
	private String phone;
	
	public PhoneEntry(String name, String lname, String phone)
    {
	this.firstName  = name;
	this.lastName  = lname;
	this.phone = phone;
    }

    public void changeName(String newfname, String newlname) {
    	firstName = newfname;
    	try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	lastName  = newlname;
    }
    public String toString()
    { return firstName + "\n" + lastName + "\n" + phone; }
}
