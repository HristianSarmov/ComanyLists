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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hristian
 */
public class SearchEmpByIDFXMLController implements Initializable{

	static Person p = new Person();
	
	Stage stage = Main.getSearchStage();
	
	Connection con;
	public SearchEmpByIDFXMLController() throws SQLException, ClassNotFoundException {
		this.con = Main.Connect(EmpListFXMLController.getSelectedFile());
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		IDSearch.textProperty().addListener(event -> {
			IDSearch.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!IDSearch.getText().isEmpty() &&
                    !IDSearch.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d")
			);
		});
		NameSearch.textProperty().addListener(event -> {
			NameSearch.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!NameSearch.getText().isEmpty() &&
                    !NameSearch.getText().matches("[a-zA-Zа-яА-Я]+")
			);
		});
		LastNameSearch.textProperty().addListener(event -> {
			LastNameSearch.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!LastNameSearch.getText().isEmpty() &&
                    !LastNameSearch.getText().matches("[a-zA-Zа-яА-Я]+")
			);
		});
		PhoneNumberSearch.textProperty().addListener(event -> {
			PhoneNumberSearch.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!PhoneNumberSearch.getText().isEmpty() &&
                    !PhoneNumberSearch.getText().matches("\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d|\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")
			);
		});
		PaydaySearch.textProperty().addListener(event -> {
			PaydaySearch.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!PaydaySearch.getText().isEmpty() &&
                    !PaydaySearch.getText().matches("0[1-9]|[12][0-9]|3[01]")
			);
		});
		SalarySearch.textProperty().addListener(event -> {
			SalarySearch.pseudoClassStateChanged(
				PseudoClass.getPseudoClass("error"),
				!SalarySearch.getText().isEmpty() &&
					!SalarySearch.getText().matches("\\d+")
			);
		});
	}
	
	private static boolean lamp = false;
	
	@FXML
	public TextField IDSearch = null;
	@FXML
	public TextField NameSearch = null;
	@FXML
	public TextField LastNameSearch = null;
	@FXML
	public TextField EmailSearch = null;
	@FXML
	public TextField PhoneNumberSearch = null;
	@FXML
	public TextField PaydaySearch = null;
	@FXML
	public TextField SalarySearch = null;
	
	@FXML
	public void onSearchClick() throws SQLException {
		
		lamp = true;
		
		p.setID(IDSearch.getText());
		p.setName(NameSearch.getText());
		p.setLastName(LastNameSearch.getText());
		p.setEmail(EmailSearch.getText());
		p.setPhoneNumber(PhoneNumberSearch.getText());
		p.setPayday(PaydaySearch.getText());
		p.setSalary(SalarySearch.getText());
		
		stage.close();
	}
	
	@FXML
	public void onCancelClick() {
		stage.close();
	}

	public static boolean isLamp() {
		return lamp;
	}

	
	
}
