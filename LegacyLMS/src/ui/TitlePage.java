package ui;

import buttons.CreateAccount;
import buttons.CreateNewLMS;
import buttons.DeleteLMS;
import buttons.LoadLMS;
import buttons.QuitLMS;
import dialogs.GeneralDialog;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TitlePage extends Stage implements GeneralDialog {
	private VBox vb;
	private HBox hb;
	private Image image;
	private Button createLMS;
	private Button createAccount;
	private Button load;
	private Button delete;
	private Button quit;
	private final String PATH = "./Logo.jpg";
	
	public TitlePage() {
		vb = new VBox();
		hb = new HBox(50);
		createLMS = new Button("Create LMS");
		load = new Button("Load LMS");
		delete = new Button("Delete LMS");
		createAccount = new Button("Create Account");
		quit = new Button("Quit LMS");
		image = new Image(PATH, 700, 450, false, false);
	}
	
	@Override
	public void initDialog() {
		ImageView iv = new ImageView(image);
		vb.getChildren().addAll(iv, hb);
		hb.getChildren().addAll(createLMS, load, delete, createAccount, quit);
		hb.setFillHeight(true);
		createLMS.setMinSize(100, 50);
		load.setMinSize(100, 50);
		delete.setMinSize(100, 50);
		createAccount.setMinSize(100, 50);
		quit.setMinSize(100, 50);
	}
	
	@Override
	public void initButtons() {
		CreateNewLMS createNewLMS = new CreateNewLMS(createLMS);
		createNewLMS.buttonPressed();
		LoadLMS loadLMS = new LoadLMS(load);
		loadLMS.buttonPressed();
		DeleteLMS deleteLMS = new DeleteLMS(delete);
		deleteLMS.buttonPressed();
		CreateAccount createAccountButton = new CreateAccount(createAccount);
		createAccountButton.buttonPressed();
		QuitLMS quitLMS = new QuitLMS(quit);
		quitLMS.buttonPressed();
	}

	@Override
	public void foolProof() {
		
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		Scene scene = new Scene(vb, 700, 500);
		this.setTitle("Library Management System");
        this.setScene(scene);
        this.setResizable(false);
		this.show();
	}
}
