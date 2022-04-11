package com.example.lab10client;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // Initialize JavaFX Components
    @FXML private Button button_send;
    @FXML private TextField tf_message;
    @FXML private TextField tf_username;
    @FXML private Button button_exit;
    // Initialize client
    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Create new client on localhost:1234
            client = new Client(new Socket("localhost", 1234));
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = tf_username.getText() + ": " + tf_message.getText();
                if (!messageToSend.isEmpty()) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));
                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(233,233,236)");
                    textFlow.setStyle("-fx-background-color: rgb(15,125,242)");
                    textFlow.setStyle("-fx-background-radius: 20px");
                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.color(0.934,0.945,0.996));

                    hBox.getChildren().add(textFlow);

                    client.sendMessageToServer(messageToSend);
                    tf_username.clear();
                    tf_message.clear();
                }
            }
        });
    }

    public void exitProgram() throws Exception {
        Stage window = (Stage) button_exit.getScene().getWindow();
        window.close();
    }

    public static void addLabel(String msgFromServer, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);");
        textFlow.setStyle("-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}