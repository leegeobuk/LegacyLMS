package ui;

import buttons.Exit;
import buttons.ViewBookWorkSpace;
import buttons.ViewCustomerWorkSpace;
import buttons.ViewLibrarianWorkSpace;
import db.AccountType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Menu extends Stage {
	private ViewLibrarianWorkSpace viewLibrarians;
	private ViewBookWorkSpace viewBooks;
	private ViewCustomerWorkSpace viewCustomers;
	private Exit exit;
	private BorderPane bp;
	private Label headerLabel;
	private GridPane gp;
	private AccountType accountType;
	
	public Menu(AccountType accountType) {
		bp = new BorderPane();
		headerLabel = new Label("Main Menu");
		gp = new GridPane();
		viewLibrarians = new ViewLibrarianWorkSpace();
		viewBooks = new ViewBookWorkSpace();
		viewCustomers = new ViewCustomerWorkSpace();
		exit = new Exit();
		this.accountType = accountType;
	}
	
	public void initWorkSpace() {
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		bp.setTop(headerLabel);
		BorderPane.setAlignment(headerLabel, Pos.CENTER);
		
		gp.addRow(0, viewLibrarians, viewBooks);
		gp.addRow(1, viewCustomers, exit);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(5, 0, 5, 15));
		bp.setCenter(gp);
	}
	
	public void initButtons() {
		if(accountType == AccountType.ADMIN)
			viewLibrarians.buttonPressed();
		else
			viewLibrarians.setDisable(true);
		viewBooks.buttonPressed();
		viewCustomers.buttonPressed();
		exit.buttonPressed();
	}
	
	public void showMenu() {
		initWorkSpace();
		initButtons();
		Scene scene = new Scene(bp);
		this.setScene(scene);
		this.setWidth(215);
		this.setHeight(230);
		this.setTitle("Menu");
		this.setResizable(false);
		this.show();
	}	
}
