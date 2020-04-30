package ok_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import buttons.GeneralButton;
import db.DB;
import db.Librarian;
import db.Table;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.LibrarianWorkSpace;

public class EditLibrarianOK extends Button implements GeneralButton {
	private LibrarianWorkSpace librarianWorkSpace;
	private Button edit;
	private TextField idField;
	private TextField nameField;
	private TextField contactField;
	private Alert fail;
	
	public EditLibrarianOK(LibrarianWorkSpace librarianWorkSpace, Button ok, TextField idField, 
					  	   TextField nameField, TextField contactField) {
		this.librarianWorkSpace = librarianWorkSpace;
		this.edit = ok;
		this.edit.setDefaultButton(true);
		this.idField = idField;
		this.nameField = nameField;
		this.contactField = contactField;
	}

	@Override
	public void buttonPressed() {
		edit.setOnAction(e -> {
			System.out.println("Edit librarian pressed!");
			try {
				Librarian l1 = librarianWorkSpace.getTable().getSelectionModel().getSelectedItem();
				Librarian l2 = new Librarian(idField.getText(), l1.getPw(), nameField.getText(), contactField.getText());
				DB.editFromTable(Table.LIBRARIANS, l1, l2);
				librarianWorkSpace.clearAndUpdateTable();
				edit.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while editing librarians.");
				String header = "Edit librarian failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Edit Librarian Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while editing librarians.");
				String header = "Edit librarian failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Edit Librarian Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
