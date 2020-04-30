package ok_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import buttons.GeneralButton;
import db.Customer;
import db.DB;
import db.Table;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.CustomerWorkSpace;

public class EditCustomerOK extends Button implements GeneralButton {
	private CustomerWorkSpace customerWorkSpace;
	private Button edit;
	private TextField idField;
	private TextField nameField;
	private TextField contactField;
	private Alert fail;
	
	public EditCustomerOK(CustomerWorkSpace customerWorkSpace, Button ok, TextField idField, 
					  	   TextField nameField, TextField contactField) {
		this.customerWorkSpace = customerWorkSpace;
		this.edit = ok;
		this.edit.setDefaultButton(true);
		this.idField = idField;
		this.nameField = nameField;
		this.contactField = contactField;
	}

	@Override
	public void buttonPressed() {
		edit.setOnAction(e -> {
			System.out.println("Edit customer pressed!");
			try {
				Customer c1 = customerWorkSpace.getTable().getSelectionModel().getSelectedItem();
				int cid = Integer.parseInt(idField.getText());
				Customer c2 = new Customer(cid, nameField.getText(), contactField.getText());
				DB.editFromTable(Table.CUSTOMERS, c1, c2);
				customerWorkSpace.clearAndUpdateTable();
				edit.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while editing customer.");
				String header = "Edit customer failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Edit customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while editing customer.");
				String header = "Edit customer failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Edit Customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
