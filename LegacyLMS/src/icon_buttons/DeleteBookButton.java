package icon_buttons;

import java.sql.SQLException;
import buttons.GeneralButton;
import db.Book;
import db.DB;
import db.Table;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.BookWorkSpace;

public class DeleteBookButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/DeleteBook.png";
	private BookWorkSpace bookWorkSpace;
	
	{
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("Delete Book");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}
	
	public DeleteBookButton(BookWorkSpace bookWorkSpace) {
		this.bookWorkSpace = bookWorkSpace;
	}

	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("Delete book button pressed!");
			ObservableList<Book> selected = bookWorkSpace.getTable().getSelectionModel().getSelectedItems();
			try {
				DB.deleteFromTable(Table.BOOKS, selected);
				bookWorkSpace.clearAndUpdateTable();
			} catch (SQLException se) {
				String header = "Delete book failed!";
				Alert fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Delete Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
