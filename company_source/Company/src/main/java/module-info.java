
module Company {
	requires javafx.controls;  
	requires javafx.fxml;
	requires java.sql;
	requires java.base;
	
	opens org.openjfx to javafx.fxml;
	exports org.openjfx;
}
