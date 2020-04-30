package ui;

import java.sql.SQLException;
import db.Librarian;
import db.Table;
import icon_buttons.BackButton;
import icon_buttons.DeleteLibrarianButton;
import icon_buttons.EditLibrarianButton;
import icon_buttons.SearchLibrarianButton;
import icon_buttons.ViewLibrarianButton;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibrarianWorkSpace extends WorkSpace<Librarian> {
	private ViewLibrarianButton viewLibrarianButton;
	private DeleteLibrarianButton deleteLibrarianButton;
	private EditLibrarianButton editLibrarianButton;
	private SearchLibrarianButton searchLibrarianButton;
	private BackButton backButton;
	
	public LibrarianWorkSpace(Table tableType) {
		super(tableType);
	}

	public void initWorkSpace() {
		table.setEditable(true);
		table.prefWidthProperty().bind(this.widthProperty());
		table.prefHeightProperty().bind(this.heightProperty());
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		TableColumn<Librarian, String> idColumn = new TableColumn<Librarian, String>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Librarian, String>("lid"));
		idColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
		TableColumn<Librarian, String> nameColumn = new TableColumn<Librarian, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Librarian, String>("name"));
		nameColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
		TableColumn<Librarian, String> contactColumn = new TableColumn<Librarian, String>("Contact");
		contactColumn.setCellValueFactory(new PropertyValueFactory<Librarian, String>("contact"));
		contactColumn.prefWidthProperty().bind(table.widthProperty().divide(3));

		table.getColumns().addAll(idColumn, nameColumn, contactColumn);

		mainPane.setTop(iconBar);
		mainPane.setCenter(table);
		
		//Buttons on icon bar
		viewLibrarianButton = new ViewLibrarianButton(this);
		viewLibrarianButton.buttonPressed();
		deleteLibrarianButton = new DeleteLibrarianButton(this);
		deleteLibrarianButton.buttonPressed();
		deleteLibrarianButton.setDisable(true);
		editLibrarianButton = new EditLibrarianButton(this);
		editLibrarianButton.buttonPressed();
		editLibrarianButton.setDisable(true);
		searchLibrarianButton = new SearchLibrarianButton(this);
		searchLibrarianButton.buttonPressed();
		backButton = new BackButton();
		backButton.buttonPressed();
		
		iconBar.getChildren().addAll(viewLibrarianButton, deleteLibrarianButton,
									 editLibrarianButton, searchLibrarianButton,
									 backButton);
	}

	@Override
	public void foolProof() {
		TableViewSelectionModel<Librarian> tsm = this.getTable().getSelectionModel();
		table.setOnMouseClicked(e -> {
			if (selected == null) {
				selected = tsm.getSelectedItem();
				if (selected != null) {
					deleteLibrarianButton.setDisable(false);
					editLibrarianButton.setDisable(false);
				}
			}
			else {
				if (tsm.getSelectedItem() == null || tsm.getSelectedItem().equals(selected)) {
					tsm.clearSelection();
					selected = null;
					deleteLibrarianButton.setDisable(true);
					editLibrarianButton.setDisable(true);
				}
				else {
					selected = tsm.getSelectedItem();
				}
			}
		});
	}

	public void showWorkSpace() throws SQLException {
		initWorkSpace();
		foolProof();
		clearAndUpdateTable();
		Scene scene = new Scene(mainPane, 550, 500);
		this.setTitle("Librarian Workspace");
		this.setScene(scene);
		System.out.println("Librarian work space open!");
		show();
	}
}
