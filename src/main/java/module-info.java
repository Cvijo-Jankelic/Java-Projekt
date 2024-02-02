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
    opens org.projekt.controllers;
    opens org.projekt.standardGui;
    exports org.projekt.standardGui;
    exports org.projekt.controllers;


}