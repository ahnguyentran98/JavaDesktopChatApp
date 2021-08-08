package business;

import com.entity.Client;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientHandler implements Runnable, Serializable {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private Client client;
    private TextArea txtContent;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TextArea getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(TextArea txtContent) {
        this.txtContent = txtContent;
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public ClientHandler(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    public ClientHandler(Socket socket, Client client, TextArea txtContent) {
        this.socket = socket;
        this.client = client;
        this.txtContent = txtContent;
    }


    //manage and handle each client
    @Override
    public void run() {
        try{
            //get clients message and show on chat box
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (true){
                Object line = dataInputStream.readUTF();
                if (line != null) {
                    this.txtContent.appendText("\n" + this.client.getUsername() + ":" + line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void send(Object line) throws Exception{
        //send message to server and chat box
        dataOutputStream.writeUTF(line.toString());
        if (!line.toString().startsWith(":")) {
            txtContent.appendText("\nMe: " + line);
        }
    }
}
