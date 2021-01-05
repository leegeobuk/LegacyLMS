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
import ok_buttons.CreateNewLMSOK;

public class CreateNewLMSDialog extends Stage implements GeneralDialog {
	private BorderPane bp = new BorderPane();
	private Label headerLabel;
	private Label nameLabel;
	private Label warningLabel;
	private TextField nameField = new TextField();
	private Button add = new Button("Add");
	private Button cancel = new Button("Cancel");
	CreateNewLMSOK addLMS;
	Cancel createNewLMSCancel;

	public CreateNewLMSDialog(String header, String name) {
		headerLabel = new Label(header);
		nameLabel = new Label(name);
		warningLabel = new Label("Name field should be filled!");
		add.setDefaultButton(true);
		add.setDisable(true);
		addLMS = new CreateNewLMSOK(add, nameField);
		createNewLMSCancel = new Cancel(cancel);
	}
	
	@Override
	public void initDialog() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		warningLabel.setTextFill(Color.RED);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.TOP_CENTER);
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		GridPane gp = new GridPane();
		gp.addRow(0, nameLabel, nameField);
		gp.add(warningLabel, 1, 1);
		gp.setHgap(10);
		gp.setVgap(5);
		gp.setPadding(new Insets(5));
		bp.setCenter(gp);
		BorderPane.setAlignment(gp, Pos.CENTER);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
    	hb.getChildren().addAll(add, cancel);
    	hb.setPadding(new Insets(0, 0, 5, 0));
		bp.setBottom(hb);
		BorderPane.setAlignment(hb, Pos.BOTTOM_CENTER);
	}
	
	@Override
	public void initButtons() {
		addLMS.buttonPressed();
		createNewLMSCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		nameField.setOnKeyReleased(e -> {
			if (nameField.getText().length() == 0) {
				warningLabel.setText("Name field should be filled!");
				add.setDisable(true);
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
		setTitle("Create New LMS");
		setScene(scene);
		show();
	}

}