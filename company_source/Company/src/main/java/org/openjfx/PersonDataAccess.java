/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hristian
 */
public class PersonDataAccess {

     Connection conn = null;

    public PersonDataAccess() throws SQLException, ClassNotFoundException {
		conn = EmpListFXMLController.getConn();
		
    }

    public List<Person> getPersonList(String sql) throws SQLException {
        try (
            Statement stmnt = conn.createStatement();
				
            ResultSet rs = stmnt.executeQuery(sql);
			
        ){
            List<Person> personList = new ArrayList<>();
            while (rs.next()) {
				
                String ID = rs.getString("ID");
				String Name = rs.getString("Name");
                String LastName = rs.getString("LastName");
                String Email = rs.getString("Email");
				String PhoneNumber = rs.getString("PhoneNumber");
				String Payday = rs.getString("Payday");
				String Salary = rs.getString("Salary");
                Person person = new Person(ID, Name, LastName, Email, PhoneNumber, Payday, Salary);
                personList.add(person);
				
            }
            return personList ;
        } 
    }
}
