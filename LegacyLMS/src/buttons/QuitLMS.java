package buttons;

import dialogs.QuitDialog;
import javafx.scene.control.Button;

public class QuitLMS implements GeneralButton {
	private Button quit;
	
	public QuitLMS(Button button) {
		quit = button;
	}

	@Override
	public void buttonPressed() {
		quit.setOnAction(e -> {
			System.out.println("Quit pressed!");
			QuitDialog quitDialog = new QuitDialog("Think twice!");
			quitDialog.showDialog();
		});
	}

}
