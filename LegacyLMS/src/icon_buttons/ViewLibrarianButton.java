package icon_buttons;

import java.sql.SQLException;
import buttons.GeneralButton;
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
import ui.Mode;

public class ViewLibrarianButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/ViewLibrarian.png";
	private LibrarianWorkSpace librarianWorkSpace;
	
	public ViewLibrarianButton(LibrarianWorkSpace librarianWorkSpace) {
		this.librarianWorkSpace = librarianWorkSpace;
		Image image = new Image(PATH, 24, 24, true, true);
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
			System.out.println("View librarian button pressed!");
			try {
				librarianWorkSpace.setMode(Mode.VIEW);
				librarianWorkSpace.clearAndUpdateTable();
			} catch (SQLException e1) {
				System.out.println("SQL Exception while viewing librarians!");
				Alert fail = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.CLOSE);
				fail.setTitle("View Librarian Error");
				fail.setHeaderText("View librarian failed!");
				fail.showAndWait();
			}
		});		
	}
}
