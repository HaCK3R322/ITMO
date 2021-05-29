package com.androsov.client.internetConnection;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class AsyncIOHandler implements ClientIOold, Closeable {
    final private ByteBuffer buffer = ByteBuffer.allocate(4096);;
    private Selector selector;
    Set<SelectionKey> keys;
    Iterator<SelectionKey> keyIterator;
    SelectionKey currentKey;

    private SocketChannel client;

    // To prevent blocking for reading
    boolean waitingForResponse = false;

    @Override
    public void connectToServer(String ipAddress, int port) throws IOException {
        selector = Selector.open();
        client = SocketChannel.open(new InetSocketAddress(ipAddress, port));
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        System.out.println("Connected to server " + ipAddress + ":" + port);
    }

    /**
     * After getting user command, we sending this command to server/
     * @param line
     * @throws IOException
     */
    @Override
    public void sendCommandLine(String line) throws IOException {
        while(true) {
            selector.select();

            keys = selector.selectedKeys();
            keyIterator = keys.iterator();

            if(keyIterator.hasNext()) {
                currentKey = keyIterator.next();
                client = (SocketChannel) currentKey.channel();
                if(currentKey.isWritable() && !waitingForResponse) {
                    final ByteArrayOutputStream output = new ByteArrayOutputStream();
                    try (DataOutputStream os = new DataOutputStream(output)) {
                        os.writeUTF(line);
                    }

                    buffer.clear();
                    buffer.put(output.toByteArray());
                    buffer.position(0);
                    client.write(buffer);

                    waitingForResponse = true;

                    keyIterator.remove();

                    break;
                }
            } else {
                throw new RuntimeException("sendCommandLine is wrong...");
            }
        }
    }

    @Override
    public String getResponse() throws IOException { // get and dont wait
        String response = "";

        while(true) {
            selector.select();

            keys = selector.selectedKeys();
            keyIterator = keys.iterator();

            if(keyIterator.hasNext()) {
                currentKey = keyIterator.next();
                client = (SocketChannel) currentKey.channel();
                if(currentKey.isReadable()) {
                    buffer.clear();
                    client.read(buffer);

                    try (DataInputStream is = new DataInputStream(new ByteArrayInputStream(buffer.array()))) {
                        response = is.readUTF();
                    }

                    waitingForResponse = false;

                    keyIterator.remove();

                    break;
                }
            }
        }

        return response;
    }

    @Override
    public void close() throws IOException {
        client.close();
        client.socket().close();
        selector.close();
    }
}
