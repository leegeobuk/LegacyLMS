package ok_buttons;

import java.sql.SQLException;
import buttons.GeneralButton;
import db.DB;
import dialogs.CreateAccountDialog;
import dialogs.LogInDialog;
import javafx.scene.control.Button;

public class LoadLMSOK implements GeneralButton {
	private Button load;
	private String dbName;

	public LoadLMSOK(Button load) {
		this.load = load;
	}

	public LoadLMSOK(Button load, String dbName) {
		this.load = load;
		this.dbName = dbName;
	}
	
	public Button getLoadButton() {
		return load;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void loadLMS() {
		System.out.println("Load LMS load button pressed!");
		if (dbName != null) {
			String query = "USE " + dbName + ";";
			try {
				DB.executeQuery(query);
				System.out.println("Load successful!");
				load.getScene().getWindow().hide();
				LogInDialog login = new LogInDialog("User ID", "Password");
				login.showDialog();
			} catch (SQLException e1) {
				System.out.println("SQL Exception while loading LMS");
				e1.printStackTrace();
			}
		}
	}
	
	public void createAccount() {
		System.out.println("Load LMS load button for create account pressed!");
		load.getScene().getWindow().hide();
		CreateAccountDialog createAccountDialog = new CreateAccountDialog(this, new DB(dbName));
		createAccountDialog.showDialog();
	}
	
	@Override
	public void buttonPressed() {
		load.setOnAction(e -> {
			loadLMS();
		});
	}
	
	public void buttonPressedInCreateAccount() {
		load.setOnAction(e -> {
			createAccount();
		});
	}
}
