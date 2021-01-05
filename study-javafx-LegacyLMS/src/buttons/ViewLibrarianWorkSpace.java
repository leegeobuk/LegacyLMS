package buttons;

import java.sql.SQLException;

import db.Table;
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
import ui.LibrarianWorkSpace;

public class ViewLibrarianWorkSpace extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/ViewLibrarian.png";
	
	public ViewLibrarianWorkSpace() {
		Image image = new Image(PATH, 64, 64, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("View Librarian");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}

	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			try {
				LibrarianWorkSpace librarianWorkSpace = new LibrarianWorkSpace(Table.LIBRARIANS);
				librarianWorkSpace.showWorkSpace();
			} catch (SQLException e1) {
				Alert fail = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.CLOSE);
				fail.setHeaderText("SQL Exception while loading librarians");
				fail.setTitle("View Librarians Error");
				fail.showAndWait();
			}
		});
	}
}
