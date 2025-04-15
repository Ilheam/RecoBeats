module JAVA_PROJECT {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 
    requires javafx.media;
	requires javafx.graphics;
    
  
    exports app; 
    exports controllers;
    
    opens controllers to javafx.fxml;
    opens models to javafx.base;
}
