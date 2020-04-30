package ok_buttons;

import java.sql.ResultSet;
import java.sql.SQLException;
import buttons.GeneralButton;
import db.DB;
import db.Database;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

public class DeleteLMSOK implements GeneralButton {
	private Button delete;
	private String dbName;
	private TableView<Database> table;
	
	public DeleteLMSOK(Button delete, TableView<Database> table, String dbName) {
		this.delete = delete;
		this.table = table;
		this.dbName = dbName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	@Override
	public void buttonPressed() {
		delete.setOnAction(e -> {
			System.out.println("Delete LMS delete button pressed!");
			if (dbName != null) {
				String query = "DROP DATABASE " + dbName + ";";
				try {
					DB.executeQuery(query);
					System.out.println("Deletion successful!");
					String header = "LMS deleted successfully!";
					Alert success = new Alert(AlertType.INFORMATION, "Demolish!", ButtonType.OK);
					success.setTitle("Delete LMS");
					success.setHeaderText(header);
					success.showAndWait();
					updateLMSTable();
				} catch (SQLException e1) {
					System.out.println("SQL Exception while deleting LMS");
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void updateLMSTable() {
		try {
			ResultSet result = DB.getLMSList();
			table.getItems().clear();
			if (result != null) {
				while (result.next()) {
					String dbName = result.getString(1);
					Database database = new Database(dbName);
					table.getItems().add(database);
				}
				table.getSelectionModel().clearSelection();
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception while updating database table!");
			e.printStackTrace();
		}
	}
}
