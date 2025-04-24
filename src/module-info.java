module JAVA_PROJECT {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.media;
	requires javafx.graphics;

	requires mysql.connector.j;
	requires transitive jbcrypt;
	requires javafx.base;

	exports app;
	exports controllers;

	opens controllers to javafx.fxml;
	opens models to javafx.base;
}
