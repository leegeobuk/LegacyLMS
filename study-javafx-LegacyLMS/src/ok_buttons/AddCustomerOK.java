package ok_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import buttons.GeneralButton;
import db.Customer;
import db.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.CustomerWorkSpace;

public class AddCustomerOK extends Button implements GeneralButton {
	private CustomerWorkSpace customerWorkSpace;
	private Button add;
	private TextField idField;
	private TextField nameField;
	private TextField contactField;
	private Alert fail;
	
	public AddCustomerOK(CustomerWorkSpace customerWorkSpace, Button ok,
						 TextField idField, TextField nameField, TextField contactField) {
		this.customerWorkSpace = customerWorkSpace;
		this.add = ok;
		this.add.setDefaultButton(true);
		this.idField = idField;
		this.nameField = nameField;
		this.contactField = contactField;
	}
	
	@Override
	public void buttonPressed() {
		add.setOnAction(e -> {
			System.out.println("Add customer pressed!");
			try {
				Customer customer = new Customer(Integer.parseInt(idField.getText()), nameField.getText(), contactField.getText());
				DB.insertCustomer(customer);
				customerWorkSpace.clearAndUpdateTable();
				add.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while adding customers.");
				String header = "Add customer failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Add Customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while adding customers.");
				String header = "Add customer failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Add Customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
