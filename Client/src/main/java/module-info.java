module Client {

    requires java.rmi;
    requires org.apache.commons.io;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;

    requires com.jfoenix;
    requires org.controlsfx.controls;

    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.dashicons;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.sql;
    //requires java.sql.rowset;
    requires java.xml.bind;

    opens eg.gov.iti.jets.models.singleChat;
    opens eg.gov.iti.jets;
    opens eg.gov.iti.jets.controllers;
    opens eg.gov.iti.jets.controllers.dto;
    opens eg.gov.iti.jets.models.network.interfaces;
}
