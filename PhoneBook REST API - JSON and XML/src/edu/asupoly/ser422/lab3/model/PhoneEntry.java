package edu.asupoly.ser422.lab3.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhoneEntry {
	
	private String firstname;
    private String lastname;
    private int phone;

    public PhoneEntry() {}
    
    public PhoneEntry(String name, String lname, int phone) {
		this.firstname  = name;
		this.lastname  = lname;
		this.phone = phone;
    }
    
    public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String toString() {
		return "Firstname " + getFirstname() + ", lastName " + getLastname() + ", phone " + getPhone();
	}
    
}