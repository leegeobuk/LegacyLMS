package ok_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import buttons.GeneralButton;
import db.Book;
import db.BookStatus;
import db.DB;
import db.Table;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.BookWorkSpace;

public class EditBookOK extends Button implements GeneralButton {
	private BookWorkSpace bookWorkSpace;
	private Button edit;
	private TextField bidField;
	private TextField titleField;
	private TextField authorField;
	private TextField stockField;
	private Alert fail;
	
	public EditBookOK(BookWorkSpace bookWorkSpace, Button ok, TextField bidField, 
					  TextField titleField, TextField authorField, TextField stockField) {
		this.bookWorkSpace = bookWorkSpace;
		this.edit = ok;
		this.edit.setDefaultButton(true);
		this.bidField = bidField;
		this.titleField = titleField;
		this.authorField = authorField;
		this.stockField = stockField;
	}

	@Override
	public void buttonPressed() {
		edit.setOnAction(e -> {
			System.out.println("Edit book pressed!");
			try {
				Book b1 = bookWorkSpace.getTable().getSelectionModel().getSelectedItem();
				int stock = Integer.parseInt(stockField.getText());
				BookStatus status = stock > 0 ? BookStatus.IN : BookStatus.OUT;
				Book b2 = new Book(bidField.getText(), titleField.getText(), authorField.getText(), status, stock);
				DB.editFromTable(Table.BOOKS, b1, b2);
				bookWorkSpace.clearAndUpdateTable();
				edit.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while editing books.");
				String header = "Edit book failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Edit Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while editing books.");
				String header = "Edit book failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Edit Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
