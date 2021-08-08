//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com;

import business.ClientThread;
import com.entity.Client;
import com.entity.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientChatController {
    @FXML
    private TextArea txtContent;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtHostIP;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtMessage;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnSend;
    ClientThread clientThread = null;

    public ClientChatController() {
    }

    //connect to server
    public void btnConnectActionPerformed(ActionEvent actionEvent) {
        if (this.clientThread == null) {
            try {
                //add user to client
                Client client = new Client(this.txtUsername.getText(), "");
                /*Set server hostIP and port
                note: reset local ip each time open project*/
                Server server = new Server(this.txtHostIP.getText(), Integer.parseInt(this.txtPort.getText()));
                //set clientThread
                this.clientThread = new ClientThread(server, this.txtContent);
                //start new thread
                Thread thread = new Thread(this.clientThread);
                thread.start();
                this.clientThread.send(":" + client.getUsername());
                this.txtContent.appendText("Connected to server");
                this.btnConnect.setDisable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //send message action
    public void btnSendActionPerformed(ActionEvent actionEvent) {
        try {
            this.clientThread.send(this.txtMessage.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
