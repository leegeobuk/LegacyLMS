package ok_buttons;

import buttons.GeneralButton;
import db.DB;
import dialogs.CreateAdminDialog;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateNewLMSOK implements GeneralButton {
	private Button add;
	private TextField nameField;

	public CreateNewLMSOK(Button add, TextField namefield) {
		this.add = add;
		this.nameField = namefield;
	}

	@Override
	public void buttonPressed() {
		add.setOnAction(e -> {
			String dbName = nameField.getText();
			DB db = new DB(dbName + "_lms");
			add.getScene().getWindow().hide();
			CreateAdminDialog create = new CreateAdminDialog(db);
			create.showDialog();
		});
	}

}
