package ok_buttons;

import java.sql.SQLException;

import buttons.GeneralButton;
import db.AccountType;
import db.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import ui.Menu;

public class LogInOK implements GeneralButton {
	private TextField idField;
	private TextField pwField;
	private Button logInOK;
	
	public LogInOK(TextField idField, TextField pwField, Button button) {
		this.idField = idField;
		this.pwField = pwField;
		logInOK = button;
	}
	
	public Button getLogInButton() {
		return logInOK;
	}
	
	@Override
	public void buttonPressed() {
		logInOK.setOnAction(e -> {
			System.out.println("Log in ok pressed");
			try {
				AccountType accountType = DB.isValidIdPw(idField.getText(), pwField.getText());
				if (accountType != AccountType.FALSE) {
					logInOK.getScene().getWindow().hide();
					System.out.println("Log in successful!");
					Menu menu = new Menu(accountType);
					menu.showMenu();
				}
				else {
					Alert fail = new Alert(AlertType.ERROR, "Wrong id or password", ButtonType.CLOSE);
					fail.setTitle("Log in Error");
					fail.setHeaderText("Log in failed.");
					fail.showAndWait();
				}
			} catch (SQLException e1) {
				System.out.println("SQL Exception while verifying id and pw!");
				e1.printStackTrace();
			}
		});
	}

}
