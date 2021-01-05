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
import ui.BookWorkSpace;
import ui.Mode;

public class ViewBookButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/ViewBook.png";
	private BookWorkSpace bookWorkSpace;
	
	public ViewBookButton(BookWorkSpace bookWorkSpace) {
		this.bookWorkSpace = bookWorkSpace;
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("View Book");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}
	
	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("View book button pressed!");
			try {
				bookWorkSpace.setMode(Mode.VIEW);
				bookWorkSpace.clearAndUpdateTable();
			} catch (SQLException e1) {
				System.out.println("SQL Exception while viewing books!");
				Alert fail = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.CLOSE);
				fail.setTitle("View Book Error");
				fail.setHeaderText("View book failed!");
				fail.showAndWait();
			}
		});		
	}
}
