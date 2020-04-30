package ok_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import buttons.GeneralButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.LibrarianWorkSpace;
import ui.Mode;

public class SearchLibrarianOK extends Button implements GeneralButton {
	private Button search;
	private LibrarianWorkSpace librarianWorkSpace;
	private TextField idField;
	private TextField nameField;
	private TextField contactField;
	private Alert fail;
	
	public SearchLibrarianOK(LibrarianWorkSpace librarianWorkSpace, Button ok,
							 TextField idField, TextField nameField,
							 TextField contactField) {
		this.librarianWorkSpace = librarianWorkSpace;
		this.search = ok;
		this.search.setDefaultButton(true);
		this.idField = idField;
		this.nameField = nameField;
		this.contactField = contactField;
	}
	
	@Override
	public void buttonPressed() {
		search.setOnAction(e -> {
			System.out.println("Search librarian pressed!");
			HashMap<Integer, String> searchCriteria = new HashMap<>();
			try {
				if (!idField.getText().equals(""))
					searchCriteria.put(1, idField.getText());
				if (!nameField.getText().equals(""))
					searchCriteria.put(2, nameField.getText());
				if (!contactField.getText().equals(""))
					searchCriteria.put(3, contactField.getText());
				
				librarianWorkSpace.setSearchCriteria(searchCriteria);
				librarianWorkSpace.setMode(Mode.SEARCH);
				librarianWorkSpace.clearAndUpdateTable();
				search.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while searching librarian.");
				String header = "Search librarian failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Search Librarian Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while searching librarian.");
				String header = "Search librarian failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Search Librarian Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
