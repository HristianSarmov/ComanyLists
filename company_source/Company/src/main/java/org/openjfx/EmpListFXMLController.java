package org.openjfx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import static org.openjfx.Main.getBulgarianBundle;
import static org.openjfx.Main.getEnglishBundle;
import static org.openjfx.SearchEmpByIDFXMLController.p;
import static org.openjfx.UpdateEmpInfoFXMLController.UpdatedPersonInfo;

public class EmpListFXMLController implements Initializable{
	
	private static String currentSQL = "SELECT * FROM `Employees`";
	
	private static String selectFile = Main.getFileString();
		
	public File selected = Main.getFile();
	
	private static Connection conn = null; 
	public EmpListFXMLController() {
		
	}
	
	private static Person UpdateP = new Person();
	
	private PersonDataAccess dataAccess;
	
	@FXML
	public TextArea ResultConsole = null;
	
	@FXML
	public TableView<Person> EmployeeTable = null;
	
	public String SelectAll = "Select * From Employees";
	
	@FXML
	public TableColumn<Person, String> IDColumn = null;
	@FXML
	public TableColumn<Person, String> NameColumn = null;
	@FXML
	public TableColumn<Person, String> LastNameColumn = null;
	@FXML
	public TableColumn<Person, String> EmailColumn = null;
	@FXML
	public TableColumn<Person, String> PhoneNumberColumn = null;
	@FXML
	public TableColumn<Person, String> PaydayColumn = null;
	@FXML
	public TableColumn<Person, String> SalaryColumn = null;
	
	@FXML
	public Button AddBtn = null;
	@FXML
	public Button UpdateBtn = null;
	@FXML
	public Button SearchBtn = null;
	@FXML
	public Button DeleteBtn = null;
	
	final KeyCombination AddEmployee = new KeyCodeCombination(KeyCode.A,KeyCombination.CONTROL_DOWN);
	final KeyCombination SearchEmployee = new KeyCodeCombination(KeyCode.S,KeyCombination.CONTROL_DOWN);
	final KeyCombination DeleteEmployee = new KeyCodeCombination(KeyCode.D,KeyCombination.CONTROL_DOWN);
	final KeyCombination UpdateEmployee = new KeyCodeCombination(KeyCode.U,KeyCombination.CONTROL_DOWN);
	final KeyCombination NewFile = new KeyCodeCombination(KeyCode.N,KeyCombination.CONTROL_DOWN);
	final KeyCombination OpenFile = new KeyCodeCombination(KeyCode.O,KeyCombination.CONTROL_DOWN);	
	final KeyCombination DeleteFile = new KeyCodeCombination(KeyCode.L,KeyCombination.CONTROL_DOWN);	
	
	@FXML
	public VBox rootNode = null;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (Main.getFileweb()) {
			if (!selected.exists()) {
				try {
					conn = Main.Connect(selectFile);
				} catch (SQLException | ClassNotFoundException ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
				String sql = "CREATE TABLE Employees (\n" +
					"	ID INT(4) NOT NULL UNIQUE,\n" +
					"	Name TEXT NOT NULL,\n" +
					"	LastName TEXT NOT NULL,\n" +
					"	Email TEXT NOT NULL ,\n" +
					"	PhoneNumber TEXT NOT NULL,\n" +
					"	Payday INT NOT NULL,\n" +
					"	Salary INT NOT NULL\n" +
					");";
				Statement stmnt = null;
				try {
					stmnt = conn.createStatement();
				} catch (SQLException ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
				try {
					stmnt.execute(sql);
				} catch (SQLException ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else try {
				conn = Main.Connect(selectFile);
			} catch (SQLException | ClassNotFoundException ex) {
				Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else {
			if (ConnectToExternalDBFXMLController.getRememberMe()) {
				try {
					conn = Main.Connect(Main.getWeb(),ConnectToExternalDBFXMLController.getUsername(),ConnectToExternalDBFXMLController.getPassword());
				} catch (SQLException | ClassNotFoundException ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else {
				try {
					onConnectClick();
				} catch (IOException | SQLException | ClassNotFoundException ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		try {
			buildData(Main.getProps().getProperty("sql.last"));
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
		}

		EmployeeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		EmployeeTable.setPlaceholder(new Label("No content in table"));
		
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.getExtensionFilters().addAll(
	        new ExtensionFilter("Database Files", "*.db"),
	        new ExtensionFilter("All Files", "*.*"));
		
		rootNode.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			
			if (AddEmployee.match(event)) {
				try {
					onAddClick();
				} catch (Exception ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if (SearchEmployee.match(event)) {
				try {
					onSearchClick();
				} catch (Exception ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if (DeleteEmployee.match(event)) {
				try {
					onDeleteClick();
				} catch (Exception ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if (UpdateEmployee.match(event)) {
				try {
					onUpdateClick();
				} catch (Exception ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if (NewFile.match(event)) {
				try {
					onSaveClick();
				} catch (Exception ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if (OpenFile.match(event)) {
				try {
					onOpenClick();
				} catch (Exception ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if (DeleteFile.match(event)) {
				try {
					onDeleteFileClick();
				} catch (Exception ex) {
					Logger.getLogger(EmpListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}
	
	public void buildData(String sql) throws SQLException, ClassNotFoundException {
		if (conn != null) {
			dataAccess = new PersonDataAccess();
		
		    IDColumn.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
		    NameColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
		    LastNameColumn.setCellValueFactory(cellData -> cellData.getValue().LastNameProperty());
	        EmailColumn.setCellValueFactory(cellData -> cellData.getValue().EmailProperty());
		    PhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().PhoneNumberProperty());
			PaydayColumn.setCellValueFactory(cellData -> cellData.getValue().PaydayProperty());
		    SalaryColumn.setCellValueFactory(cellData -> cellData.getValue().SalaryProperty());
		
			EmployeeTable.getItems().addAll(dataAccess.getPersonList(sql));
			currentSQL = sql;
		}
		//ResultConsole.setText(sql);
    }
	
	@FXML
	public void onShowAllAction() throws SQLException, ClassNotFoundException {
		EmployeeTable.getItems().clear();
		buildData(SelectAll);
	}
	
	@FXML
	public void setEnglish() throws IOException {
		Main.setCurrentBundle(Main.getEnglishBundle());
		Main.setRoot(FXMLLoader.load(getClass().getResource("EmpListFXML.fxml"), Main.getCurrentBundle()));
		Main.MainStage.setTitle(Main.getCurrentBundle().getString("application.title"));
		Main.setHome(new Scene(Main.getRoot()));
		Main.MainStage.setScene(Main.getHome());
		Main.setCurrentBundleName("org.openjfx.Bundle");
		Main.setCurrentBundleLocale("en_US");
	}
	
	@FXML
	public void setBulgarian() throws IOException {
		Main.setCurrentBundle(Main.getBulgarianBundle());
		Main.setRoot(FXMLLoader.load(getClass().getResource("EmpListFXML.fxml"), Main.getCurrentBundle()));
		Main.MainStage.setTitle(Main.getCurrentBundle().getString("application.title"));
		Main.setHome(new Scene(Main.getRoot()));
		Main.MainStage.setScene(Main.getHome());
		Main.setCurrentBundleName("org.openjfx.BundleBG");
		Main.setCurrentBundleLocale("bg_BG");
	}
	
	@FXML
	public void onAddClick() throws Exception {
		Main.createAddStage();
		Parent rootAdd = FXMLLoader.load(getClass().getResource("AddNewEmpFXML.fxml"), Main.getCurrentBundle());
		Main.getAddStage().setScene(new Scene(rootAdd));
		Main.getAddStage().showAndWait();
		EmployeeTable.getItems().clear();
		buildData(SelectAll);
	}
	
	@FXML
	public void onUpdateClick() throws Exception {
		if (EmployeeTable.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(Main.getCurrentBundle().getString("Alert.title"));
			alert.setHeaderText(null);
			alert.setContentText(Main.getCurrentBundle().getString("UpdateAlert.text"));
			alert.showAndWait();
		}
		else {
			UpdateP.setID(EmployeeTable.getSelectionModel().getSelectedItem().getID().trim());
			UpdateP.setName(EmployeeTable.getSelectionModel().getSelectedItem().getName().trim());
			UpdateP.setLastName(EmployeeTable.getSelectionModel().getSelectedItem().getLastName().trim());
			UpdateP.setEmail(EmployeeTable.getSelectionModel().getSelectedItem().getEmail().trim());
			UpdateP.setPhoneNumber(EmployeeTable.getSelectionModel().getSelectedItem().getPhoneNumber().trim());
			UpdateP.setPayday(EmployeeTable.getSelectionModel().getSelectedItem().getPayday().trim());
			UpdateP.setSalary(EmployeeTable.getSelectionModel().getSelectedItem().getSalary().trim());
			Main.createUpdateStage();
			Parent rootUpd = FXMLLoader.load(getClass().getResource("UpdateEmpInfoFXML.fxml"), Main.getCurrentBundle());
			Main.getUpdateStage().setScene(new Scene(rootUpd));
			Main.getUpdateStage().showAndWait();
			if (UpdateEmpInfoFXMLController.isLamp()) {
				String query = "UPDATE `Employees` SET "
						+ "ID = '"+UpdatedPersonInfo.getID().trim()+"' ,"
						+ "Name = '"+UpdatedPersonInfo.getName().trim()+"' ,"
						+ "LastName = '"+UpdatedPersonInfo.getLastName().trim()+"' ,"
						+ "Email = '"+UpdatedPersonInfo.getEmail().trim()+"' ,"
						+ "PhoneNumber = '"+UpdatedPersonInfo.getPhoneNumber().trim()+"' ,"
						+ "Payday = '"+UpdatedPersonInfo.getPayday().trim()+"' ,"
						+ "Salary = '"+UpdatedPersonInfo.getSalary().trim()+"' "
						+ "WHERE "
						+ "ID = '"+UpdateP.getID().trim()+"' AND "
						+ "Name = '"+UpdateP.getName().trim()+"' AND "
						+ "LastName = '"+UpdateP.getLastName().trim()+"' AND "
						+ "Email = '"+UpdateP.getEmail().trim()+"' AND "
						+ "PhoneNumber = '"+UpdateP.getPhoneNumber().trim()+"' AND "
						+ "Payday = '"+UpdateP.getPayday().trim()+"' AND "
						+ "Salary = '"+UpdateP.getSalary().trim()+"'";
				Statement stmnt = getConn().createStatement();
				stmnt.executeUpdate(query);
			
				ResultConsole.setText(query);
			
				EmployeeTable.getItems().clear();
				buildData(SelectAll);
			}
		}
	}
	
	@FXML
	public void onSearchClick() throws Exception {
		Main.createSearchStage();
		Parent rootSrch = FXMLLoader.load(getClass().getResource("SearchEmpByIDFXML.fxml"), Main.getCurrentBundle());
		Main.getSearchStage().setScene(new Scene(rootSrch));
		Main.getSearchStage().showAndWait();
		if (SearchEmpByIDFXMLController.isLamp()) {
			EmployeeTable.getItems().clear();
			
			String query = "SELECT * FROM `Employees` WHERE ";
			if (!p.getID().isEmpty()) query += "INSTR(ID, '"+p.getID().trim()+"') ";
			else query += "true ";
			if (!p.getName().isEmpty()) query += "AND INSTR(lower(Name), lower('"+p.getName().trim()+"')) ";
			else query += "AND true ";
			if (!p.getLastName().isEmpty()) query += "AND INSTR(lower(LastName), lower('"+p.getLastName().trim()+"')) ";
			else query += "AND true ";
			if (!p.getEmail().isEmpty()) query += "AND INSTR(lower(Email), lower('"+p.getEmail().trim()+"')) ";
			else query += "AND true ";
			if (!p.getPhoneNumber().isEmpty()) query += "AND INSTR(PhoneNumber, '"+p.getPhoneNumber().trim()+"') ";
			else query += "AND true ";
			if (!p.getPayday().isEmpty()) query += "AND INSTR(Payday, '"+p.getPayday().trim()+"') ";
			else query += "AND true ";
			if (!p.getSalary().isEmpty()) query += "AND INSTR(Salary, '"+p.getSalary().trim()+"') ";
			
			buildData(query);
		}
	}
	
	@FXML
	public void onDeleteClick() throws Exception {
		if (EmployeeTable.getSelectionModel().getSelectedItems().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(Main.getCurrentBundle().getString("Alert.title"));
			alert.setHeaderText(null);
			alert.setContentText(Main.getCurrentBundle().getString("DeleteAlert.text"));
			alert.showAndWait();
		}
		else {
			Main.createDeleteStage();
			Parent rootDel = FXMLLoader.load(getClass().getResource("DeleteEmpWarningFXML.fxml"), Main.getCurrentBundle());
			Main.getDeleteStage().setScene(new Scene(rootDel));
			Main.getDeleteStage().showAndWait();
			if (DeleteEmpWarningFXMLController.isLamp()) {
				ObservableList<Person> DeleteP = EmployeeTable.getSelectionModel().getSelectedItems();
			
				for (int i = 0 ; i<DeleteP.size() ; i++) {
			
					String query = "DELETE FROM `Employees` WHERE "
						+ "ID = '"+DeleteP.get(i).getID().trim()+"' "
						+ "AND Name = '"+DeleteP.get(i).getName().trim()+"' "
						+ "AND LastName = '"+DeleteP.get(i).getLastName().trim()+"' "
						+ "AND Email = '"+DeleteP.get(i).getEmail().trim()+"' "
						+ "AND PhoneNumber = '"+DeleteP.get(i).getPhoneNumber().trim()+"' "
						+ "AND Payday = '"+DeleteP.get(i).getPayday().trim()+"' "
						+ "AND Salary = '"+DeleteP.get(i).getSalary().trim()+"' ";
				
					Statement stmnt = getConn().createStatement();
					stmnt.executeUpdate(query);
				}
			
				EmployeeTable.getItems().clear();
				buildData(currentSQL);
			}
		}
	}
	
	
	@FXML
	public void onAboutClick() throws IOException {
		Main.createAboutStage();
		Parent rootAbout = FXMLLoader.load(getClass().getResource("AboutFXML.fxml"), Main.getCurrentBundle());
		Main.getAboutStage().setScene(new Scene(rootAbout));
		Main.getAboutStage().show();
	}
	
	public void onAppGuidanceClick() throws IOException {
		Main.createAppGuidanceStage();
		Parent rootAGuidance = FXMLLoader.load(getClass().getResource("AppGuidanceFXML.fxml"), Main.getCurrentBundle());
		Main.getAppGuidanceStage().setScene(new Scene(rootAGuidance));
		Main.getAppGuidanceStage().show();
	}
	
	public void onConnectClick() throws IOException, SQLException, ClassNotFoundException {
		Main.createConnectStage();
		Parent rootConnect = FXMLLoader.load(getClass().getResource("ConnectToExternalDBFXML.fxml"), Main.getCurrentBundle());
		Main.getConnectStage().setScene(new Scene(rootConnect));
		Main.getConnectStage().showAndWait();
		if (ConnectToExternalDBFXMLController.isLamp() == true) {
			setConn(Main.Connect("//"+ConnectToExternalDBFXMLController.getHost()+":"+ConnectToExternalDBFXMLController.getPort()+"/"+ConnectToExternalDBFXMLController.getDatabase(), ConnectToExternalDBFXMLController.getUsername(), ConnectToExternalDBFXMLController.getPassword()));
			if (getConn() != null) {
				EmployeeTable.getItems().clear();
				buildData(SelectAll);
			}
		}
	}
	
	public FileChooser fileChooser = new FileChooser();
	
	public void onOpenClick() throws Exception {
		fileChooser.setTitle("Open Database File");
		setSelectedFile(fileChooser.showOpenDialog(Main.getMainStage()).toString());
		setConn(Main.Connect(selectFile));
		if (getSelectedFile() != null) {
			EmployeeTable.getItems().clear();
			buildData(SelectAll);
		}
	}
	
	public void onDeleteFileClick() throws Exception {
		fileChooser.setTitle("Delete Database File");
		List<File> selectedList = fileChooser.showOpenMultipleDialog(Main.getMainStage());
		if (getSelectedFile() != null) {
			for (File file : selectedList) {
				file.delete();
			}
			selected = null;
			selectFile = null;
			EmployeeTable.getItems().clear();
			buildData("SELECT * FROM `Employees`");
		}
	}
	
	public void onSaveClick() throws Exception {
		fileChooser.setTitle("Create Database File");
		fileChooser.setInitialFileName("MyList.db");
		setSelectedFile(fileChooser.showSaveDialog(Main.getMainStage()).toString());
		setConn(Main.Connect(selectFile));
		if (getSelectedFile() != null) {
			String sql = "CREATE TABLE Employees (\n" +
				"	ID TEXT NOT NULL UNIQUE,\n" +
				"	Name TEXT NOT NULL,\n" +
				"	LastName TEXT NOT NULL,\n" +
				"	Email TEXT NOT NULL ,\n" +
				"	PhoneNumber TEXT NOT NULL,\n" +
				"	Payday INTEGER NOT NULL,\n" +
				"	Salary INTEGER NOT NULL\n" +
				");";
			Statement stmnt = getConn().createStatement();
			stmnt.execute(sql);
			EmployeeTable.getItems().clear();
			buildData(SelectAll);
		}
	}

	public static Person getUpdateP() {
		return UpdateP;
	}

	public static void setUpdateP(Person aUpdateP) {
		UpdateP = aUpdateP;
	}

	public static String getSelectedFile() {
		return selectFile;
	}

	public void setSelectedFile(String selectedFile) {
		this.selectFile = selectedFile;
	}

	/**
	 * @return the currentSQL
	 */
	public static String getCurrentSQL() {
		return currentSQL;
	}

	/**
	 * @param currentSQL the currentSQL to set
	 */
	public static void setCurrentSQL(String currentSQL) {
		EmpListFXMLController.currentSQL = currentSQL;
	}

	/**
	 * @return the conn
	 */
	public static Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	public static void setConn(Connection conn) {
		EmpListFXMLController.conn = conn;
	}
}
