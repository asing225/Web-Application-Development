package edu.asupoly.ser422.lab3.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhoneBook {
	private List<PhoneEntry> entries;
	
	public PhoneBook() {}

	public List<PhoneEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<PhoneEntry> entries) {
		this.entries = entries;
	}
}
