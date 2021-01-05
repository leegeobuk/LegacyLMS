package icon_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

import buttons.GeneralButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.CustomerWorkSpace;
import ui.Mode;

public class SearchCustomerOK extends Button implements GeneralButton {
	private Button search;
	private CustomerWorkSpace customerWorkSpace;
	private TextField idField;
	private TextField nameField;
	private TextField contactField;
	private Alert fail;
	
	public SearchCustomerOK(CustomerWorkSpace customerWorkSpace, Button ok,
							 TextField idField, TextField nameField,
							 TextField contactField) {
		this.customerWorkSpace = customerWorkSpace;
		this.search = ok;
		this.search.setDefaultButton(true);
		this.idField = idField;
		this.nameField = nameField;
		this.contactField = contactField;
	}
	
	@Override
	public void buttonPressed() {
		search.setOnAction(e -> {
			System.out.println("Search customer pressed!");
			HashMap<Integer, String> searchCriteria = new HashMap<>();
			try {
				if (!idField.getText().equals(""))
					searchCriteria.put(1, idField.getText());
				if (!nameField.getText().equals(""))
					searchCriteria.put(2, nameField.getText());
				if (!contactField.getText().equals(""))
					searchCriteria.put(3, contactField.getText());
				
				customerWorkSpace.setSearchCriteria(searchCriteria);
				customerWorkSpace.setMode(Mode.SEARCH);
				customerWorkSpace.clearAndUpdateTable();
				search.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while searching customer.");
				String header = "Search customer failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Search Customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while searching customer.");
				String header = "Search customer failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Search Customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
