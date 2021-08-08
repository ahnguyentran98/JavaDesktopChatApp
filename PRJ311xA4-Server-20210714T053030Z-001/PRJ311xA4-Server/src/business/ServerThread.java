package business;

import com.entity.Client;
import com.entity.Server;
import controller.ServerBoxController;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread implements Runnable{
    private ServerSocket serverSocket;
    private Server chatServer;
    private Socket socket;

    //store clients by key/value
    public static HashMap<String,ClientHandler> clients = new HashMap<>();

    //start server and waiting for connection
    public ServerThread(Server chatServer) {
        this.chatServer = chatServer;
        try{
            this.serverSocket = new ServerSocket(chatServer.getPort());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //create clientHandle each time client connect to server
    @Override
    public void run() {
        try{
            while (true){
                this.socket = this.serverSocket.accept();

                //take user name throughout Input stream
                DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                String username = dataInputStream.readUTF();
                Client client = new Client();

                //clear old data
                if (username != null){
                    String x = username.substring(username.indexOf(":") +1 );
                    client.setUsername(x);
                    client.setSocket(this.socket);
                    //log
                    System.out.println(client);

                    /*add new client on listview
                    Note: fix Not on FX application thread (only the FX thread can modify the ui elements)*/
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ServerBoxController.clientObservableList.add(client);
                        }
                    });

                    //create new thread to handle client's connection
                    ClientHandler clientHandler = new ClientHandler(this.socket,client);
                    clients.put(x,clientHandler);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
