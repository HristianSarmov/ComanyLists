/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx;
import javafx.beans.property.StringProperty ;
import javafx.beans.property.SimpleStringProperty ;
/**
 *
 * @author hristian
 */
public class Person {
	private final StringProperty ID = new SimpleStringProperty(this, "ID");
	public StringProperty IDProperty() {
		return ID;
	}
	public final String getID() {
		return IDProperty().get();
	}
	public final void setID(String ID) {
		IDProperty().set(ID);
	}
	
	private final StringProperty Name = new SimpleStringProperty(this, "Name");
    public StringProperty NameProperty() {
        return Name ;
    }
    public final String getName() {
        return NameProperty().get();
    }
    public final void setName(String Name) {
        NameProperty().set(Name);
    }

    private final StringProperty LastName = new SimpleStringProperty(this, "LastName");
    public StringProperty LastNameProperty() {
        return LastName ;
    }
    public final String getLastName() {
        return LastNameProperty().get();
    }
    public final void setLastName(String LastName) {
        LastNameProperty().set(LastName);
    }

    private final StringProperty Email = new SimpleStringProperty(this, "Email");
    public StringProperty EmailProperty() {
        return Email;
    }
    public final String getEmail() {
        return EmailProperty().get();
    }
    public final void setEmail(String Email) {
        EmailProperty().set(Email);
    }
	
	private final StringProperty PhoneNumber = new SimpleStringProperty(this, "PhoneNumber");
	public StringProperty PhoneNumberProperty() {
		return PhoneNumber;
	}
	public final String getPhoneNumber() {
		return PhoneNumberProperty().get();
	}
	public final void setPhoneNumber(String PhoneNumber) {
		PhoneNumberProperty().set(PhoneNumber);
	}
	
	private final StringProperty Payday = new SimpleStringProperty(this, "Payday");
    public StringProperty PaydayProperty() {
        return Payday;
    }
    public final String getPayday() {
        return PaydayProperty().get();
    }
    public final void setPayday(String Payday) {
        PaydayProperty().set(Payday);
    }
	
	private final StringProperty Salary = new SimpleStringProperty(this, "Salary");
    public StringProperty SalaryProperty() {
        return Salary;
    }
    public final String getSalary() {
        return SalaryProperty().get();
    }
    public final void setSalary(String Salary) {
        SalaryProperty().set(Salary);
    }
	
    public Person() {}

    public Person(String ID, String Name, String LastName, String Email, String PhoneNumber, String Payday, String Salary) {
        setID(ID);
		setName(Name);
        setLastName(LastName);
        setEmail(Email);
		setPhoneNumber(PhoneNumber);
		setPayday(Payday);
		setSalary(Salary);
	}
}
