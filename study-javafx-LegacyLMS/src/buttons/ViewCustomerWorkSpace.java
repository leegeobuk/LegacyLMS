package buttons;

import java.sql.SQLException;

import db.Table;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.CustomerWorkSpace;

public class ViewCustomerWorkSpace extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/ViewCustomer.png";
	
	public ViewCustomerWorkSpace() {
		Image image = new Image(PATH, 64, 64, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("View Customer");
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
				CustomerWorkSpace customerWorkspace = new CustomerWorkSpace(Table.CUSTOMERS);
				customerWorkspace.showWorkSpace();
			} catch (SQLException e1) {
				Alert fail = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.CLOSE);
				fail.setTitle("View Customer Work Space Error");
				fail.setHeaderText("Loading customer work space failed.");
				fail.showAndWait();
			}
		});
	}
}
