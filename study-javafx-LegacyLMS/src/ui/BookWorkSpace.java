package ui;

import java.sql.SQLException;
import java.util.List;
import db.Book;
import db.BookStatus;
import db.Table;
import icon_buttons.AddBookButton;
import icon_buttons.BackButton;
import icon_buttons.BorrowBookButton;
import icon_buttons.DeleteBookButton;
import icon_buttons.EditBookButton;
import icon_buttons.ReturnBookButton;
import icon_buttons.SearchBookButton;
import icon_buttons.ViewBookButton;
import icon_buttons.ViewBorrowedBookButton;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookWorkSpace extends WorkSpace<Book> {
	private ViewBookButton viewBookButton;
	private AddBookButton addBookButton;
	private DeleteBookButton deleteBookButton;
	private EditBookButton editBookButton;
	private SearchBookButton searchBookButton;
	private ViewBorrowedBookButton viewBorrowedBookButton;
	private BorrowBookButton borrowBookButton;
	private ReturnBookButton returnBookButton;
	private BackButton backButton;

	public BookWorkSpace(Table tableType) {
		super(tableType);
	}
	
	@Override
	public void initWorkSpace() {
		table.setEditable(true);
		table.prefWidthProperty().bind(this.widthProperty());
		table.prefHeightProperty().bind(this.heightProperty());
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		TableColumn<Book, String> idColumn = new TableColumn<>("Book ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bid"));
		idColumn.prefWidthProperty().bind(table.widthProperty().multiply(4.0 / 15.0));
		TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		titleColumn.prefWidthProperty().bind(table.widthProperty().multiply(4.0 / 15.0));
		TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
		authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		authorColumn.prefWidthProperty().bind(table.widthProperty().multiply(4.0 / 15.0));
		TableColumn<Book, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));
		statusColumn.prefWidthProperty().bind(table.widthProperty().divide(10));
		TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
		stockColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("stock"));
		stockColumn.prefWidthProperty().bind(table.widthProperty().divide(10));

		table.getColumns().addAll(idColumn, titleColumn, authorColumn, statusColumn, stockColumn);

		mainPane.setTop(iconBar);
		mainPane.setCenter(table);

		//Buttons on icon bar
		viewBookButton = new ViewBookButton(this);
		viewBookButton.buttonPressed();
		addBookButton = new AddBookButton(this);
		addBookButton.buttonPressed();
		deleteBookButton = new DeleteBookButton(this);
		deleteBookButton.buttonPressed();
		deleteBookButton.setDisable(true);
		editBookButton = new EditBookButton(this);
		editBookButton.buttonPressed();
		editBookButton.setDisable(true);
		searchBookButton = new SearchBookButton(this);
		searchBookButton.buttonPressed();
		viewBorrowedBookButton = new ViewBorrowedBookButton(this);
		viewBorrowedBookButton.buttonPressed();
		borrowBookButton = new BorrowBookButton(this);
		borrowBookButton.buttonPressed();
		borrowBookButton.setDisable(true);
		returnBookButton = new ReturnBookButton(this);
		returnBookButton.buttonPressed();
		returnBookButton.setDisable(true);
		backButton = new BackButton();
		backButton.buttonPressed();

		iconBar.getChildren().addAll(viewBookButton, addBookButton,
									 deleteBookButton, editBookButton,
									 searchBookButton, viewBorrowedBookButton,
									 borrowBookButton, returnBookButton, 
									 backButton);
	}

	@Override
	public void foolProof() {
		TableViewSelectionModel<Book> tsm = this.getTable().getSelectionModel();
		ObservableList<Book> books = tsm.getSelectedItems();
		table.setOnMouseClicked(e -> {
			if (selected == null) {
				selected = tsm.getSelectedItem();
				if (selected != null) {
					deleteBookButton.setDisable(false);
					editBookButton.setDisable(false);
					if (selected.getStatus() == BookStatus.IN && hasStock(books))
						borrowBookButton.setDisable(false);
					returnBookButton.setDisable(false);
				}
			}
			else {
				if (tsm.getSelectedItem() == null || 
						tsm.getSelectedItem().equals(selected)) {
					tsm.clearSelection();
					selected = null;
					deleteBookButton.setDisable(true);
					editBookButton.setDisable(true);
					borrowBookButton.setDisable(true);
					returnBookButton.setDisable(true);
				}
				else {
					selected = tsm.getSelectedItem();
					if (selected.getStatus() == BookStatus.IN && hasStock(books))
						borrowBookButton.setDisable(false);
					else
						borrowBookButton.setDisable(true);
				}
			}
		});
	}
	
	private boolean hasStock(List<Book> books) {
		for (Book b: books) {
			if (b.getStatus() == BookStatus.OUT)
				return false;
		}
		return true;
	}

	@Override
	public void showWorkSpace() throws SQLException {
		initWorkSpace();
		foolProof();
		clearAndUpdateTable();
		Scene scene = new Scene(mainPane);
		this.setTitle("Books List");
		this.setScene(scene);
		this.setWidth(550);
		this.setHeight(500);
		System.out.println("Books list open!");
		show();
	}
}
