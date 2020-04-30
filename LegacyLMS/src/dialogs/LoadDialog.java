package dialogs;

import java.sql.ResultSet;
import java.sql.SQLException;

import buttons.Cancel;
import db.DB;
import db.Database;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ok_buttons.LoadLMSOK;

public class LoadDialog extends Stage implements GeneralDialog {
	private DialogType dialogType;
	private GridPane gp;
	private TableView<Database> table;
	private Label headerLabel;
	private Button load;
	private Button cancel;
	private Database selected;
	private LoadLMSOK loadLMS;
	private Cancel loadCancel;
	private TableColumn<Database, String> nameColumn;

	{
		gp = new GridPane();
		table = new TableView<Database>();
		table.setEditable(false);
		table.prefWidthProperty().bind(this.widthProperty());
		table.prefHeightProperty().bind(this.heightProperty().multiply(9.0/10.0));
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		load = new Button("Load");
		load.setDisable(true);
		load.setDefaultButton(true);
		cancel = new Button("Cancel");
		loadLMS = new LoadLMSOK(load);
		loadCancel = new Cancel(cancel);
	}
	
	public LoadDialog(DialogType dialogType) {
		this.dialogType = dialogType;
		if (dialogType == DialogType.LOAD)
			headerLabel = new Label("Load LMS");
		else
			headerLabel = new Label("Select LMS");
	}

	@Override
	public void initDialog() {
		nameColumn = new TableColumn<Database, String>("Database");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Database, String>("name"));
		nameColumn.prefWidthProperty().bind(table.widthProperty());
		nameColumn.setEditable(false);
		table.getColumns().add(nameColumn);

		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		gp.addRow(0, headerLabel);
		gp.addRow(1, table);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
		hb.getChildren().addAll(load, cancel);
		gp.addRow(2, hb);
		gp.setHgap(3);
		gp.setVgap(5);
		gp.setPadding(new Insets(5));
	}

	@Override
	public void initButtons() {
		if (dialogType == DialogType.LOAD)
			loadLMS.buttonPressed();
		else
			loadLMS.buttonPressedInCreateAccount();
		loadCancel.buttonPressed();
	}

	public void foolProof() {
		TableSelectionModel<Database> tsm = table.getSelectionModel();
		table.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				selected = tsm.getSelectedItem();
				if (selected != null) {
					if (headerLabel.getText().equals("Load LMS"))
						loadLMS.loadLMS();
					else
						loadLMS.createAccount();
				}
			}
			else if (e.getClickCount() == 1) {
				if (selected == null) {
					selected = tsm.getSelectedItem();
					if (selected != null) {
						loadLMS.setDbName(selected.getName());
						load.setDisable(false);
					}
				}
				else if (selected != null) {
					if (tsm.getSelectedItem() == null || 
							tsm.getSelectedItem().equals(selected)) {
						tsm.clearSelection();
						selected = null;
						load.setDisable(true);
					}
					else {
						selected = tsm.getSelectedItem();
						loadLMS.setDbName(selected.getName());
					}
				}
			}
		});
		table.setOnKeyPressed(e -> {
			if (selected == null ) {
				tsm.selectFirst();
				selected = tsm.getSelectedItem();
			}
			if (selected == null)
				load.setDisable(true);
			else {
				load.setDisable(false);
				loadLMS.setDbName(selected.getName());
			}
		});
	}

	@Override
	public void showDialog() {
		initDialog();
		initButtons();
		foolProof();
		try {
			DB.connect();
			ResultSet result = DB.getLMSList();
			while (result.next()) {
				String name = result.getString(1);
				Database db = new Database(name);
				table.getItems().add(db);
			}
			Scene scene = new Scene(gp, 250, 300);
			setTitle("Load LMS");
			setScene(scene);
			System.out.println("Load dialog open!");
			show();
		}
		catch (SQLException | ClassNotFoundException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		catch (NullPointerException npe) {
			System.out.println("No LMS to load!");
			Scene scene = new Scene(gp, 250, 300);
			setTitle("Load LMS");
			setScene(scene);
			show();
		}
	}
}
