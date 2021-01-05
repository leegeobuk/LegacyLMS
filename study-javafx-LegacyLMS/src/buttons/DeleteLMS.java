package buttons;

import dialogs.DeleteDialog;
import javafx.scene.control.Button;

public class DeleteLMS implements GeneralButton {
	private Button deleteLMS;
	
	public DeleteLMS(Button button) {
		deleteLMS = button;
	}

	@Override
	public void buttonPressed() {
		deleteLMS.setOnAction(e -> {
			System.out.println("Delete LMS pressed!");
			DeleteDialog deleteDialog = new DeleteDialog("Delete LMS");
			deleteDialog.showDialog();
		});
	}

}
