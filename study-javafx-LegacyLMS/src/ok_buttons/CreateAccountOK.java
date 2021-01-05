package ok_buttons;

import java.sql.SQLException;
import buttons.GeneralButton;
import db.AccountType;
import db.DB;
import db.Librarian;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.Menu;

public class CreateAccountOK extends Button implements GeneralButton {
	private Button create;
	private TextField idField;
	private PasswordField pwField;
	private TextField nameField;
	private TextField contactField;
	private DB db;
	
	public CreateAccountOK(Button button, TextField idField, PasswordField pwField,
						   TextField nameField, TextField contactField, DB db) {
		create = button;
		this.idField = idField;
		this.pwField = pwField;
		this.nameField = nameField;
		this.contactField = contactField;
		this.db = db;
	}

	@Override
	public void buttonPressed() {
		create.setOnAction(e -> {
			System.out.println("Create and load button pressed!");
			String id = idField.getText();
			String pw = pwField.getText();
			String lname = nameField.getText();
			String contact = contactField.getText();
			
			try {
				String query = "USE " + db.getDatabaseName() + ";";
				DB.executeQuery(query);
				Librarian librarian = new Librarian(id, pw, lname, contact);
				DB.insertLibrarian(librarian);
				String header = "Account is successfully created!";
				Alert success = new Alert(AlertType.INFORMATION, "Jass!", ButtonType.OK);
				success.setTitle("Create Account");
				success.setHeaderText(header);
				create.getScene().getWindow().hide();
				success.showAndWait();
				Menu menu = new Menu(AccountType.LIBRARIAN);
				menu.showMenu();
			}
			catch (SQLException e1) {
				Alert fail = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.CLOSE);
				fail.setTitle("Create Account Error");
				fail.setHeaderText("Account creation failed.");
				fail.showAndWait();
				e1.printStackTrace();
			}
		});
	}
}
