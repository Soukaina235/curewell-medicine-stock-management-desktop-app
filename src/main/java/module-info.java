module com.example.curewell {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires com.jfoenix;

    opens com.curewell.model to javafx.base;


    exports com.curewell;
    opens com.curewell to javafx.fxml;
}