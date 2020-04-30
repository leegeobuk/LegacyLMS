package icon_buttons;

import buttons.GeneralButton;
import dialogs.EditCustomerDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.CustomerWorkSpace;

public class EditCustomerButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/EditCustomer.png";
	private CustomerWorkSpace customerWorkSpace;
	
	{
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("Edit Customer");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}
	
	public EditCustomerButton(CustomerWorkSpace customerWorkSpace) {
		this.customerWorkSpace = customerWorkSpace;
	}
	
	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("Edit customer button pressed!");
			EditCustomerDialog editCustomerDialog = new EditCustomerDialog(customerWorkSpace);
			editCustomerDialog.showDialog();
		});
	}
}
