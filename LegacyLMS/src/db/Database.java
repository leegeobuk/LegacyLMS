package db;

import javafx.beans.property.SimpleStringProperty;

public class Database {
	private SimpleStringProperty name;
	
	public Database(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public String getName() {
		return name.get();
	}
	
	public boolean equals(Object obj) {
		Database db = (Database)obj;
		if (getName().equals(db.getName()))
			return true;
		return false;		
	}
}
