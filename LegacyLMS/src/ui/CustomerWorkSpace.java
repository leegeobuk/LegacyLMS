package ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import db.Book;
import db.Customer;
import db.DB;
import db.Table;
import dialogs.BorrowBookDialog;
import icon_buttons.AddCustomerButton;
import icon_buttons.BackButton;
import icon_buttons.DeleteCustomerButton;
import icon_buttons.EditCustomerButton;
import icon_buttons.SearchCustomerButton;
import icon_buttons.ViewCustomerButton;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerWorkSpace extends WorkSpace<Customer> {
	private ViewCustomerButton viewCustomerButton;
	private AddCustomerButton addCustomerButton;
	private DeleteCustomerButton deleteCustomerButton;
	private EditCustomerButton editCustomerButton;
	private SearchCustomerButton searchCustomerButton;
	private BackButton backButton;
	
	private BookWorkSpace bookWorkSpace;
	private BorrowBookDialog borrowBookDialog;
	private ObservableList<Book> books;
	
	public CustomerWorkSpace(Table tableType) {
		super(tableType);
	}
	
	public CustomerWorkSpace(Table tableType, BookWorkSpace bookWorkSpace) {
		super(tableType);
		this.bookWorkSpace = bookWorkSpace;
		this.books = bookWorkSpace.getTable().getSelectionModel().getSelectedItems();
	}
	
	public CustomerWorkSpace(Table tableType, BookWorkSpace bookWorkSpace, BorrowBookDialog borrowBookDialog) {
		super(tableType);
		this.bookWorkSpace = bookWorkSpace;
		this.borrowBookDialog = borrowBookDialog;
		this.books = bookWorkSpace.getTable().getSelectionModel().getSelectedItems();
	}

	public void initWorkSpace() {
		table.setEditable(true);
		table.prefWidthProperty().bind(this.widthProperty());
		table.prefHeightProperty().bind(this.heightProperty());
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		TableColumn<Customer, Integer> idColumn = new TableColumn<Customer, Integer>("Customer ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("cid"));
		idColumn.prefWidthProperty().bind(table.widthProperty().multiply(2.0 / 10.0));
		TableColumn<Customer, String> nameColumn = new TableColumn<Customer, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("cname"));
		nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(4.0 / 10.0));
		TableColumn<Customer, String> contactColumn = new TableColumn<Customer, String>("Contact");
		contactColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("contact"));
		contactColumn.prefWidthProperty().bind(table.widthProperty().multiply(4.0 / 10.0));

		table.getColumns().addAll(idColumn, nameColumn, contactColumn);

		mainPane.setTop(iconBar);
		mainPane.setCenter(table);

		//Buttons on icon bar
		viewCustomerButton = new ViewCustomerButton(this);
		viewCustomerButton.buttonPressed();
		addCustomerButton = new AddCustomerButton(this);
		addCustomerButton.buttonPressed();
		deleteCustomerButton = new DeleteCustomerButton(this);
		deleteCustomerButton.buttonPressed();
		deleteCustomerButton.setDisable(true);
		editCustomerButton = new EditCustomerButton(this);
		editCustomerButton.buttonPressed();
		editCustomerButton.setDisable(true);
		searchCustomerButton = new SearchCustomerButton(this);
		searchCustomerButton.buttonPressed();
		backButton = new BackButton();
		backButton.buttonPressed();

		iconBar.getChildren().addAll(viewCustomerButton, addCustomerButton, 
									 deleteCustomerButton, editCustomerButton,
									 searchCustomerButton, backButton);
	}

	public void foolProof() {
		TableViewSelectionModel<Customer> tsm = this.getTable().getSelectionModel();
		table.setOnMouseClicked(e -> {
			if (selected == null) {
				selected = tsm.getSelectedItem();
				if (selected != null) {
					deleteCustomerButton.setDisable(false);
					editCustomerButton.setDisable(false);
				}
			}
			else {
				if (tsm.getSelectedItem() == null || tsm.getSelectedItem().equals(selected)) {
					tsm.clearSelection();
					selected = null;
					deleteCustomerButton.setDisable(true);
					editCustomerButton.setDisable(true);
				}
				else {
					selected = tsm.getSelectedItem();
				}
			}
		});
	}

	public void selectCustomer() {
		TableViewSelectionModel<Customer> tsm = this.getTable().getSelectionModel();
		table.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				selected = tsm.getSelectedItem();
				if (selected != null) {
					borrowBookDialog.getIdField().setText(""+selected.getCid());
					borrowBookDialog.getNameField().setText(selected.getCname());
					borrowBookDialog.getPhoneField().setText(selected.getContact());
					borrowBookDialog.getWarningLabel().setText("");
					borrowBookDialog.getBorrow().setDisable(false);
					this.hide();
				}
			}
		});
	}

	public void selectReturnCustomer() {
		TableViewSelectionModel<Customer> tsm = this.getTable().getSelectionModel();
		table.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				selected = tsm.getSelectedItem();
				if (selected != null) {
					int cid = selected.getCid();
					try {
						if (isBookRented(cid, books)) {
							System.out.println("Book successfully returned!");
							DB.returnBooks(cid, books);
							bookWorkSpace.clearAndUpdateTable();
						}
						else {
							System.out.println("Book not borrowed from this customer!");
							String message = "Book that " + selected.getCname() + " didn't borrow is contained!";
							Alert fail = new Alert(AlertType.ERROR, message, ButtonType.CLOSE);
							fail.setTitle("Return Book Error");
							fail.setHeaderText("Return book failed.");
							fail.showAndWait();
						}
						this.hide();
					} catch (SQLException sqle) {
						System.out.println("Error while returning book!");
						Alert fail = new Alert(AlertType.ERROR, sqle.getMessage(), ButtonType.CLOSE);
						fail.setTitle("Return Book Error");
						fail.setHeaderText("Return book failed.");
						fail.showAndWait();
					}
				}
			}
		});
	}

	private boolean isBookRented(int cid, List<Book> books) throws SQLException {
		String query = "SELECT bid FROM rents WHERE cid=" + cid +";";
		ResultSet result = DB.executeQuery(query);
		boolean isRented;
		for (Book b: books) {
			isRented = false;
			while (result.next()) {
				String bid = result.getString(1);
				if (b.getBid().equals(bid)) {
					isRented = true;
					break;
				}
			}
			if (!isRented)
				return false;
		}
		return true;
	}

	public void deactivateButtons() {
		addCustomerButton.setDisable(true);
	}

	public void showWorkSpace() throws SQLException {
		initWorkSpace();
		foolProof();
		updateTable();
		Scene scene = new Scene(mainPane);
		this.setTitle("Customers List");
		this.setScene(scene);
		this.setWidth(400);
		this.setHeight(400);
		System.out.println("Customers list open!");
		show();
	}
	
	public void showCustomers() throws SQLException {
		initWorkSpace();
		foolProof();
		selectCustomer();
		clearAndUpdateTable();
		Scene scene = new Scene(mainPane);
		this.setTitle("Customers List");
		this.setScene(scene);
		this.setWidth(400);
		this.setHeight(400);
		System.out.println("Customers list open!");
		show();
	}

	public void showUnreturnedCustomers() {
		initWorkSpace();
		deactivateButtons();
		foolProof();
		selectReturnCustomer();
		try {
			HashSet<Integer> cids = new HashSet<>();
			String query;
			for (Book b: books) {
				query = "SELECT DISTINCT cid FROM rents WHERE bid='" + b.getBid() + "';";
				ResultSet result = DB.executeQuery(query);
				while (result.next()) {
					cids.add(result.getInt(1));
				}
			}
			Iterator<Integer> it = cids.iterator();
			while (it.hasNext()) {
				int cid = it.next();
				query = "SELECT * FROM customers WHERE cid=" + cid + ";";
				ResultSet customerResult = DB.executeQuery(query);
				while (customerResult.next()) {
					String cname = customerResult.getString(2);
					String contact = customerResult.getString(3);
					table.getItems().add(new Customer(cid, cname, contact));
				}
			}
		} catch (SQLException e) {
			Alert fail = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
			fail.setTitle("Loading Rents Error");
			fail.setHeaderText("SQL Exception while loading rents.");
			fail.showAndWait();
		}
		Scene scene = new Scene(mainPane);
		this.setTitle("Unreturned customers List");
		this.setScene(scene);
		this.setWidth(400);
		this.setHeight(400);
		System.out.println("Unreturned customers list open!");
		show();
	}
}
