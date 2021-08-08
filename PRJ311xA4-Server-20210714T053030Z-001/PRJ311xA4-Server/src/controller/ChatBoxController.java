package controller;

import business.ClientHandler;
import business.ServerThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatBoxController {
    @FXML
    private TextArea txtContent;
    @FXML
    private Button btnSend;
    @FXML
    private TextField txtMessage;

    private ClientHandler clientHandler;
    private ServerBoxController serverBoxController;
    private String username;

    //set user information for chat box thread
    public void setUsername(String username) {
        this.username = username;
        this.clientHandler = (ClientHandler) ServerThread.clients.get(username);
        this.clientHandler.setTxtContent(this.txtContent);
        (new Thread(this.clientHandler)).start();
    }

    //send message action
    public void btnSendActionPerformed(ActionEvent actionEvent){
        try {
            System.out.println(this.txtMessage.getText());
            this.clientHandler.send(this.txtMessage.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
