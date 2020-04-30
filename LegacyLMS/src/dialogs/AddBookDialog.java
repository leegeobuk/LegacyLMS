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
import ok_buttons.AddBookOK;
import ui.BookWorkSpace;

public class AddBookDialog extends Stage implements GeneralDialog {
	private BorderPane bp = new BorderPane();
	private Label headerLabel;
	private Label idLabel;
	private Label titleLabel;
	private Label authorLabel;
	private Label stockLabel;
	private Label warningLabel;
	private TextField idField = new TextField();
	private TextField titleField = new TextField();
	private TextField authorField = new TextField();
	private TextField stockField = new TextField("1");
	private Button add = new Button("Add");
	private Button cancel = new Button("Cancel");
	private AddBookOK addBookOK;
	private Cancel addBookCancel;
	private boolean isIdFilled;
	private boolean isTitleFilled;
	private boolean isAuthorFilled;
	private boolean isStockFilled = true;
	
	public AddBookDialog(BookWorkSpace bookWorkSpace) {
		this.headerLabel = new Label("Add Book");
		this.idLabel = new Label("Book ID:");
		this.titleLabel = new Label("Title:");
		this.authorLabel = new Label("Author:");
		this.stockLabel = new Label("Stock:");
		warningLabel = new Label("All fields must be filled!");
		add.setDisable(true);
		addBookOK = new AddBookOK(bookWorkSpace, add, idField, titleField, authorField, stockField);
		addBookCancel = new Cancel(cancel);
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
		stockLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		warningLabel.setTextFill(Color.RED);
		
		GridPane gp = new GridPane();
		gp.addRow(0, idLabel, idField);
		gp.addRow(1, titleLabel, titleField);
		gp.addRow(2, authorLabel,authorField);
		gp.addRow(3, stockLabel, stockField);
		gp.add(warningLabel, 1, 4);
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
		addBookOK.buttonPressed();
		addBookCancel.buttonPressed();
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
		titleField.setOnKeyReleased(e -> {
			if (titleField.getText().length() > 0)
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
		authorField.setOnKeyReleased(e -> {
			if (authorField.getText().length() > 0)
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
		stockField.setOnKeyReleased(e -> {
			try {
				if (stockField.getText().length() > 0)
					isStockFilled = true;
				else
					isStockFilled = false;
				if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
					add.setDisable(true);
					warningLabel.setText("All fields must be filled!");
				}
				else {
					warningLabel.setText("");
					add.setDisable(false);
				}
				int quantity;
				if (isStockFilled) {
					quantity = Integer.parseInt(stockField.getText());
					if (quantity < 0) {
						warningLabel.setText("Can't enter a negative number!");
						add.setDisable(true);
					}
				}
			} catch (NumberFormatException nfe) {
				warningLabel.setText("Stock field must be a number!");
				add.setDisable(true);
			}
		});
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		Scene scene = new Scene(bp);
		setTitle("Add Book");
		setScene(scene);
		showAndWait();
	}
}
