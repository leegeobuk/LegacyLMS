package ok_buttons;

import java.sql.SQLException;
import buttons.GeneralButton;
import db.Book;
import db.Customer;
import db.DB;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.BookWorkSpace;

public class BorrowBookOK extends Button implements GeneralButton {
	private BookWorkSpace bookWorkSpace;
	private Button borrow;
	private TextField idField;
	private TextField nameField; 
	private TextField phoneField;
	private TextField borrowField;
	private TextField returnField;
	
	public BorrowBookOK(BookWorkSpace bookWorkSpace, Button borrow, TextField idField, 
						TextField nameField, TextField phoneField,
						TextField borrowField, TextField returnField) {
		this.bookWorkSpace = bookWorkSpace;
		this.borrow = borrow;
		this.idField = idField;
		this.nameField = nameField;
		this.phoneField = phoneField;
		this.borrowField = borrowField;
		this.returnField = returnField;
	}

	@Override
	public void buttonPressed() {
		borrow.setOnAction(e -> {
			System.out.println("Borrow book ok pressed!");
			ObservableList<Book> selected = bookWorkSpace.getTable().getSelectionModel().getSelectedItems();
			try {
				int id = Integer.parseInt(idField.getText());
				Customer customer = new Customer(id, nameField.getText(), phoneField.getText());
				String borrowDate = borrowField.getText();
				String returnDate = returnField.getText();
				DB.borrowBooks(customer, selected, borrowDate, returnDate);
				bookWorkSpace.clearAndUpdateTable();
				borrow.getScene().getWindow().hide();
			} catch (SQLException e1) {
				String header = "Borrow book failed!";
				Alert fail = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.OK);
				fail.setTitle("Borrow Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
