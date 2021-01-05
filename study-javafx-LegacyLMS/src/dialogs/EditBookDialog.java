package dialogs;

import buttons.Cancel;
import db.Book;
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
import ok_buttons.EditBookOK;
import ui.BookWorkSpace;

public class EditBookDialog extends Stage implements GeneralDialog {
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
	private TextField stockField = new TextField();
	private Button edit = new Button("Edit");
	private Button cancel = new Button("Cancel");
	private EditBookOK editBookOK;
	private Cancel editBookCancel;
	private BookWorkSpace bookWorkSpace;
	private Book book;
	private boolean isIdFilled = true;
	private boolean isTitleFilled = true;
	private boolean isAuthorFilled = true;
	private boolean isStockFilled = true;
	
	{
		this.headerLabel = new Label("Edit Book");
		this.idLabel = new Label("Book ID:");
		this.titleLabel = new Label("Title:");
		this.authorLabel = new Label("Author:");
		this.stockLabel = new Label("Stock:");
		warningLabel = new Label("");
	}
	
	public EditBookDialog(BookWorkSpace bookWorkSpace) {
		this.bookWorkSpace = bookWorkSpace;
		editBookOK = new EditBookOK(bookWorkSpace, edit, idField, titleField, authorField, stockField);
		editBookCancel = new Cancel(cancel);
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
		
		book = bookWorkSpace.getTable().getSelectionModel().getSelectedItem();
		idField.setText(book.getBid());
		titleField.setText(book.getTitle());
		authorField.setText(book.getAuthor());
		stockField.setText("" + book.getStock());
		
		GridPane gp = new GridPane();
		gp.addRow(0, idLabel, idField);
		gp.addRow(1, titleLabel, titleField);
		gp.addRow(2, authorLabel,authorField);
		gp.addRow(3, stockLabel, stockField);
		gp.add(warningLabel, 1, 4);
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
		editBookOK.buttonPressed();
		editBookCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		idField.setOnKeyReleased(e -> {
			if (idField.getText().length() > 0)
				isIdFilled = true;
			else
				isIdFilled = false;
			if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
				edit.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				edit.setDisable(false);
			}
		});
		titleField.setOnKeyReleased(e -> {
			if (titleField.getText().length() > 0)
				isTitleFilled = true;
			else
				isTitleFilled = false;
			if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
				edit.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				edit.setDisable(false);
			}
		});
		authorField.setOnKeyReleased(e -> {
			if (authorField.getText().length() > 0)
				isAuthorFilled = true;
			else
				isAuthorFilled = false;
			if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
				edit.setDisable(true);
				warningLabel.setText("All fields must be filled!");
			}
			else {
				warningLabel.setText("");
				edit.setDisable(false);
			}
		});
		stockField.setOnKeyReleased(e -> {
			try {
				if (stockField.getText().length() > 0)
					isStockFilled = true;
				else
					isStockFilled = false;
				if (!(isIdFilled && isTitleFilled && isAuthorFilled && isStockFilled)) {
					edit.setDisable(true);
					warningLabel.setText("All fields must be filled!");
				}
				else {
					warningLabel.setText("");
					edit.setDisable(false);
				}
				int quantity;
				if (isStockFilled) {
					quantity = Integer.parseInt(stockField.getText());
					if (quantity < 0) {
						warningLabel.setText("Can't enter a negative number!");
						edit.setDisable(true);
					}
				}
			} catch (NumberFormatException nfe) {
				warningLabel.setText("Stock field must be a number!");
				edit.setDisable(true);
			}
		});
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		Scene scene = new Scene(bp);
		setTitle("Edit Book");
		setScene(scene);
		showAndWait();
	}
}
