package dialogs;

import java.sql.SQLException;
import java.util.GregorianCalendar;

import buttons.Cancel;
import db.Table;
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
import ok_buttons.BorrowBookOK;
import ui.BookWorkSpace;
import ui.CustomerWorkSpace;

public class BorrowBookDialog extends Stage implements GeneralDialog {
	private BorderPane bp = new BorderPane();
	private Label headerLabel;
	private Label idLabel;
	private Label nameLabel;
	private Label phoneLabel;
	private Label borrowLabel;
	private Label returnLabel;
	private Label warningLabel;
	private TextField idField = new TextField();
	private TextField nameField = new TextField();
	private TextField phoneField = new TextField();
	private TextField borrowField = new TextField();
	private TextField returnField = new TextField();
	private GregorianCalendar borrowDate = new GregorianCalendar();
	private Button customer = new Button("Customer");
	private Button borrow = new Button("Borrow");
	private Button cancel = new Button("Cancel");
	private BorrowBookOK borrowBookOK;
	private Cancel borrowBookCancel;
	private BookWorkSpace bookWorkSpace;

	{
		this.headerLabel = new Label("Borrow Book");
		this.idLabel = new Label("Customer ID");
		this.nameLabel = new Label("Customer name");
		this.phoneLabel = new Label("Customer phone number");
		this.borrowLabel = new Label("Borrowing date");
		this.returnLabel = new Label("Returning date");
		this.idField.setEditable(false);
		this.nameField.setEditable(false);
		this.phoneField.setEditable(false);
		this.borrowField.setEditable(false);
		this.returnField.setEditable(false);
		this.idField.setFocusTraversable(false);
		this.nameField.setFocusTraversable(false);
		this.phoneField.setFocusTraversable(false);
		this.borrowField.setFocusTraversable(false);
		this.returnField.setFocusTraversable(false);
		borrowDate.setLenient(true);
		String bday = formatDate(borrowDate);
		this.borrowField.setText(bday);
		borrowDate.add(GregorianCalendar.DAY_OF_MONTH, 14);
		String rday = formatDate(borrowDate);
		this.returnField.setText(rday);
		warningLabel = new Label("Only a customer can borrow!");
		borrow.setDisable(true);
	}

	public BorrowBookDialog(BookWorkSpace bookWorkSpace) {
		this.bookWorkSpace = bookWorkSpace;
		borrowBookOK = new BorrowBookOK(bookWorkSpace, borrow, idField, nameField, phoneField, borrowField, returnField);
		borrowBookCancel = new Cancel(cancel);
	}

	public TextField getIdField() {
		return idField;
	}

	public TextField getNameField() {
		return nameField;
	}

	public TextField getPhoneField() {
		return phoneField;
	}

	public Label getWarningLabel() {
		return warningLabel;
	}

	public Button getBorrow() {
		return borrow;
	}

	public void setWarningLabel(Label warningLabel) {
		this.warningLabel = warningLabel;
	}

	public String formatDate(GregorianCalendar gc) {
		String bday = gc.get(GregorianCalendar.YEAR) + "-";
		if (gc.get(GregorianCalendar.MONTH) < 9)
			bday += "0";
		bday += (gc.get(2)+1) + "-";
		if (gc.get(GregorianCalendar.DAY_OF_MONTH) < 10)
			bday += "0";
		bday += gc.get(GregorianCalendar.DAY_OF_MONTH);
		return bday;
	}

	@Override
	public void initDialog() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.CENTER);

		idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		phoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		borrowLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		returnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
		warningLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		warningLabel.setTextFill(Color.RED);

		GridPane gp = new GridPane();
		gp.addRow(0, idLabel);
		gp.addRow(1, idField);
		gp.addRow(2, nameLabel);
		gp.addRow(3, nameField);
		gp.addRow(4, phoneLabel);
		gp.addRow(5, phoneField);
		gp.addRow(6, borrowLabel);
		gp.addRow(7, borrowField);
		gp.addRow(8, returnLabel);
		gp.addRow(9, returnField);
		gp.addRow(10, customer);
		gp.add(warningLabel, 0, 11);
		gp.setHgap(5);
		gp.setVgap(5);
		gp.setPadding(new Insets(10, 40, 20, 40));
		bp.setCenter(gp);

		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(borrow, cancel);
		hb.setPadding(new Insets(0, 0, 8, 0));
		bp.setBottom(hb);
	}

	@Override
	public void initButtons() {
		borrowBookOK.buttonPressed();
		borrowBookCancel.buttonPressed();
	}

	public void initCustomerButton() {
		customer.setOnAction(e -> {
			System.out.println("Customer button pressed!");
			try {
				CustomerWorkSpace customerWorkSpace = new CustomerWorkSpace(Table.CUSTOMERS, bookWorkSpace, this);
				customerWorkSpace.showCustomers();
			} catch (SQLException e1) {
				System.out.println("SQL exception while loading customer list!");
				e1.printStackTrace();
			}
		});
	}

	@Override
	public void foolProof() {

	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		initCustomerButton();
		foolProof();
		Scene scene = new Scene(bp);
		setTitle("Borrow Book");
		setScene(scene);
		showAndWait();
	}
}
