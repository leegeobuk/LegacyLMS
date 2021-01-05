package buttons;

import dialogs.DialogType;
import dialogs.LoadDialog;
import javafx.scene.control.Button;

public class LoadLMS implements GeneralButton {
	private Button loadLMS;

	public LoadLMS(Button button) {
		loadLMS = button;
	}
	
	@Override
	public void buttonPressed() {
		loadLMS.setOnAction(e -> {
			System.out.println("Load LMS!");
			LoadDialog loadDialog = new LoadDialog(DialogType.LOAD);
			loadDialog.showDialog();
		});
	}
}
