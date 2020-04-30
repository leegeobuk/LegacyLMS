package icon_buttons;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import buttons.GeneralButton;
import db.DB;
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
import ui.Mode;

public class ViewBorrowedBookButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/ViewBorrowedBook.png";
	private BookWorkSpace bookWorkSpace;

	{
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("Borrowed Book");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}

	public ViewBorrowedBookButton(BookWorkSpace bookWorkSpace) {
		this.bookWorkSpace = bookWorkSpace;
	}

	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("View borrowed book button pressed!");
			HashMap<Integer, String> searchCriteria = new HashMap<>();
			try {
				bookWorkSpace.setMode(Mode.SEARCH);
				bookWorkSpace.getTable().getItems().clear();
				String query = "SELECT bid FROM rents;";
				ResultSet result = DB.executeQuery(query);
				while (result.next()) {
					String bid = result.getString(1);
					searchCriteria.put(1, bid);
					bookWorkSpace.setSearchCriteria(searchCriteria);
					bookWorkSpace.updateTable();
				}
			} catch (SQLException se) {
				String header = "View borrowed book failed!";
				Alert fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("View Borrowed Book Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
