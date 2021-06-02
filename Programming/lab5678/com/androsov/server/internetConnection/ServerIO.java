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
        try { selector.select(); } catch (IOException ignored) {}

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

    //TODO отправка должна быть конкретному юзеру (создается пул из респонсов, которые содержат адрес юзера, когда можно, респонс отправляется)
    //отправляем байты нужному юзеру
    @Override
    public void send(ByteBuffer buffer) {
        try { selector.select(); } catch (IOException ignored) {}
        final Set<SelectionKey> keys = selector.selectedKeys();
        final Iterator<SelectionKey> keyIterator = keys.iterator();

        for(int i = 0; i < keys.size(); i++) {
            final SelectionKey key = keyIterator.next();
            try {
                if (key.isWritable() && ((SocketChannel) key.channel()).getRemoteAddress().equals(currentUser.getUserAddress())) {
                    ((SocketChannel)key.channel()).write(buffer);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                try {
                    key.channel().close();
                    ((SocketChannel) key.channel()).socket().close();
                } catch (IOException ignored) { }
            }
            keyIterator.remove();
        }
    }

    @Override
    public ByteBuffer get() {
        try { selector.select(); } catch (IOException ignored) {}

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
                    } catch (IOException ignored) {}
                }
            }
            keyIterator.remove();
        }

        return buffer;
    }
}
