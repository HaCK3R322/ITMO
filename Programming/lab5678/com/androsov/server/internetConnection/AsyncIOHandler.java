package com.androsov.server.internetConnection;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class AsyncIOHandler implements ServerIO {
    final public Selector selector;
    private SocketChannel currentChannel;
    private Set<SelectionKey> keys;
    private Iterator<SelectionKey> keyIterator;
    private SelectionKey currentKey;

    final ByteBuffer buffer;

    final ServerSocketChannel server;

    public AsyncIOHandler() {
        try {
            buffer = ByteBuffer.allocate(4096);

            selector = Selector.open();

            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress("localhost", 27015));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void configureKeys() {
        keys = selector.selectedKeys();
        keyIterator = keys.iterator();
    }

    /**
     * if has next key, iterates next key (READ OR ACCEPT)
      * @return
     */
    public boolean nextKey() {
        if(keyIterator.hasNext()) {
            currentKey = keyIterator.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void accept() {
        try {
            if (currentKey.isAcceptable()) {
                final SocketChannel client = server.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandLine() throws IOException {
        String result = "";
        if (currentKey.isReadable()) {
            currentChannel = (SocketChannel) currentKey.channel();

            buffer.clear();
            currentChannel.read(buffer);

            try (DataInputStream is = new DataInputStream(new ByteArrayInputStream(buffer.array()))) {
                result = is.readUTF();
            }
        } else {
            result = "wait";
        }
        return result;
    }

    @Override
    public void sendResponse(String line) throws IOException {
        if(!line.equals("wait")) {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            try (DataOutputStream os = new DataOutputStream(output)) {
                os.writeUTF(line);
            }

            buffer.clear();
            buffer.put(output.toByteArray());
            buffer.position(0);
            currentChannel.write(buffer);
        }
    }

    public void removeCurrentKey() {
        keyIterator.remove();
    }
}
