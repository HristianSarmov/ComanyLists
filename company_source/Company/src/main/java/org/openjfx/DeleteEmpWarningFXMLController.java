/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hristian
 */
public class DeleteEmpWarningFXMLController {

	Connection conn = null;	
	public DeleteEmpWarningFXMLController() throws SQLException, ClassNotFoundException {
		this.conn = Main.Connect(EmpListFXMLController.getSelectedFile());
	}
	
	private static boolean lamp = false;
	
	@FXML
	public Button YesButton = null;
	@FXML
	public Button NoButton = null;
	
	Stage stage = Main.getDeleteStage();
	
	@FXML
	public void onYesChosen(ActionEvent event) throws Exception {
		lamp = true;
		
		stage.close();
	}
	
	@FXML
	public void onNoChosen(ActionEvent event) throws Exception {
		stage.close();
	}
	
	public static boolean isLamp() {
		return lamp;
	}
	
}
