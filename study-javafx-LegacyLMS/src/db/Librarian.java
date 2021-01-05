package db;

import javafx.beans.property.SimpleStringProperty;

public class Librarian {
	private SimpleStringProperty lid;
	private SimpleStringProperty pw;
	private SimpleStringProperty name;
	private SimpleStringProperty contact;
	
	public Librarian(String lid, String pw, String name, String contact) {
		this.lid = new SimpleStringProperty(lid);
		this.pw = new SimpleStringProperty(pw);
		this.name = new SimpleStringProperty(name);
		this.contact = new SimpleStringProperty(contact);
	}
	
	public String getLid() {
		return lid.get();
	}
	
	public String getPw() {
		return pw.get();
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getContact() {
		return contact.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public void setContact(String contact) {
		this.contact.set(contact);
	}
}
