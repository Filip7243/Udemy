import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) {

        try(FileOutputStream binFile = new FileOutputStream("data.dat");
            FileChannel binChanel = binFile.getChannel();
            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
            FileChannel chanel = ra.getChannel()) {

            byte[] msg = "Hello World!".getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(msg);
            int numBytes = binChanel.write(buffer);
            System.out.println("numBytes = " + numBytes);

            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
            intBuffer.putInt(245213);
            intBuffer.flip();
            numBytes = binChanel.write(intBuffer);
            System.out.println("numBytes = " + numBytes);

            intBuffer.flip();
            intBuffer.putInt(-83213621);
            intBuffer.flip();
            binChanel.write(intBuffer);
            System.out.println("numBytes = " + numBytes);

            //must be when switching from writing to reading and vice versa
            buffer.flip();
            long numBytesRead = chanel.read(buffer);

            System.out.println("outputBytes = " + new String(msg));

//            byte[] b = new byte[msg.length];
//            ra.read(b);
//            System.out.println(new String(b));
//
//            long int1 = ra.readInt();
//            long int2 = ra.readInt();
//            System.out.println(int2);
//            System.out.println(int1);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}