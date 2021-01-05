package dialogs;

import buttons.Cancel;
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
import ok_buttons.LogInOK;

public class LogInDialog extends Stage implements GeneralDialog {
	private BorderPane bp;
	private Label headerLabel;
	private Label idLabel;  
	private Label pwLabel;
	private Label warningLabel;
	private TextField idField;  
	private PasswordField pwField;
	private Button logIn;
	private Button cancel;
	private boolean isIdFilled;
	private boolean isPwFilled;
	private LogInOK logInOK;
	private Cancel logInCancel;

	public LogInDialog(String label_1, String label_2) {
		bp = new BorderPane();
		headerLabel = new Label("Log in");
		idLabel = new Label(label_1);
		pwLabel = new Label(label_2);
		warningLabel = new Label("All fields must be filled!");
		idField = new TextField();
		pwField = new PasswordField();
		logIn = new Button("Log in");
		logIn.setMinWidth(45);
		logIn.setDisable(true);
		logIn.setDefaultButton(true);
		cancel = new Button("Cancel");
		cancel.setMinWidth(45);
		logInOK = new LogInOK(idField, pwField, logIn);
		logInCancel = new Cancel(cancel);
	}

	@Override
	public void initDialog() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.TOP_CENTER);
		
		idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		pwLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		warningLabel.setTextFill(Color.RED);

		GridPane gp = new GridPane();
		gp.addRow(0, idLabel, idField);
		gp.addRow(1, pwLabel, pwField);
		gp.add(warningLabel, 1, 2);
		gp.setHgap(10);
		gp.setVgap(5);
		gp.setPadding(new Insets(5));
		bp.setCenter(gp);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.setPadding(new Insets(0, 0, 3, 0));
		hb.getChildren().addAll(logIn, cancel);
		bp.setBottom(hb);
	}

	public void initButtons() {
		logInOK.buttonPressed();
		logInCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		idField.setOnKeyReleased(e -> {
			if (idField.getText().length() > 0)
				isIdFilled = true;
			else
				isIdFilled = false;
			if (!(isIdFilled && isPwFilled)) {
				logIn.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				logIn.setDisable(false);
				warningLabel.setText("");
			}
		});
		pwField.setOnKeyReleased(e -> {
			if (pwField.getText().length() > 0)
				isPwFilled = true;
			else
				isPwFilled = false;
			if (!(isIdFilled && isPwFilled)) {
				logIn.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				logIn.setDisable(false);
				warningLabel.setText("");
			}
		});
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		Scene scene = new Scene(bp);
		this.setTitle("Log In");
		this.setScene(scene);
		this.setResizable(false);
		showAndWait();
	}    
}
