package icon_buttons;

import buttons.GeneralButton;
import dialogs.AddBookDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.BookWorkSpace;

public class AddBookButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/AddBook.png";
	private BookWorkSpace bookWorkSpace;
	
	public AddBookButton(BookWorkSpace workSpace) {
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("Add Book");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
		this.bookWorkSpace = workSpace;
	}
	
	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("Add book button pressed!");
			AddBookDialog addBookDialog = new AddBookDialog(bookWorkSpace);
			addBookDialog.showDialog();
		});		
	}
}
