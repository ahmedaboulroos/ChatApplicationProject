module Client {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires com.jfoenix;
    requires java.rmi;

    opens eg.gov.iti.jets;
    opens eg.gov.iti.jets.views;

//export eg.gov.iti.jets.views to module javafx.fxml
}
