module org.projekt.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires org.slf4j;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens org.projekt.runner to javafx.fxml;
    exports org.projekt.runner;
    exports org.projekt.controllers.adminControllers;
    exports org.projekt.controllers.commonControllers;
    exports org.projekt.builders;
    exports org.projekt.entity;
    exports org.projekt.utils;
    exports org.projekt.exceptions;
    exports org.projekt.generics;
    exports org.projekt.interfaces;
    exports org.projekt.main;
    exports org.projekt.services;
    exports org.projekt.sort;
    exports org.projekt.threads;
    exports org.projekt.Enum;
    opens org.projekt.controllers.adminControllers;
    opens org.projekt.controllers.commonControllers;


}