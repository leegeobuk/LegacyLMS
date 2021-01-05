package ok_buttons;

import java.sql.SQLException;
import buttons.GeneralButton;
import db.AccountType;
import db.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.Menu;

public class CreateAdminOK implements GeneralButton {
	private Button create;
	private TextField idField;
	private PasswordField pwField;
	private DB db;
	
	public CreateAdminOK(Button button, TextField idField, PasswordField pwField, DB db) {
		create = button;
		this.idField = idField;
		this.pwField = pwField;
		this.db = db;
	}

	@Override
	public void buttonPressed() {
		create.setOnAction(e -> {
			System.out.println("Create admin ok pressed!");
			//Add account on database
			String id = idField.getText();
			String pw = pwField.getText();
			
			try {
				db.createDatabase();
				DB.insertAdmin(id, pw);
				String header = "Account is successfully created!";
				Alert success = new Alert(AlertType.INFORMATION, "Jass!", ButtonType.OK);
				success.setTitle("Create Account");
				success.setHeaderText(header);
				create.getScene().getWindow().hide();
				success.showAndWait();
				System.out.println("Main scene closed!");
				Menu menu = new Menu(AccountType.ADMIN);
				menu.showMenu();
			}
			catch (ClassNotFoundException | SQLException e1) {
				Alert fail = new Alert(AlertType.ERROR, "Try again!", ButtonType.CLOSE);
				fail.setTitle("Create Admint Error");
				fail.setHeaderText("Admin creation failed.");
				fail.showAndWait();
				e1.printStackTrace();
			}
		});
	}
}
