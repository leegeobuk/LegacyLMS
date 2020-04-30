package icon_buttons;

import buttons.GeneralButton;
import dialogs.SearchLibrarianDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.LibrarianWorkSpace;

public class SearchLibrarianButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/SearchLibrarian.png";
	private LibrarianWorkSpace librarianWorkSpace;
	
	public SearchLibrarianButton(LibrarianWorkSpace librarianWorkSpace) {
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("Search Librarian");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
		this.librarianWorkSpace = librarianWorkSpace;
	}
	
	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("Search librarian button pressed!");
			SearchLibrarianDialog searchLibrarianDialog = new SearchLibrarianDialog(librarianWorkSpace);
			searchLibrarianDialog.showDialog();
		});
	}
}
