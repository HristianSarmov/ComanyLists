/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hristian
 */
public class AddNewEmpFXMLController implements Initializable{
	@FXML
	public Button CancelButton;
	
	PseudoClass errorClass = PseudoClass.getPseudoClass("error");
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		IDText.textProperty().addListener(event -> {
			IDText.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!IDText.getText().isEmpty() &&
                    !IDText.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d")
			);
		});
		NameText.textProperty().addListener(event -> {
			NameText.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!NameText.getText().isEmpty() &&
                    !NameText.getText().matches("[a-zA-Zа-яА-Я]+")
			);
		});
		LastNameText.textProperty().addListener(event -> {
			LastNameText.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!LastNameText.getText().isEmpty() &&
                    !LastNameText.getText().matches("[a-zA-Zа-яА-Я]+")
			);
		});
		EmailText.textProperty().addListener(event -> {
			EmailText.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!EmailText.getText().isEmpty() &&
                    !EmailText.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
			);
		});
		PhoneNumberText.textProperty().addListener(event -> {
			PhoneNumberText.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!PhoneNumberText.getText().isEmpty() &&
                    !PhoneNumberText.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d|\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")
			);
		});
		PaydayText.textProperty().addListener(event -> {
			PaydayText.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!PaydayText.getText().isEmpty() &&
                    !PaydayText.getText().matches("0[1-9]|[12][0-9]|3[01]")
			);
		});
		SalaryText.textProperty().addListener(event -> {
			SalaryText.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!SalaryText.getText().isEmpty() &&
					!SalaryText.getText().matches("\\d+")
			);
		});
	}
	
	@FXML
	public void CancelButtonPress() {
		Stage stage = (Stage) CancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public Button SaveChangesButton = null;
	
	@FXML 
	public TextField IDText = null;
	@FXML
	public TextField NameText = null;
	@FXML
	public TextField LastNameText = null;
	@FXML
	public TextField EmailText = null;
	@FXML
	public TextField PhoneNumberText = null;
	@FXML
	public TextField PaydayText = null;
	@FXML
	public TextField SalaryText = null;
	
	@FXML
	public void SaveButtonPress() throws SQLException, ClassNotFoundException {
		
		if (IDText.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d") && 
			NameText.getText().matches("[a-zA-Zа-яА-Я]+") && 
			LastNameText.getText().matches("[a-zA-Zа-яА-Я]+") && 
			EmailText.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])") && 
			PhoneNumberText.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d|\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d") && 
			PaydayText.getText().matches("0[1-9]|[12][0-9]|3[01]") && 
			SalaryText.getText().matches("\\d+")) {
		
			Connection conn1 = EmpListFXMLController.getConn();
			String ID = IDText.getText();
			String Name = NameText.getText();
			String LastName = LastNameText.getText();
			String Email = EmailText.getText();
			String PhoneNumber = PhoneNumberText.getText();
			String Payday = PaydayText.getText();
			String Salary = SalaryText.getText();		 
			
			String sqlIDCheck = "SELECT * FROM Employees WHERE ID="+ID+";";
			Statement stmtIDCheck = conn1.createStatement();
			if (stmtIDCheck.executeQuery(sqlIDCheck).next() == false) {
			
				String sql = "INSERT INTO Employees (ID,Name,LastName,Email,PhoneNumber,Payday,Salary) VALUES ('"+ID.trim()+"','"+Name.trim()+"','"+LastName.trim()+"','"+Email.trim()+"','"+PhoneNumber.trim()+"','"+Payday.trim()+"','"+Salary.trim()+"');";
				Statement stmt = conn1.createStatement();
				stmt.executeUpdate(sql);
		
				Stage stage = (Stage) SaveChangesButton.getScene().getWindow();
				stage.close();
			}
			else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(Main.getCurrentBundle().getString("ExistingID.title"));
				alert.setHeaderText(null);
				alert.setContentText(Main.getCurrentBundle().getString("ExistingIDMessage.text"));
				alert.showAndWait();
			}
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
