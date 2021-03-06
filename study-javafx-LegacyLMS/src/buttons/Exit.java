package buttons;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Exit extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/Close.png";
	
	public Exit() {
		Image image = new Image(PATH, 64, 64, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("Exit");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}

	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			this.getScene().getWindow().hide();
		});
	}
}
