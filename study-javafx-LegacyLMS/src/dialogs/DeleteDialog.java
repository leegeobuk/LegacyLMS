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
import ok_buttons.DeleteLMSOK;

public class DeleteDialog extends Stage implements GeneralDialog {
	private GridPane gp;
	private TableView<Database> table;
	private Label headerLabel;
	private Button delete;
	private Button cancel;
	private Database selected;
	private DeleteLMSOK deleteLMS;
	private Cancel loadCancel;
	
	public DeleteDialog(String header) {
		gp = new GridPane();
		table = new TableView<Database>();
		table.prefWidthProperty().bind(this.widthProperty());
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		headerLabel = new Label(header);
		delete = new Button("Delete");
		delete.setDisable(true);
		cancel = new Button("Cancel");
		deleteLMS = new DeleteLMSOK(delete, table, "");
		loadCancel = new Cancel(cancel);
	}
	
	@Override
	public void initDialog() {
		TableColumn<Database, String> nameColumn = new TableColumn<Database, String>("Database");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Database, String>("name"));
		nameColumn.prefWidthProperty().bind(table.widthProperty());
		table.getColumns().add(nameColumn);
		
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		headerLabel.setTextFill(Color.DARKBLUE);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		gp.addRow(0, headerLabel);
		gp.addRow(1, table);
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.BASELINE_CENTER);
    	hb.getChildren().addAll(delete, cancel);
    	gp.addRow(2, hb);
		gp.setHgap(3);
		gp.setVgap(5);
		gp.setPadding(new Insets(5));
	}

	@Override
	public void initButtons() {
		deleteLMS.buttonPressed();
		loadCancel.buttonPressed();
	}

	@Override
	public void foolProof() {
		TableSelectionModel<Database> tsm = table.getSelectionModel();
//		tsm.selectedItemProperty().addListener(e -> {
//			delete.setDisable(false);
//			selected = tsm.getSelectedItem();
//			if (selected != null)
//				deleteLMS.setDbName(selected.getName());
//		});
		table.setOnMouseClicked(e -> {
			if (selected == null) {
				selected = tsm.getSelectedItem();
				if (selected != null) {
					delete.setDisable(false);
					deleteLMS.setDbName(selected.getName());
				}
			}
			else {
				if (tsm.getSelectedItem() == null || 
						tsm.getSelectedItem().equals(selected)) {
					tsm.clearSelection();
					selected = null;
					delete.setDisable(true);
				}
				else {
					selected = tsm.getSelectedItem();
					deleteLMS.setDbName(selected.getName());
				}
			}
		});
		table.setOnKeyReleased(e -> {
			selected = tsm.getSelectedItem();
			if (selected == null)
				delete.setDisable(true);
			else
				delete.setDisable(false);
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
			setTitle("Delete LMS");
			setScene(scene);
			System.out.println("Delete LMS dialog open!");
			show();
		}
		catch (SQLException | ClassNotFoundException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		catch (NullPointerException npe) {
			System.out.println("No LMS to delete!");
			Scene scene = new Scene(gp, 250, 300);
			setTitle("Delete LMS");
			setScene(scene);
			show();
		}
	}
}
