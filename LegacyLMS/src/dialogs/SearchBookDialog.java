package dialogs;

import buttons.Cancel;
import db.BookStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ok_buttons.SearchBookOK;
import ui.BookWorkSpace;

public class SearchBookDialog extends Stage implements GeneralDialog {
	private BorderPane bp = new BorderPane();
	private Label headerLabel;
	private Label idLabel;
	private Label titleLabel;
	private Label authorLabel;
	private Label statusLabel;
	private Label warningLabel;
	private TextField idField = new TextField();
	private TextField titleField = new TextField();
	private TextField authorField = new TextField();
	private ChoiceBox<BookStatus> statusBox;
	private Button search = new Button("Search");
	private Button cancel = new Button("Cancel");
	private SearchBookOK searchBookOK;
	private Cancel searchBookCancel;
	private boolean isIdFilled;
	private boolean isTitleFilled;
	private boolean isAuthorFilled;
	private boolean isStatusFilled;
	
	public SearchBookDialog(BookWorkSpace bookWorkSpace) {
		this.headerLabel = new Label("Search Book");
		this.idLabel = new Label("Book ID:");
		this.titleLabel = new Label("Title:");
		this.authorLabel = new Label("Author:");
		this.statusLabel = new Label("Status:");
		statusBox = new ChoiceBox<>();
		statusBox.getItems().addAll(BookStatus.IN, BookStatus.OUT);
		warningLabel = new Label("Enter at least one field!");
		search.setDisable(true);
		searchBookOK = new SearchBookOK(bookWorkSpace, search, idField, titleField, authorField, statusBox);
		searchBookCancel = new Cancel(cancel);
	}
	
	@Override
	public void initDialog() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.TOP_CENTER);
		
		idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		authorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		warningLabel.setTextFill(Color.RED);
		
		GridPane gp = new GridPane();
		gp.addRow(0, idLabel, idField);
		gp.addRow(1, titleLabel, titleField);
		gp.addRow(2, authorLabel,authorField);
		gp.addRow(3, statusLabel, statusBox);
		gp.add(warningLabel, 1, 4);
		gp.setHgap(5);
		gp.setVgap(5);
		gp.setPadding(new Insets(15));
		bp.setCenter(gp);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
    	hb.getChildren().addAll(search, cancel);
    	hb.setPadding(new Insets(0, 0, 3, 0));
    	bp.setBottom(hb);
	}

	@Override
	public void initButtons() {
		searchBookOK.buttonPressed();
		searchBookCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		idField.setOnKeyReleased(e -> {
			if (idField.getText().length() > 0)
				isIdFilled = true;
			else
				isIdFilled = false;
			if (!(isIdFilled || isTitleFilled || isAuthorFilled || isStatusFilled)) {
				search.setDisable(true);
				warningLabel.setText("Fill at least one field!");
			}
			else {
				warningLabel.setText("");
				search.setDisable(false);
			}
		});
		titleField.setOnKeyReleased(e -> {
			if (titleField.getText().length() > 0)
				isTitleFilled = true;
			else
				isTitleFilled = false;
			if (!(isIdFilled || isTitleFilled || isAuthorFilled || isStatusFilled)) {
				search.setDisable(true);
				warningLabel.setText("Fill at least one field!");
			}
			else {
				warningLabel.setText("");
				search.setDisable(false);
			}
		});
		authorField.setOnKeyReleased(e -> {
			if (authorField.getText().length() > 0)
				isAuthorFilled = true;
			else
				isAuthorFilled = false;
			if (!(isIdFilled || isTitleFilled || isAuthorFilled || isStatusFilled)) {
				search.setDisable(true);
				warningLabel.setText("Fill at least one field!");
			}
			else {
				warningLabel.setText("");
				search.setDisable(false);
			}
		});
		statusBox.setOnAction(e -> {
			if (statusBox.getValue() != null)
				isStatusFilled = true;
			else
				isStatusFilled = false;
			if (!(isIdFilled || isTitleFilled || isAuthorFilled || isStatusFilled)) {
				search.setDisable(true);
				warningLabel.setText("Fill at least one field!");
			}
			else {
				warningLabel.setText("");
				search.setDisable(false);
			}
		});
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		Scene scene = new Scene(bp);
		setTitle("Search Book");
		setScene(scene);
		showAndWait();
	}
}
