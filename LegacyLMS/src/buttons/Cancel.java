package buttons;

import javafx.scene.control.Button;

public class Cancel implements GeneralButton {
	private Button cancel;
	
	public Cancel(Button button) {
		cancel = button;
	}
	
	@Override
	public void buttonPressed() {
		cancel.setOnAction(e -> {
			System.out.println("Cancel pressed!");
			cancel.getScene().getWindow().hide();
		});
	}

}
