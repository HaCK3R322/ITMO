package com.androsov.client.internetConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class IOHandler implements ClientIO {
    InetAddress serverAddress;
    int serverPort;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public IOHandler() {
    }


    @Override
    public void connectToServer(String ipAddress, int port) throws IOException {
        serverAddress = InetAddress.getByName(ipAddress);
        serverPort = port;

        socket = new Socket(serverAddress, serverPort);

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        System.out.println("Connected to server " + ipAddress + ":" + port);
    }

    @Override
    public void sendCommandLine(String line) throws IOException {
        out.writeUTF(line);
        out.flush();
    }

    @Override
    public String getResponse() throws IOException {
        return in.readUTF();
    }
}
