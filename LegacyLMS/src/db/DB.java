package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DB {
	private static final String DRIVER = "org.mariadb.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/";
	private static final String ID = "lms";
	private static final String PW = "accounts";
	public static Table admins, librarians, customers, books, rents;
	private String database;
	private static Statement statement;

	public DB(String database) {
		this.database = database;
		admins = Table.ADMINS;
		librarians = Table.LIBRARIANS;
		customers = Table.CUSTOMERS;
		books = Table.BOOKS;
		rents =  Table.RENTS;
	}

	public void createDatabase() throws ClassNotFoundException, SQLException {
		System.out.println("About to load the driver");
		Class.forName(DRIVER);
		System.out.println("About to connect ...");
		Connection con = DriverManager.getConnection(URL, "root", "");
		statement = con.createStatement();
		String query = "CREATE DATABASE " + database;
		statement.executeUpdate(query);
		query = "GRANT ALL PRIVILEGES ON " + database + ".* to " + ID + 
				"@localhost IDENTIFIED BY "+ "\'" + PW + "\'";
		statement.executeQuery(query);
		query = "USE " + database;
		statement.executeQuery(query);
		query = "CREATE TABLE " + admins + "("
				+ "aid CHAR(15) NOT NULL,"
				+ "pw CHAR(15) NOT NULL,"
				+ "PRIMARY KEY(aid)"
				+ ")";
		statement.executeQuery(query);
		query = "CREATE TABLE " + librarians + "("
				+ "lid CHAR(15) NOT NULL,"
				+ "pw CHAR(15) NOT NULL,"
				+ "lname CHAR(30) NOT NULL,"
				+ "contact CHAR(13) NOT NULL,"
				+ "PRIMARY KEY(lid)"
				+ ")";
		statement.executeQuery(query);
		query = "CREATE TABLE " + customers + "("
				+ "cid INTEGER,"
				+ "cname CHAR(30),"
				+ "contact CHAR(13),"
				+ "PRIMARY KEY(cid)"
				+ ")";
		statement.executeQuery(query);
		query = "CREATE TABLE " + books + "("
				+ "bid CHAR(30) NOT NULL,"
				+ "title CHAR(30) NOT NULL,"
				+ "author CHAR(30) NOT NULL,"
				+ "status ENUM('IN', 'OUT'),"
				+ "stock INTEGER NOT NULL,"
				+ "PRIMARY KEY(bid)"
				+ ")";
		statement.executeQuery(query);
		query = "CREATE TABLE " + rents + "("
				+ "cid INTEGER NOT NULL,"
				+ "bid CHAR(10) NOT NULL,"
				+ "bdate DATE NOT NULL,"
				+ "rdate DATE NOT NULL,"
				+ "PRIMARY KEY(cid, bid)"
				+ ")";
		statement.executeQuery(query);
		System.out.println("Database created");
	}

	public static void connect() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection con = DriverManager.getConnection(URL, "root", "");
		statement = con.createStatement();
		System.out.println("Database connected!");
	}

	public void connectDatabase() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		DriverManager.getConnection(URL + database, ID, PW);
		System.out.println("Database connected!");
	}

	public static ResultSet executeQuery(String query) throws SQLException {
		return statement.executeQuery(query);
	}

	public String getDatabaseName() {
		return database;
	}

	public static void insertAdmin(String id, String pw) throws SQLException {
		String query = "INSERT INTO admins VALUES "
				+ "('" + id + "', '" + pw + "');";
		executeQuery(query);
	}
	
	public static void insertLibrarian(Librarian l) throws SQLException {
		String query = "INSERT INTO librarians VALUES "
				+ "('" + l.getLid() + "', '" + l.getPw() + "', '" 
				+ l.getName() + "', '" + l.getContact() + "');";
		executeQuery(query);
	}

	public static void insertBook(Book b) throws SQLException {
		String query = "INSERT INTO books VALUES "
				+ "('" + b.getBid() + "', '" + b.getTitle() + "', '" 
				+ b.getAuthor() + "', 'IN', " + b.getStock() + ");";
		executeQuery(query);
	}
	
	public static void insertCustomer(Customer c) throws SQLException {
		String query = "INSERT INTO customers VALUES "
				+ "(" + c.getCid() + ", '" + c.getCname() + "', '" 
				+ c.getContact() + "');";
		executeQuery(query);
	}

	public static ResultSet getLMSList() throws SQLException {
		String query = "SHOW DATABASES LIKE '_%lms';";
		return executeQuery(query);
	}

	public static AccountType isValidIdPw(String id, String pw) throws SQLException {
		String query = "SELECT * FROM admins WHERE aid = '" + id + "' and pw = '" + pw + "';";
		ResultSet result = executeQuery(query);
		if (result.first())
			return AccountType.ADMIN;
		else {
			query = "SELECT * FROM librarians WHERE lid = '" + id + "' and pw = '" + pw + "';";
			result = executeQuery(query);
			if (result.first())
				return AccountType.LIBRARIAN;
		}
		return AccountType.FALSE;
	}

	public static ResultSet selectAllFromTable(Table tableType) throws SQLException {
		String query = "SELECT * FROM " + tableType + ";";
		return executeQuery(query);
	}
	
	public static <E> void deleteFromTable(Table tableType, List<E> items) throws SQLException {
		String query;
		switch (tableType) {
		case LIBRARIANS:
			for (Librarian l: (List<Librarian>)items) {
				query = "DELETE FROM " + tableType + " WHERE lid='" + l.getLid() + "';";
				executeQuery(query);
			}
			break;
		case BOOKS:
			for (Book b: (List<Book>)items) {
				query = "DELETE FROM " + tableType + " WHERE bid='" + b.getBid() + "';";
				executeQuery(query);
			}
			break;
		case CUSTOMERS:
			for (Customer c: (List<Customer>)items) {
				query = "DELETE FROM " + tableType + " WHERE cid=" + c.getCid() + ";";
				executeQuery(query);
			}
			break;
		default:
			System.out.println("Wrong table type!");
		}
	}
	
	public static <E> void editFromTable(Table tableType, E e1, E e2) throws SQLException {
		String query;
		switch(tableType) {
		case LIBRARIANS:
			Librarian l1 = (Librarian)e1;
			Librarian l2 = (Librarian)e2;
			query = "UPDATE librarians SET lid='" + l2.getLid() +
					"', lname='" + l2.getName() + "', contact='" + l2.getContact() +
					"' WHERE lid='" + l1.getLid() + "';";
			executeQuery(query);
			break;
		case BOOKS:
			Book b1 = (Book)e1;
			Book b2 = (Book)e2;
			query = "UPDATE books SET bid = '" + b2.getBid() +
					   "', title = '" + b2.getTitle() + "', author = '" + b2.getAuthor() + 
					   "', status = '" + b2.getStatus() + "', stock = " + b2.getStock() + 
					   " WHERE bid = '" + b1.getBid() + "';";
			executeQuery(query);
			break;
		case CUSTOMERS:
			Customer c1 = (Customer)e1;
			Customer c2 = (Customer)e2;
			query = "UPDATE customers SET cid=" + c2.getCid() +
					", cname='" + c2.getCname() + "', contact='" + c2.getContact() +
					"' WHERE cid=" + c1.getCid() +";";
			executeQuery(query);
			break;
		default:
			System.out.println("Wrong table type!");		
		}
	}

	public static ResultSet searchFromTable(Table tableType, HashMap<Integer, String> criteria) throws SQLException {
		String query = "SELECT * FROM " + tableType + " WHERE ";
		HashMap<Integer, String> searchQueries = new HashMap<>();
		if (tableType == Table.LIBRARIANS) {
			searchQueries.put(1, "lid='");
			searchQueries.put(2, "name='");
			searchQueries.put(3, "contact='");
		}
		else if (tableType == Table.BOOKS) {
			searchQueries.put(1, "bid='");
			searchQueries.put(2, "title='");
			searchQueries.put(3, "author='");
			searchQueries.put(4, "status='");
		}
		else {
			searchQueries.put(1, "cid='");
			searchQueries.put(2, "cname='");
			searchQueries.put(3, "contact='");
		}
		int count = 0;
		Set<Integer> key = criteria.keySet();
		Iterator<Integer> it = key.iterator();
		while (it.hasNext()) {
			int k = it.next();
			query += searchQueries.get(k);
			query += criteria.get(k) + "'";
			if (count < criteria.size()-1)
				query += " and ";
			else
				query += ";";
			count++;
		}
		return executeQuery(query);
	}
	
	public static void borrowBooks(Customer c, List<Book> books, String borrowDate, String returnDate) throws SQLException {
		for (Book b: books) {
			int stock = b.getStock()-1;
			String query = "UPDATE books SET stock = " + stock + " WHERE bid = '" + b.getBid() + "';";
			if (stock == 0)
				query = "UPDATE books SET status='OUT', stock=0 WHERE bid ='" + b.getBid() + "';";
			executeQuery(query);
			query = "INSERT INTO rents VALUES(" + c.getCid() +
					", '" + b.getBid() + "', '" + borrowDate + "', '" + returnDate + "');";
			executeQuery(query);
		}
	}
	
	public static void returnBooks(int cid, List<Book> books) throws SQLException {
		for (Book b: books) {
			String query = "DELETE FROM rents WHERE cid=" + cid + " and bid='" + b.getBid() + "';";
			executeQuery(query);
			if (b.getStock() == 0)
				query = "UPDATE books SET status='IN', stock=stock+1 WHERE bid='" + b.getBid() + "';";
			else
				query = "UPDATE books SET stock=stock+1 WHERE bid='" + b.getBid() + "';";
			executeQuery(query);
		}
	}
}
