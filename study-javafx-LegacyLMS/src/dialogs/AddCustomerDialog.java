package dialogs;

import buttons.Cancel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ok_buttons.AddCustomerOK;
import ui.CustomerWorkSpace;

public class AddCustomerDialog extends Stage implements GeneralDialog {
	private BorderPane bp = new BorderPane();
	private Label headerLabel;
	private Label idLabel;
	private Label nameLabel;
	private Label phoneLabel;
	private Label warningLabel;
	private TextField idField = new TextField();
	private TextField nameField = new TextField();
	private TextField contactField = new TextField();
	private Button add = new Button("Add");
	private Button cancel = new Button("Cancel");
	private AddCustomerOK addCustomerOK;
	private Cancel addCustomerCancel;
	private boolean isIdFilled;
	private boolean isTitleFilled;
	private boolean isAuthorFilled;
	private boolean isStockFilled = true;
	
	public AddCustomerDialog(CustomerWorkSpace customerWorkSpace) {
		this.headerLabel = new Label("Add Customer");
		this.idLabel = new Label("Customer ID:");
		this.nameLabel = new Label("Name:");
		this.phoneLabel = new Label("Phone number:");
		warningLabel = new Label("All fields must be filled!");
		add.setDisable(true);
		addCustomerOK = new AddCustomerOK(customerWorkSpace, add, idField, nameField, contactField);
		addCustomerCancel = new Cancel(cancel);
	}
	
	@Override
	public void initDialog() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.TOP_CENTER);
		
		idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		phoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		warningLabel.setTextFill(Color.RED);
		
		GridPane gp = new GridPane();
		gp.addRow(0, idLabel, idField);
		gp.addRow(1, nameLabel, nameField);
		gp.addRow(2, phoneLabel,contactField);
		gp.add(warningLabel, 1, 3);
		gp.setHgap(5);
		gp.setVgap(5);
		gp.setPadding(new Insets(10));
		bp.setCenter(gp);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
    	hb.getChildren().addAll(add, cancel);
    	hb.setPadding(new Insets(0, 0, 3, 0));
    	bp.setBottom(hb);
	}

	@Override
	public void initButtons() {
		addCustomerOK.buttonPressed();
		addCustomerCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		idField.setOnKeyReleased(e -> {
			if (idField.getText().length() > 0)
				isIdFilled = true;
			else
				isIdFilled = false;
			if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
				add.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				add.setDisable(false);
			}
		});
		nameField.setOnKeyReleased(e -> {
			if (nameField.getText().length() > 0)
				isTitleFilled = true;
			else
				isTitleFilled = false;
			if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
				add.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				add.setDisable(false);
			}
		});
		contactField.setOnKeyReleased(e -> {
			if (contactField.getText().length() > 0)
				isAuthorFilled = true;
			else
				isAuthorFilled = false;
			if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
				add.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				add.setDisable(false);
			}
		});
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		Scene scene = new Scene(bp);
		setTitle("Add Customer");
		setScene(scene);
		showAndWait();
	}
}
