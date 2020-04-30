package ok_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import buttons.GeneralButton;
import db.Book;
import db.BookStatus;
import db.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.BookWorkSpace;

public class AddBookOK extends Button implements GeneralButton {
	private BookWorkSpace bookWorkSpace;
	private Button add;
	private TextField bidField;
	private TextField titleField;
	private TextField authorField;
	private TextField stockField;
	private Alert fail;
	
	public AddBookOK(BookWorkSpace bookWorkSpace, Button ok, TextField bidField, TextField titleField, TextField authorField, TextField stockField) {
		this.bookWorkSpace = bookWorkSpace;
		this.add = ok;
		this.add.setDefaultButton(true);
		this.bidField = bidField;
		this.titleField = titleField;
		this.authorField = authorField;
		this.stockField = stockField;
	}
	
	@Override
	public void buttonPressed() {
		add.setOnAction(e -> {
			System.out.println("Add book pressed!");
			try {
				Book book = new Book(bidField.getText(), titleField.getText(), authorField.getText(), BookStatus.IN, Integer.parseInt(stockField.getText()));
				DB.insertBook(book);
				bookWorkSpace.clearAndUpdateTable();
				add.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while adding books.");
				String header = "Add book failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Add Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while adding books.");
				String header = "Add book failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Add Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
