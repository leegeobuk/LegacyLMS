package app;

import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.TitlePage;

public class LMS extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Locale.setDefault(Locale.US);
		TitlePage titlePage = new TitlePage();
		titlePage.showDialog();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
