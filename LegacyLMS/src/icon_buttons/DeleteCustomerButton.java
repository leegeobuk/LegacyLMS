package icon_buttons;

import java.sql.SQLException;

import buttons.GeneralButton;
import db.Customer;
import db.DB;
import db.Table;
import javafx.collections.ObservableList;
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

public class DeleteCustomerButton extends Button implements GeneralButton {
	private static final String PATH = "file:./icons/DeleteCustomer.png";
	private CustomerWorkSpace customerWorkSpace;
	
	{
		Image image = new Image(PATH, 24, 24, true, true);
		ImageView iv = new ImageView(image);
		iv.setEffect(new Glow(0.5));
		Tooltip tooltip = new Tooltip("Delete Customer");
		tooltip.setShowDelay(Duration.seconds(0.7));
		tooltip.setStyle("-fx-background-color: #009999");
		tooltip.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		this.setGraphic(iv);
		this.setTooltip(tooltip);
	}
	
	public DeleteCustomerButton(CustomerWorkSpace customerWorkSpace) {
		this.customerWorkSpace = customerWorkSpace;
	}

	@Override
	public void buttonPressed() {
		setOnAction(e -> {
			System.out.println("Delete customer button pressed!");
			ObservableList<Customer> selected = customerWorkSpace.getTable().getSelectionModel().getSelectedItems();
			try {
				DB.deleteFromTable(Table.CUSTOMERS, selected);
				customerWorkSpace.clearAndUpdateTable();
			} catch (SQLException se) {
				String header = "Delete customer failed!";
				Alert fail = new Alert(AlertType.ERROR, se.getMessage(), ButtonType.OK);
				fail.setTitle("Delete Customer Error");
				fail.setHeaderText(header);
				fail.showAndWait();
			}
		});
	}
}
