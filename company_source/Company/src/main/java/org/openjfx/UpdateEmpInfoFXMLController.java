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
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hristian
 */
public class UpdateEmpInfoFXMLController implements Initializable{
	
	Connection con;
	public UpdateEmpInfoFXMLController() throws SQLException, ClassNotFoundException {
		this.con = EmpListFXMLController.getConn();
	}
	
	static Person UpdatedPersonInfo = new Person();
	
	@FXML
	public TextField IDUpdField = null;
	@FXML
	public TextField NameUpdField = null;
	@FXML
	public TextField LastNameUpdField = null;
	@FXML
	public TextField EmailUpdField = null;
	@FXML
	public TextField PhoneNumberUpdField = null;
	@FXML 
	public TextField PaydayUpdField = null;
	@FXML
	public TextField SalaryUpdField = null;
	
	Stage stage = Main.getUpdateStage();
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		IDUpdField.textProperty().addListener(event -> {
			IDUpdField.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!IDUpdField.getText().isEmpty() &&
                    !IDUpdField.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d")
			);
		});
		NameUpdField.textProperty().addListener(event -> {
			NameUpdField.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!NameUpdField.getText().isEmpty() &&
                    !NameUpdField.getText().matches("[a-zA-Zа-яА-Я]+")
			);
		});
		LastNameUpdField.textProperty().addListener(event -> {
			LastNameUpdField.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!LastNameUpdField.getText().isEmpty() &&
                    !LastNameUpdField.getText().matches("[a-zA-Zа-яА-Я]+")
			);
		});
		EmailUpdField.textProperty().addListener(event -> {
			EmailUpdField.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!EmailUpdField.getText().isEmpty() &&
                    !EmailUpdField.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
			);
		});
		PhoneNumberUpdField.textProperty().addListener(event -> {
			PhoneNumberUpdField.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!PhoneNumberUpdField.getText().isEmpty() &&
                    !PhoneNumberUpdField.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d|\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")
			);
		});
		PaydayUpdField.textProperty().addListener(event -> {
			PaydayUpdField.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!PaydayUpdField.getText().isEmpty() &&
                    !PaydayUpdField.getText().matches("0[1-9]|[12][0-9]|3[01]")
			);
		});
		SalaryUpdField.textProperty().addListener(event -> {
			SalaryUpdField.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!SalaryUpdField.getText().isEmpty() &&
					!SalaryUpdField.getText().matches("\\d+")
			);
		});
		IDUpdField.setText(EmpListFXMLController.getUpdateP().getID());
		NameUpdField.setText(EmpListFXMLController.getUpdateP().getName());
		LastNameUpdField.setText(EmpListFXMLController.getUpdateP().getLastName());
		EmailUpdField.setText(EmpListFXMLController.getUpdateP().getEmail());
		PhoneNumberUpdField.setText(EmpListFXMLController.getUpdateP().getPhoneNumber());
		PaydayUpdField.setText(EmpListFXMLController.getUpdateP().getPayday());
		SalaryUpdField.setText(EmpListFXMLController.getUpdateP().getSalary());
	}
	
	private static boolean lamp = false;
	public static boolean isLamp() {
		return lamp;
	}
	
	public void onDiscardAction(ActionEvent event) throws Exception {
		stage.close();
	}	
	
	public void onSaveAction(ActionEvent event) throws Exception {
		if (IDUpdField.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d") && 
			NameUpdField.getText().matches("[a-zA-Zа-яА-Я]+") && 
			LastNameUpdField.getText().matches("[a-zA-Zа-яА-Я]+") && 
			EmailUpdField.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])") && 
			PhoneNumberUpdField.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d|\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d") && 
			PaydayUpdField.getText().matches("0[1-9]|[12][0-9]|3[01]") && 
			SalaryUpdField.getText().matches("\\d+")) {
			lamp = true;
		
			UpdatedPersonInfo.setID(IDUpdField.getText());
			UpdatedPersonInfo.setName(NameUpdField.getText());
			UpdatedPersonInfo.setLastName(LastNameUpdField.getText());
			UpdatedPersonInfo.setEmail(EmailUpdField.getText());
			UpdatedPersonInfo.setPhoneNumber(PhoneNumberUpdField.getText());
			UpdatedPersonInfo.setPayday(PaydayUpdField.getText());
			UpdatedPersonInfo.setSalary(SalaryUpdField.getText());
			
			stage.close();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(Main.getCurrentBundle().getString("IncorrectValue.alerttitle"));
			alert.setHeaderText(null);
			alert.setContentText(Main.getCurrentBundle().getString("IncorrectText.alert"));
			alert.showAndWait();
		}
	}


}
