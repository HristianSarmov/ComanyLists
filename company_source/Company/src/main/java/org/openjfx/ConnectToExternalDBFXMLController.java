package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author hristian
 */
public class ConnectToExternalDBFXMLController {

	Stage stage = Main.getConnectStage();
	
	@FXML
	private CheckBox RememberMeCheck;
	
	@FXML
	private TextField HostnameField;
	
	@FXML
	private TextField PortField;
	
	@FXML
	private TextField DatabaseField;
	
	@FXML
	private TextField UsernameField;
	
	@FXML
	private PasswordField PassField;
	
	private static boolean lamp = false;
	
	private static String Host;
	private static String Port;
	private static String Database;
	private static String Username;
	private static String Password;
	private static Boolean RememberMe;
	
	public void onConnectClick() {
		Host = HostnameField.getText();
		Port = PortField.getText();
		Database = DatabaseField.getText();
		Username = UsernameField.getText();
		Password = PassField.getText();
		RememberMe = RememberMeCheck.isSelected();

		lamp = true;
		stage.close();
	}
	
	public void onCancelClick() {
		stage.close();
	}

	public static String getHost() {
		return Host;
	}

	public static void setHost(String Host) {
		ConnectToExternalDBFXMLController.Host = Host;
	}

	public static String getDatabase() {
		return Database;
	}

	public static void setDatabase(String Database) {
		ConnectToExternalDBFXMLController.Database = Database;
	}

	public static String getPort() {
		return Port;
	}

	public static void setPort(String Port) {
		ConnectToExternalDBFXMLController.Port = Port;
	}

	public static String getUsername() {
		return Username;
	}

	public static void setUsername(String Username) {
		ConnectToExternalDBFXMLController.Username = Username;
	}

	public static String getPassword() {
		return Password;
	}

	public static void setPassword(String Password) {
		ConnectToExternalDBFXMLController.Password = Password;
	}

	public static boolean isLamp() {
		return lamp;
	}

	public static void setLamp(boolean lamp) {
		ConnectToExternalDBFXMLController.lamp = lamp;
	}
	
	/**
	 * @return the RememberMe
	 */
	public static Boolean getRememberMe() {
		return RememberMe;
	}

	/**
	 * @param aRememberMe the RememberMe to set
	 */
	public static void setRememberMe(Boolean aRememberMe) {
		RememberMe = aRememberMe;
	}
}
