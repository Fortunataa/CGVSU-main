module com.cgvsu.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.cgvsu.protocurvefxapp to javafx.fxml;
    exports com.cgvsu.protocurvefxapp;
}