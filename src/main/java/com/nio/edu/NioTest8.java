package com.nio.edu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest8 {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannle = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(512);
        while (true) {
            buffer.clear();

            int read = inputChannel.read(buffer);

            if (-1 == read) {
                break;
            }

            buffer.flip();

            outputChannle.write(buffer);
        }
        inputChannel.close();
        outputChannle.close();
    }

}
