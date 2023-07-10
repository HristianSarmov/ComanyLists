package org.openjfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	private static Properties props = new Properties();
	
	private static Stage AddStage = null;
	private static Stage UpdateStage = null;
	private static Stage SearchStage = null;
	private static Stage DeleteStage = null;
	private static Stage AboutStage = null;
	private static Stage AppGuidanceStage = null;
	private static Stage ConnectStage = null;
	public static Stage MainStage = null;
	
    public static void main(String[] args) throws SQLException {
		launch(args);
    }
	
	private static ResourceBundle currentBundle;
	private static String currentBundleName;
	private static String currentBundleLocale;
	private static Scene home;
	public static Stage stage1 = null;
	private static ResourceBundle EnglishBundle = ResourceBundle.getBundle("org.openjfx.Bundle", new Locale("en", "US"));
	private static ResourceBundle BulgarianBundle = ResourceBundle.getBundle("org.openjfx.BundleBG", new Locale("bg", "BG"));
	private static Parent root;
	private static Boolean fileweb;
	private static String web;
	private static String fileString;
	private static File file;
	
	@Override
	public void start(Stage stage) throws Exception {
		try (InputStream input = new FileInputStream("Settings.properties")) {
			props.load(input);
						
			//read locale
			String bundle = props.getProperty("lang.bundle");
			String locale = props.getProperty("lang.locale");
			String fw = props.getProperty("fileorweb.db");
			
			if ("true".equals(fw)) {
				fileString = props.getProperty("file.db");
				file = new File(fileString);
				fileweb = true;
			}
			else {
				String selected = props.getProperty("rememberme.status");
				if ("true".equals(selected)) {
					ConnectToExternalDBFXMLController.setRememberMe(true);
					ConnectToExternalDBFXMLController.setUsername(props.getProperty("username.db"));
					ConnectToExternalDBFXMLController.setPassword(props.getProperty("password.db"));
					setWeb(props.getProperty("web.db"));
				}
				else ConnectToExternalDBFXMLController.setRememberMe(false);
				fileweb = false;
			}
			currentBundle = ResourceBundle.getBundle(bundle, new Locale(locale));
			currentBundleName = bundle;
			currentBundleLocale = locale;
			
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		root = FXMLLoader.load(getClass().getResource("EmpListFXML.fxml"), currentBundle);
		setHome(new Scene(root));
		
		stage.setOnCloseRequest(event -> {
			try (OutputStream output2 = new FileOutputStream("Settings.properties")) {
			
			    // set the properties value
			    props.setProperty("lang.bundle", currentBundleName);
			    props.setProperty("lang.locale", currentBundleLocale);
			    props.setProperty("sql.last", EmpListFXMLController.getCurrentSQL());
			    props.setProperty("fileorweb.db", getFileweb().toString());
			    if (getFileweb() == false) {
					props.setProperty("rememberme.status", ConnectToExternalDBFXMLController.getRememberMe().toString());
					if (ConnectToExternalDBFXMLController.getRememberMe() == true) {
						props.setProperty("username.db", ConnectToExternalDBFXMLController.getUsername());
						props.setProperty("password.db", ConnectToExternalDBFXMLController.getPassword());
						props.setProperty("web.db", "//"+ConnectToExternalDBFXMLController.getHost()+":"+ConnectToExternalDBFXMLController.getPort()+"/"+ConnectToExternalDBFXMLController.getDatabase());
					}
				}
				else {
					props.setProperty("file.db", EmpListFXMLController.getSelectedFile());
				}
			    props.store(output2, null);
			
			} catch (IOException io) {
			    io.printStackTrace();
			}	
		});
		
		stage.setTitle(currentBundle.getString("application.title"));
//		EmpListFXMLController.getEmployeeTable().setPlaceholder(new Label(currentBundle.getString("Table.Placeholder")));
		stage.setScene(getHome());
		stage.show();
		
		MainStage = stage;
		
		stage1 = stage;
	}
	
	public static void createAddStage() {
		AddStage = new Stage();
		AddStage.setTitle(currentBundle.getString("AddEmployee.title"));
		AddStage.setResizable(false);
		AddStage.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static void createUpdateStage() {
		UpdateStage = new Stage();
		UpdateStage.setTitle(currentBundle.getString("UpdateWindow.title"));
		UpdateStage.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static void createSearchStage() {
		SearchStage = new Stage();
		SearchStage.setTitle(currentBundle.getString("SearchWindow.title"));
		SearchStage.setAlwaysOnTop(true);
		SearchStage.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static void createDeleteStage() {
		DeleteStage = new Stage();
		DeleteStage.setTitle(currentBundle.getString("DeleteWindow.title"));
		DeleteStage.setAlwaysOnTop(true);
		DeleteStage.setResizable(false);
		DeleteStage.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static void createAboutStage() {
		AboutStage = new Stage();
		AboutStage.setTitle(currentBundle.getString("AboutWindow.title"));
		AboutStage.setAlwaysOnTop(true);
		AboutStage.setResizable(false);
		AboutStage.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static void createAppGuidanceStage() {
		AppGuidanceStage = new Stage();
		AppGuidanceStage.setTitle(currentBundle.getString("AppGuidanceWindow.title"));
		AppGuidanceStage.setAlwaysOnTop(true);
		AppGuidanceStage.setResizable(false);
		AppGuidanceStage.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static void createConnectStage() {
		ConnectStage = new Stage();
		ConnectStage.setTitle(currentBundle.getString("ConnectWindow.title"));
		ConnectStage.setAlwaysOnTop(true);
		ConnectStage.setResizable(false);
		ConnectStage.initModality(Modality.APPLICATION_MODAL);
	}
	
	public static Connection Connect(String urlDatabase) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		urlDatabase = "jdbc:sqlite:"+urlDatabase;
		conn = DriverManager.getConnection(urlDatabase);
		setFileweb((Boolean) true);
			return conn;
	}
	
	public static Connection Connect(String urlDatabase, String username, String password) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		urlDatabase = "jdbc:mariadb:"+urlDatabase;
		conn = DriverManager.getConnection(urlDatabase, username, password);
		setFileweb((Boolean) false);
			return conn;
	}
	
	public static Stage getAddStage() {
		return AddStage;
	} 
	
	public static Stage getUpdateStage() {
		return UpdateStage;
	}
	
	public static Stage getSearchStage() {
		return SearchStage;
	}
	
	public static Stage getDeleteStage() {
		return DeleteStage;
	}
	
	public static Stage getMainStage() {
		return MainStage;
	}

	public static ResourceBundle getEnglishBundle() {
		return EnglishBundle;
	}

	public static void setEnglishBundle(ResourceBundle aEnglishBundle) {
		EnglishBundle = aEnglishBundle;
	}

	public static Parent getRoot() {
		return root;
	}
	
	public static ResourceBundle getBulgarianBundle() {
		return BulgarianBundle;
	}
	
	public static void setRoot(Parent root) {
		Main.root = root;
	}

	public static Scene getHome() {
		return home;
	}

	public static void setHome(Scene home) {
		Main.home = home;
	}

	public static ResourceBundle getCurrentBundle() {
		return currentBundle;
	}

	public static void setCurrentBundle(ResourceBundle currentBundle) {
		Main.currentBundle = currentBundle;
	}

	public static Stage getAboutStage() {
		return AboutStage;
	}

	public static void setAboutStage(Stage AboutStage) {
		Main.AboutStage = AboutStage;
	}
	
	public static Stage getAppGuidanceStage() {
		return AppGuidanceStage;
	}

	public static void setAppGuidanceStage(Stage AppGuidanceStage) {
		Main.AppGuidanceStage = AppGuidanceStage;
	}

	public static Properties getProps() {
		return props;
	}

	public static void setProps(Properties props) {
		Main.props = props;
	}

	/**
	 * @return the currentBundleName
	 */
	public static String getCurrentBundleName() {
		return currentBundleName;
	}

	/**
	 * @param currentBundleName the currentBundleName to set
	 */
	public static void setCurrentBundleName(String currentBundleName) {
		Main.currentBundleName = currentBundleName;
	}

	/**
	 * @return the currentBundleLocale
	 */
	public static String getCurrentBundleLocale() {
		return currentBundleLocale;
	}

	/**
	 * @param currentBundleLocale the currentBundleLocale to set
	 */
	public static void setCurrentBundleLocale(String currentBundleLocale) {
		Main.currentBundleLocale = currentBundleLocale;
	}

	/**
	 * @return the ConnectStage
	 */
	public static Stage getConnectStage() {
		return ConnectStage;
	}

	/**
	 * @param ConnectStage the ConnectStage to set
	 */
	public static void setConnectStage(Stage ConnectStage) {
		Main.ConnectStage = ConnectStage;
	}

	/**
	 * @return the fileweb
	 */
	public static Boolean getFileweb() {
		return fileweb;
	}

	/**
	 * @param aFileweb the fileweb to set
	 */
	public static void setFileweb(Boolean aFileweb) {
		fileweb = aFileweb;
	}

	/**
	 * @return the web
	 */
	public static String getWeb() {
		return web;
	}

	/**
	 * @param aWeb the web to set
	 */
	public static void setWeb(String aWeb) {
		web = aWeb;
	}

	/**
	 * @return the file
	 */
	public static String getFileString() {
		return fileString;
	}

	/**
	 * @param aFile the file to set
	 */
	public static void setFileString(String aFile) {
		fileString = aFile;
	}

	/**
	 * @return the file
	 */
	public static File getFile() {
		return file;
	}

	/**
	 * @param aFile the file to set
	 */
	public static void setFile(File aFile) {
		file = aFile;
	}
	
}