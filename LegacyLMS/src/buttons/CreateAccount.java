package buttons;

import dialogs.DialogType;
import dialogs.LoadDialog;
import javafx.scene.control.Button;

public class CreateAccount implements GeneralButton {
	private Button createAccount;

	public CreateAccount(Button button) {
		createAccount = button;
	}

	@Override
	public void buttonPressed() {
		createAccount.setOnAction(e -> {
			System.out.println("Create New Account!");
			LoadDialog loadDialog = new LoadDialog(DialogType.CREATE);
			loadDialog.showDialog();
		});
	}
}
