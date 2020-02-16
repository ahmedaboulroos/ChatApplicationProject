module Server {

    requires java.sql;
    requires java.rmi;

    requires ojdbc;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires javafx.swing;

    requires java.desktop;

    requires com.jfoenix;
    requires org.controlsfx.controls;

    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;

    opens eg.gov.iti.jets;
    opens eg.gov.iti.jets.views;
    opens eg.gov.iti.jets.views.models;
    opens eg.gov.iti.jets.models.dao.interfaces;

    exports eg.gov.iti.jets.models.dao.interfaces to java.rmi;

}
