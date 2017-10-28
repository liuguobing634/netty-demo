package nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by 刘国兵 on 2017/7/8.
 */
public class BufferTest {

    public static void main(String[] args) throws IOException {
        String content = "1234567890";
        InputStream is = new ByteArrayInputStream(content.getBytes());
        ReadableByteChannel channel = Channels.newChannel(is);
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer);
        byteBuffer.flip();
        // byteBuffer
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WritableByteChannel channel1 = Channels.newChannel(os);
        channel1.write(byteBuffer);
        byteBuffer.clear();
        System.out.println(os.toString());
    }

}
