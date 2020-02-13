module Client {


    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires com.jfoenix;
    requires java.rmi;

    requires org.controlsfx.controls;

    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;

    opens eg.gov.iti.jets;
    opens eg.gov.iti.jets.views;

//export eg.gov.iti.jets.views to module javafx.fxml
}
