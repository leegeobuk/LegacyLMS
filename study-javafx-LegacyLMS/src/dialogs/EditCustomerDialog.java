package dialogs;

import buttons.Cancel;
import db.Customer;
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
import ok_buttons.EditCustomerOK;
import ui.CustomerWorkSpace;

public class EditCustomerDialog extends Stage implements GeneralDialog {
	private BorderPane bp = new BorderPane();
	private Label headerLabel;
	private Label idLabel;
	private Label nameLabel;
	private Label contactLabel;
	private Label warningLabel;
	private TextField idField = new TextField();
	private TextField nameField = new TextField();
	private TextField contactField = new TextField();
	private Button edit = new Button("Edit");
	private Button cancel = new Button("Cancel");
	private EditCustomerOK editCustomerOK;
	private Cancel editCustomerCancel;
	private CustomerWorkSpace customerWorkSpace;
	private Customer selected;
	private boolean isIdFilled = true;
	private boolean isNameFilled = true;
	private boolean isContactFilled = true;
	
	{
		this.headerLabel = new Label("Edit Customer");
		this.idLabel = new Label("Customer ID:");
		this.nameLabel = new Label("Name:");
		this.contactLabel = new Label("Contact:");
		warningLabel = new Label("");
	}
	
	public EditCustomerDialog(CustomerWorkSpace customerWorkSpace) {
		this.customerWorkSpace = customerWorkSpace;
		editCustomerOK = new EditCustomerOK(customerWorkSpace, edit, idField, nameField, contactField);
		editCustomerCancel = new Cancel(cancel);
	}
	
	@Override
	public void initDialog() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.TOP_CENTER);
		
		idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		contactLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		warningLabel.setTextFill(Color.RED);
		
		selected = customerWorkSpace.getTable().getSelectionModel().getSelectedItem();
		idField.setText(""+selected.getCid());
		nameField.setText(selected.getCname());
		contactField.setText(selected.getContact());
		
		GridPane gp = new GridPane();
		gp.addRow(0, idLabel, idField);
		gp.addRow(1, nameLabel, nameField);
		gp.addRow(2, contactLabel,contactField);
		gp.add(warningLabel, 1, 3);
		gp.setHgap(5);
		gp.setVgap(5);
		gp.setPadding(new Insets(6));
		bp.setCenter(gp);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
    	hb.getChildren().addAll(edit, cancel);
    	hb.setPadding(new Insets(0, 0, 3, 0));
    	bp.setBottom(hb);
	}

	@Override
	public void initButtons() {
		editCustomerOK.buttonPressed();
		editCustomerCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		idField.setOnKeyReleased(e -> {
			if (idField.getText().length() > 0)
				isIdFilled = true;
			else
				isIdFilled = false;
			if (!(isIdFilled && isNameFilled && isContactFilled)) {
				edit.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				edit.setDisable(false);
			}
		});
		nameField.setOnKeyReleased(e -> {
			if (nameField.getText().length() > 0)
				isNameFilled = true;
			else
				isNameFilled = false;
			if (!(isIdFilled && isNameFilled && isContactFilled)) {
				edit.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				edit.setDisable(false);
			}
		});
		contactField.setOnKeyReleased(e -> {
			if (contactField.getText().length() > 0)
				isContactFilled = true;
			else
				isContactFilled = false;
			if (!(isIdFilled && isNameFilled && isContactFilled)) {
				edit.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				edit.setDisable(false);
			}
		});
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		Scene scene = new Scene(bp);
		setTitle("Edit Customer");
		setScene(scene);
		showAndWait();
	}
}
