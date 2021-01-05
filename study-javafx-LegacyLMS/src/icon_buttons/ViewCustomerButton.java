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
import ui.CustomerWorkSpace;
import ui.Mode;

public class ViewCustomerButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/ViewCustomer.png";
	private CustomerWorkSpace customerWorkSpace;
	
	{
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("View Customer");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}
	
	public ViewCustomerButton(CustomerWorkSpace customerWorkSpace) {
		this.customerWorkSpace = customerWorkSpace;
	}

	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("View customer button pressed!");
			try {
				customerWorkSpace.setMode(Mode.VIEW);
				customerWorkSpace.clearAndUpdateTable();
			} catch (SQLException se) {
				String header = "View customer failed!";
				Alert fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("View Customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
