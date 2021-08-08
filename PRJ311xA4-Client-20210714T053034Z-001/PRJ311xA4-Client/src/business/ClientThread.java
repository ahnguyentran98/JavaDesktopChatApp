/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import com.entity.Server;
import javafx.scene.control.TextArea;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;


public class ClientThread implements Runnable, Serializable {

    //for I/O
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;
    private Server server;
    private TextArea txtContent;





    //connect to server and get I/O stream
    public ClientThread(Server server, TextArea txtContent) {
        try{
            this.txtContent = txtContent;
            this.server = server;
            socket = new Socket(server.getHost(),server.getPort());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //receive message from sever and show on the chat place
    @Override
    public void run() {
        try{
            while (true){
                Object line = dataInputStream.readUTF();
                if (line != null){
                    txtContent.appendText("\n" + "Sever "+ "(" + server.getHost() + ")" + ":" + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //send input message to server
    public void send(Object line) throws Exception {
        dataOutputStream.writeUTF(line.toString());
        if (!line.toString().startsWith(":")){
            txtContent.appendText("\nMe: " + line);
        }

    }

    //check layout
    //phuong thuc ket noi
}
