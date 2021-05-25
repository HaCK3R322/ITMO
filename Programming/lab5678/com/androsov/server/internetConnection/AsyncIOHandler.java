package com.androsov.server.internetConnection;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * This asynchronized io must strictly adhere to one order of actions:
 * let the selector choose the channel with which it will work at a certain iteration,
 * configure the keys on which the work will be carried out,
 * and if the key is intended for reading(that is a command has come from the client),
 * then is imperative to send the result of this commands, and then delete this key.
 */
public class AsyncIOHandler implements ServerIO, Closeable {
    final public Selector selector;
    private final LinkedList<SocketChannel> channels = new LinkedList<>();
    private SocketChannel currentChannel;
    private Set<SelectionKey> keys;
    private Iterator<SelectionKey> keyIterator;
    private SelectionKey currentKey;

    final ByteBuffer buffer;

    final ServerSocketChannel server;

    /**
     * In constructor we allocating buffer, initializing, configure blocking and register server,
     * registering selector on ACCEPT.
     */
    public AsyncIOHandler() {
        try {
            buffer = ByteBuffer.allocate(4096);

            selector = Selector.open();

            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress("localhost", 25565));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void closeRefusedConnections() {
//        try {
//            for(int i = 0; i < channels.size(); i++) {
//                if(!channels.get(i).isConnected()) {
//                    channels.get(i).socket().close();
//                    channels.get(i).close();
//                    channels.remove(i);
//                    System.out.println("channel " + channels.get(i).socket().getInetAddress() + " disconnected");
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("close error");
//        }
//    }

    /**
     * There we configure all keys to iterate with.
     */
    public void configureKeys() {
        keys = selector.selectedKeys();
        keyIterator = keys.iterator();
    }

    /**
     * If has next key, iterates next key (can be only READABLE or ACCEPTABLE, when it READABLE, next step must be
     * instant read, so it becomes WRITABLE, we write and wait when it becomes READABLE again).
      * @return
     */
    public boolean goToNextKey() {
        if(keyIterator.hasNext()) {
            currentKey = keyIterator.next();
            return true;
        } else {
            return false;
        }
    }

    /**
     * All easy: if current key is ACCEPTABLE, we accept new client.
     */
    @Override
    public void accept() {
        try {
            if (currentKey.isAcceptable()) {
                final SocketChannel client = server.accept();
                if(client != null) {
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);

                    channels.add(client);

                    System.out.println("Connected client " + client.socket().getInetAddress().toString());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * If client wrote command, we read it and when command executed, on the next step we must send result,
     * so channel becomes readable again.
     * @return
     * @throws IOException
     */
    @Override
    public String getCommandLine() throws IOException {
        String line = "";
        if (currentKey.isReadable()) {
            currentChannel = (SocketChannel) currentKey.channel();

            buffer.clear();
            currentChannel.read(buffer);

            try (DataInputStream is = new DataInputStream(new ByteArrayInputStream(buffer.array()))) {
                line = is.readUTF();
            }

            System.out.println("got command from (" + currentChannel.socket().getInetAddress().getCanonicalHostName() + "): " + line);
        } else {
            line = "wait";
        }
        return line;
    }

    /**
     * Taking into account that the response can be sent only after receiving
     * the command, the key always remains WRITABLE, so we send our response
     * to the channel without any problems.
     * @param line
     * @throws IOException
     */
    @Override
    public void sendResponse(String line) throws IOException {
        currentChannel = (SocketChannel) currentKey.channel();

        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (DataOutputStream os = new DataOutputStream(output)) {
            os.writeUTF(line);
        }

        buffer.clear();
        buffer.put(output.toByteArray());
        buffer.position(0);
        currentChannel.write(buffer);
    }

    @Override
    public void close() throws IOException {
        selector.close();
        currentChannel.close();
    }

    public void removeCurrentKey() {
        keyIterator.remove();
    }
}
