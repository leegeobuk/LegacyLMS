package dialogs;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class QuitDialog extends Stage {
	private String content;
	private Alert alert;
	
	public QuitDialog(String contentText) {
		content = contentText;
		alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
		alert.setTitle("Quit LMS");
		alert.setHeaderText("Quit LMS?");
	}

	public void showDialog() {
		alert.showAndWait().ifPresent(e -> {
			if (e == ButtonType.YES) {
				System.out.println("Bye bye!");
				Platform.exit();
			}
			else {
				System.out.println("Quit canceled!");
				this.hide();
			}
		});
	}

}
