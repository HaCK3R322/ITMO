package com.androsov.server.InternetConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IOHandler implements ServerIO {
    protected ServerSocket serverSocket;
    protected Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public IOHandler() {
        try {
            serverSocket = new ServerSocket(0);
            System.out.println("Server io handler opened on address " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
            accept();
        } catch (Exception e)  {
            System.out.println("Server io handler exception: " + e.getMessage());
        }
    }

    @Override
    public void accept() {
        try {
            System.out.println("Server io handler: waiting for access from any client.");
            socket = serverSocket.accept();
            System.out.println("Server io handler: client " + socket.getRemoteSocketAddress().toString() + " connected.");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandLine() throws IOException {
        return in.readUTF();
    }

    @Override
    public void sendResponse(String line) throws IOException {
        out.writeUTF(line);
        out.flush();
    }
}
