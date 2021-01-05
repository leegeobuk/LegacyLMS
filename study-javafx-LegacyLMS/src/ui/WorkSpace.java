package ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import db.Book;
import db.BookStatus;
import db.Customer;
import db.DB;
import db.Librarian;
import db.Table;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public abstract class WorkSpace<E> extends Stage {
	protected Mode mode = Mode.VIEW;
	protected Table tableType;
	protected BorderPane mainPane = new BorderPane();
	protected HBox iconBar = new HBox();
	protected TableView<E> table = new TableView<>();
	protected HashMap<Integer, String> searchCriteria = new HashMap<>();
	protected E selected;
	
	public WorkSpace(Table tableType) {
		this.tableType = tableType;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public Table getType() {
		return tableType;
	}
	
	public TableView<E> getTable() {
		return table;
	}
	
	public HashMap<Integer, String> getSearchCriteria() {
		return searchCriteria;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public void setSearchCriteria(HashMap<Integer, String> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public void updateTable() throws SQLException {
		ResultSet result;
		if (mode == Mode.VIEW)
			result = DB.selectAllFromTable(tableType);
		else
			result = DB.searchFromTable(tableType, searchCriteria);
		while (result.next()) {
			E item = getTableItems(result, tableType);
			table.getItems().add(item);
		}
	}
	
	public void clearAndUpdateTable() throws SQLException {
		table.getItems().clear();
		updateTable();
	}

	private E getTableItems(ResultSet result, Table type) throws SQLException {
		switch (type) {
		case LIBRARIANS:
			String lid = result.getString(1);
			String pw = result.getString(2);
			String lname = result.getString(3);
			String lcontact = result.getString(4);
			Librarian librarian = new Librarian(lid, pw, lname, lcontact);
			return (E)librarian;
		case BOOKS:
			String bid = result.getString(1);
			String title = result.getString(2);
			String author = result.getString(3);
			BookStatus status = result.getString(4).equals("IN") ? BookStatus.IN : BookStatus.OUT;
			int stock = result.getInt(5);
			Book book = new Book(bid, title, author, status, stock);
			return (E)book;
		case CUSTOMERS:
			int cid = result.getInt(1);
			String cname = result.getString(2);
			String contact = result.getString(3);
			Customer customer = new Customer(cid, cname, contact);
			return (E)customer;
		default:
			System.out.println("Wrong table type!");
			return null;
		}
	}
	
	public abstract void initWorkSpace();
	
	public abstract void foolProof();
	
	public abstract void showWorkSpace() throws SQLException;
}
