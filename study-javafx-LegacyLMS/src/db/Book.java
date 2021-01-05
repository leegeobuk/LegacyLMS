package db;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private SimpleStringProperty bid;
	private SimpleStringProperty title;
	private SimpleStringProperty author;
	private BookStatus status;
	private SimpleIntegerProperty stock;
	
	public Book(String bid, String title, String author, BookStatus status, int stock) {
		this.bid = new SimpleStringProperty(bid);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.status = status;
		this.stock = new SimpleIntegerProperty(stock);
	}

	public String getBid() {
		return bid.get();
	}
	
	public String getTitle() {
		return title.get();
	}

	public String getAuthor() {
		return author.get();
	}
	
	public BookStatus getStatus() {
		return status;
	}
	
	public int getStock() {
		return stock.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public void setAuthor(String author) {
		this.author.set(author);
	}
	
	public void setStatus(BookStatus status) {
		this.status = status;
	}
	
	public void setStock(int stock) {
		this.stock.set(stock);
	}
	
	public void edit(Book book) {
		this.bid = book.bid;
		this.title = book.title;
		this.author = book.author;
		this.status = book.status;
		this.stock = book.stock;
	}
	
	public boolean equals(Object o) {
		Book b = (Book)o;
		return this.getBid().equals(b.getBid()) &&
			   this.getTitle().equals(b.getTitle()) && 
			   this.getAuthor().equals(b.getAuthor());
	}
}
