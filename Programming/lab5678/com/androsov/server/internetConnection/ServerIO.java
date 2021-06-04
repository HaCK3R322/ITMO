package com.androsov.server.internetConnection;

import com.androsov.general.IO.IO;
import com.androsov.general.ObjectSerialization;
import com.androsov.general.User;
import com.androsov.general.request.Request;
import com.androsov.general.response.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ServerIO {
    private User currentUser;

    private final ServerSocketChannel serverSocketChannel;
    public final Selector selector;

    public ServerIO() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 25565));
        serverSocketChannel.configureBlocking(false);

        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    //смотрим, если он хочет зарегистрироваться, регистрируем
    public void acceptAll() throws IOException {
        try { selector.select(); } catch (IOException ignored) {}

        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            if (key.isAcceptable()) {
                final SocketChannel clientSocketChannel = serverSocketChannel.accept();
                clientSocketChannel.configureBlocking(false);
                clientSocketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                System.out.println("Connected client " + clientSocketChannel.socket().getRemoteSocketAddress());
                keyIterator.remove();
            }
        }
    }

    //если он что-то записал, т.е. кей стал Реадабл, то возвращаем труе
    public boolean hasRequest() {
//        try { selector.select(); } catch (IOException ignored) {}
        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            try {
                final SelectionKey key = keyIterator.next();
                if (key.isReadable()) {
                    return true;
                }
            } catch (CancelledKeyException ignored) {}
        }

        return false;
    }

    public void setUser(User user) {
        currentUser = user;
    }

    public void send(Response response) {
        ByteBuffer buffer = ByteBuffer.allocate(0);
        try {
            buffer = ObjectSerialization.serialize(response);
        } catch (IOException e) {
            System.out.println("");
        }

        try { selector.select(); } catch (IOException ignored) {}
        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            try {

                if (key.isWritable() && ((SocketChannel) key.channel()).getRemoteAddress().equals(response.getUser().getUserAddress())) {
                    ((SocketChannel)key.channel()).write(buffer);
                    keyIterator.remove();
                    return;
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
                try {
                    key.channel().close();
                    ((SocketChannel) key.channel()).socket().close();
                } catch (IOException ignored) { }
            }
        }
        System.out.println("No available channels at the moment");
    }

    public Request get() {
        try { selector.select(); } catch (IOException ignored) {}

        final ByteBuffer buffer = ByteBuffer.allocate(16384);
        Request request = null;

        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                try {//вот сюда можно пихнуть для галочки

                    ((SocketChannel)key.channel()).read(buffer);
                    request = (Request) ObjectSerialization.deserialize(buffer);

                    keyIterator.remove();
                    break;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    try {
                        key.channel().close();
                        ((SocketChannel) key.channel()).socket().close();
                    } catch (IOException ignored) {}
                }
            }
        }

        return request;
    }
}
