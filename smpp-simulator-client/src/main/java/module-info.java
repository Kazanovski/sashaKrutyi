module com.gms.ua.smppsimulatorclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;


    opens com.gms.ua.smppsimulatorclient to javafx.fxml;
    exports com.gms.ua.smppsimulatorclient;
}