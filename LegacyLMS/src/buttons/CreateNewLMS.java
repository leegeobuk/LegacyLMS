package buttons;

import dialogs.CreateNewLMSDialog;
import javafx.scene.control.Button;

public class CreateNewLMS implements GeneralButton {
	private Button createNewLMS;

	public CreateNewLMS(Button button) {
		createNewLMS = button;
	}

	@Override
	public void buttonPressed() {
		createNewLMS.setOnAction(e -> {
			System.out.println("Create New LMS!");
			CreateNewLMSDialog saveDialog = new CreateNewLMSDialog("Create New LMS", "Name:");
			saveDialog.showDialog();
		});
	}
}
