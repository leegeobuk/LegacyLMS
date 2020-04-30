package dialogs;

import buttons.Cancel;
import db.DB;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ok_buttons.CreateAccountOK;
import ok_buttons.LoadLMSOK;

public class CreateAccountDialog extends Stage implements GeneralDialog {
	private BorderPane bp;
	private Label headerLabel;
	private Label idLabel;  
	private Label pwLabel;
	private Label pw2Label;
	private Label nameLabel;
	private Label contactLabel;
	private Label warningLabel;
	private TextField idField;  
	private PasswordField pwField;
	private PasswordField pw2Field;
	private TextField nameField;
	private TextField contactField;
	private Button create;
	private Button cancel;
	private CreateAccountOK createAccountOK;
	private Cancel createNewAccountCancel;
	private boolean isIdFilled;
	private boolean isPwFilled;
	private boolean isPw2Filled;
	private boolean isNameFilled;
	private boolean isContactFilled;

	{
		bp = new BorderPane();
		headerLabel = new Label("Create Account");
		warningLabel = new Label("All fields must be filled!");
		idLabel = new Label("User ID:");
		pwLabel = new Label("Password:");
		pw2Label = new Label("Confirm Password:");
		nameLabel = new Label("Name:");
		contactLabel = new Label("Contact:");
		idField = new TextField();
		pwField = new PasswordField();
		pw2Field = new PasswordField();
		nameField = new TextField();
		contactField = new TextField();
		create = new Button("Create");
		create.setMinWidth(45);
		create.setDefaultButton(true);
		create.setDisable(true);
		cancel = new Button("Cancel");
		cancel.setMinWidth(45);
	}

	public CreateAccountDialog(LoadLMSOK loadLMSOK, DB db) {
		createAccountOK = new CreateAccountOK(this.create, idField, pwField, nameField, contactField, db);
		createNewAccountCancel = new Cancel(cancel);
	}

	@Override
	public void initDialog() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		warningLabel.setTextFill(Color.RED);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.TOP_CENTER);
		GridPane gp = new GridPane();
		gp.setMinHeight(52);
		gp.addRow(0, idLabel);
		gp.addRow(1, idField);
		gp.addRow(2, pwLabel);
		gp.addRow(3, pwField);
		gp.addRow(4, pw2Label);
		gp.addRow(5, pw2Field);
		gp.addRow(6, nameLabel);
		gp.addRow(7, nameField);
		gp.addRow(8, contactLabel);
		gp.addRow(9, contactField);
		gp.add(warningLabel, 0, 10);
		gp.setHgap(2);
		gp.setVgap(5);
		gp.setPadding(new Insets(0, 26, 5, 26));
		bp.setCenter(gp);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
		hb.setPadding(new Insets(5));
		hb.getChildren().addAll(create, cancel);
		bp.setBottom(hb);
	}

	@Override
	public void initButtons() {
		createAccountOK.buttonPressed();
		createNewAccountCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		idField.setOnKeyReleased(e -> {
			if (idField.getText().length() > 0)
				isIdFilled = true;
			else
				isIdFilled = false;
			if (!(isIdFilled && isPwFilled && isPw2Filled && isNameFilled && isContactFilled)) {
				create.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else if (!isPwSame()) {
				create.setDisable(true);
				warningLabel.setText("Passwords should match!");
			}
			else {
				warningLabel.setText("");
				create.setDisable(false);
			}
		});
		pwField.setOnKeyReleased(e -> {
			if (pwField.getText().length() > 0)
				isPwFilled = true;
			else
				isPwFilled = false;
			if (!(isIdFilled && isPwFilled && isPw2Filled && isNameFilled && isContactFilled)) {
				create.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else if (!isPwSame()) {
				create.setDisable(true);
				warningLabel.setText("Passwords should match!");
			}
			else {
				warningLabel.setText("");
				create.setDisable(false);
			}
		});
		pw2Field.setOnKeyReleased(e -> {
			if (pw2Field.getText().length() > 0)
				isPw2Filled = true;
			else
				isPw2Filled = false;
			if (!(isIdFilled && isPwFilled && isPw2Filled && isNameFilled && isContactFilled)) {
				create.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else if (!isPwSame()) {
				create.setDisable(true);
				warningLabel.setText("Passwords should match!");
			}
			else {
				warningLabel.setText("");
				create.setDisable(false);
			}
		});
		nameField.setOnKeyReleased(e -> {
			if (nameField.getText().length() > 0)
				isNameFilled = true;
			else
				isNameFilled = false;
			if (!(isIdFilled && isPwFilled && isPw2Filled && isNameFilled && isContactFilled)) {
				create.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else if (!isPwSame()) {
				create.setDisable(true);
				warningLabel.setText("Passwords should match!");
			}
			else {
				warningLabel.setText("");
				create.setDisable(false);
			}
		});
		contactField.setOnKeyReleased(e -> {
			if (contactField.getText().length() > 0)
				isContactFilled = true;
			else
				isContactFilled = false;
			if (!(isIdFilled && isPwFilled && isPw2Filled && isNameFilled && isContactFilled)) {
				create.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else if (!isPwSame()) {
				create.setDisable(true);
				warningLabel.setText("Passwords should match!");
			}
			else {
				warningLabel.setText("");
				create.setDisable(false);
			}
		});
	}

	private boolean isPwSame() {
		return pwField.getText().equals(pw2Field.getText());
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		Scene scene = new Scene(bp);
		setTitle("Account");
		setScene(scene);
		System.out.println("Create account dialog!");
		showAndWait();
	}
}
