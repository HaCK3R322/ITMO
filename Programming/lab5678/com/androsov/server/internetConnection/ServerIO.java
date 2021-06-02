package com.androsov.server.internetConnection;

import com.androsov.general.IO.IO;
import com.androsov.general.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerIO implements IO {
    private User currentUser;

    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public ServerIO() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 25565));
        serverSocketChannel.configureBlocking(false);

        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    //выбрали какой-то канал
    public void select() throws IOException {
        selector.select();
    }

    //смотрим, если он хочет зарегистрироваться, регистрируем
    public void acceptAll() throws IOException {
        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            if (key.isAcceptable()) {
                final SocketChannel client = serverSocketChannel.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                System.out.println("Connected client!");
                keyIterator.remove();
            }
        }

//        while (keyIterator.hasNext()) {
//            final SelectionKey key = keyIterator.next();
//            if (key.isAcceptable()) {
//                final SocketChannel client = serverSocketChannel.accept();
//                client.configureBlocking(false);
//                client.register(selector, SelectionKey.OP_READ);
//                System.out.println("Connected client!");
//                keyIterator.remove();
//                break;
//            }
//        }
    }

    //если он что-то записал, т.е. кей стал Реадабл, то возвращаем труе
    public boolean hasRequest() {

        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                return true;
            }
        }

        return false;
    }

    public void setUser(User user) {
        currentUser = user;
    }

    //TODO send get must work with channels

    //отправляем байты нужному юзеру
    @Override
    public void send(ByteBuffer buffer) {
        try {
            selector.select();
        } catch (IOException e) {

        }
        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            if (key.isWritable()) {
                try {
                    ((SocketChannel)key.channel()).write(buffer);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    try {
                        key.channel().close();
                        ((SocketChannel) key.channel()).socket().close();
                    } catch (IOException ignored) { }
                }
            }
            keyIterator.remove();
        }
    }

    @Override
    public ByteBuffer get() {
        final ByteBuffer buffer = ByteBuffer.allocate(16384);

        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            if (key.isReadable()) {
                try {
                    ((SocketChannel)key.channel()).read(buffer);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    try {
                        key.channel().close();
                        ((SocketChannel) key.channel()).socket().close();
                    } catch (IOException ignored) { }
                }
            }
            keyIterator.remove();
        }

        return buffer;
    }
}
