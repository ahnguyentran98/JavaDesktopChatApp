package controller;

import com.entity.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ServerBoxController {
    public static ObservableList<Client> clientObservableList = FXCollections.observableArrayList();

    @FXML
    private ListView<Client> clients;

    //initialize client list view
    @FXML
    void initialize() {
        this.clients.setItems(clientObservableList);
    }

    //choose client to chat action
    public void listClientsMouseClicked(MouseEvent mouseEvent) {
        try {
            //choose by double click on client
            if (mouseEvent.getClickCount() == 2) {
                //open chat box and chat box controller
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ui/ChatBox.fxml"));
                Parent parent = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                String clientName = this.clients.getSelectionModel().getSelectedItem().getUsername();
                stage.setTitle("Chat with " + clientName);
                stage.show();
                ChatBoxController chatBoxController = (ChatBoxController) loader.getController();
                chatBoxController.setUsername(clientName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
