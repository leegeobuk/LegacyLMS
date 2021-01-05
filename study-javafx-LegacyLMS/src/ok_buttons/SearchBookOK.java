package ok_buttons;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

import buttons.GeneralButton;
import db.BookStatus;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import ui.BookWorkSpace;
import ui.Mode;

public class SearchBookOK extends Button implements GeneralButton {
	private Button search;
	private BookWorkSpace bookWorkSpace;
	private TextField bidField;
	private TextField titleField;
	private TextField authorField;
	private ChoiceBox<BookStatus> statusBox;
	private Alert fail;
	
	public SearchBookOK(BookWorkSpace bookWorkSpace, Button ok,
						TextField bidField, TextField titleField,
						TextField authorField, ChoiceBox<BookStatus> statusBox) {
		this.bookWorkSpace = bookWorkSpace;
		this.search = ok;
		this.search.setDefaultButton(true);
		this.bidField = bidField;
		this.titleField = titleField;
		this.authorField = authorField;
		this.statusBox = statusBox;
	}
	
	@Override
	public void buttonPressed() {
		search.setOnAction(e -> {
			System.out.println("Search book pressed!");
			HashMap<Integer, String> searchCriteria = new HashMap<>();
			try {
				if (!bidField.getText().equals(""))
					searchCriteria.put(1, bidField.getText());
				if (!titleField.getText().equals(""))
					searchCriteria.put(2, titleField.getText());
				if (!authorField.getText().equals(""))
					searchCriteria.put(3, authorField.getText());
				if (statusBox.getValue() != null)
					searchCriteria.put(4, statusBox.getValue().name());
				
				bookWorkSpace.setSearchCriteria(searchCriteria);
				bookWorkSpace.setMode(Mode.SEARCH);
				bookWorkSpace.clearAndUpdateTable();
				search.getScene().getWindow().hide();
			}
			catch (SQLIntegrityConstraintViolationException sicve) {
				System.out.println("SQL Integrity Exception while searching books.");
				String header = "Search book failed!";
				fail = new Alert(AlertType.ERROR, sicve.getMessage(), ButtonType.OK);
				fail.setTitle("Search Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
			catch (SQLException se) {
				System.out.println("SQL Exception while searching books.");
				String header = "Search book failed!";
				fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Search Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
