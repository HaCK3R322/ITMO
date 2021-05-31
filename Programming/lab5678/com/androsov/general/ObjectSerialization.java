package com.androsov.general;
import java.io.*;
import java.nio.ByteBuffer;

public class ObjectSerialization {
    public static ByteBuffer serialize(Object object) throws IOException {

        final ByteBuffer buffer;

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        }

        return buffer;
    }

    public static Object deserialize(ByteBuffer buffer) throws IOException {
        Object object = null; //может быть поправить, может нет

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()))
        ) {
            try {
                object = objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                //ignore
            }
        }

        return object;
    }
}
