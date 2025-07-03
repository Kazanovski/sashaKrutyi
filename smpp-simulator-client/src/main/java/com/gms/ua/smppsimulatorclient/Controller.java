package com.gms.ua.smppsimulatorclient;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // Binding Block
    public TextField hostname;
    public TextField port;
    public TextField systemId;
    public TextField password;
    public ChoiceBox<String> mode;

    // Binding buttons block
    public Button connect;
    public Button disconnect;

    // Submit Settings Block
    public TextArea text;
    public TextField destAddr;
    public TextField sourceAddr;
    public CheckBox deliveryReceipt;
    public ChoiceBox<String> dataCoding;
    public Button submit;
    //
    public TextArea logs;
    public Button clearLog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logs.setEditable(false);
        dataCoding.getItems().addAll(List.of("Default", "Latin1", "USC2"));
        mode.getItems().addAll(List.of("Transmitter", "Receiver", "Transceiver"));
    }

    public void onConnectButtonClick(ActionEvent actionEvent) {
        if (!hostname.getText().isEmpty()
                && !port.getText().isEmpty()
                && !systemId.getText().isEmpty()
                && !password.getText().isEmpty()
                && !mode.getValue().isEmpty()) {
            log("OK", "Session started connection...");
        } else {
            log("WARN", "All binding settings fields mandatory!");
        }
    }

    private void log(String level, String message) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logs.appendText(level + "  " + date + " " + message + "\n");
    }

    public void oClearLogsButtonClick(ActionEvent actionEvent) {
        logs.clear();
    }

}