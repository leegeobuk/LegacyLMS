package db;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
	private SimpleIntegerProperty cid;
	private SimpleStringProperty cname;
	private SimpleStringProperty contact;
	
	public Customer(int cid, String cname, String contact) {
		this.cid = new SimpleIntegerProperty(cid);
		this.cname = new SimpleStringProperty(cname);
		this.contact = new SimpleStringProperty(contact);
	}

	public int getCid() {
		return cid.get();
	}

	public String getCname() {
		return cname.get();
	}

	public String getContact() {
		return contact.get();
	}

	public void setCid(int cid) {
		this.cid.set(cid);
	}

	public void setName(String cname) {
		this.cname.set(cname);
	}

	public void setContact(String contact) {
		this.contact.set(contact);
	}
}
