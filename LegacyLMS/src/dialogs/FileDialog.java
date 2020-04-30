package dialogs;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileDialog extends Stage {
	protected String title;
	protected File dir;
	protected FileChooser fc;
	
	public FileDialog(String title) {
		this.title = title;
		dir = new File("./work");
		dir.mkdir();
		fc = new FileChooser();
		fc.setTitle(title);
		fc.setInitialDirectory(dir);
		fc.getExtensionFilters().add(new ExtensionFilter("LMS Files", "*.lms"));
	}

}
