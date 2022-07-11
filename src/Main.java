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
            FileChannel raChanel = ra.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(100);
            byte[] msg = "Hello World!".getBytes();
            buffer.put(msg);
            buffer.putInt(213);
            buffer.putInt(-21321);
            byte[] msg2 = "Nice to meet you!".getBytes();
            buffer.put(msg2);
            buffer.flip();
            binChanel.write(buffer);

            ByteBuffer readBuffer = ByteBuffer.allocate(100);
            raChanel.read(readBuffer);
            readBuffer.flip();
            byte[] inputString = new byte[msg.length];
            readBuffer.get(inputString);
            System.out.println("inputString = " + new String(inputString));
            System.out.println("int1 = " + readBuffer.getInt());
            System.out.println("int2 = " + readBuffer.getInt());
            byte[] inputString2 = new byte[msg2.length];
            readBuffer.get(inputString2);
            System.out.println("inputString2 = " + new String(inputString2));

//            byte[] msg = "Hello World!".getBytes();
//            ByteBuffer buffer = ByteBuffer.allocate(msg.length); //when we using wrap we dont have to put anything to buffer because it is doing for us and flips buffer to position 0 after all,
//                                                                //so when we use alocate instead we have to flip buffer
//            buffer.put(msg);
//            buffer.flip();
//            int numBytes = binChanel.write(buffer);
//            System.out.println("numBytes = " + numBytes);
//
//            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
//            intBuffer.putInt(245213);
//            intBuffer.flip();
//            numBytes = binChanel.write(intBuffer);
//            System.out.println("numBytes = " + numBytes);
//
//            intBuffer.flip();
//            intBuffer.putInt(-83213621);
//            intBuffer.flip();
//            binChanel.write(intBuffer);
//            System.out.println("numBytes = " + numBytes);
//
//            //must be when switching from writing to reading and vice versa
//            buffer.flip();
//            long numBytesRead = chanel.read(buffer);
//            if(buffer.hasArray()) {
//                System.out.println("byte buffer = " + new String(buffer.array()));
//            }
//            //Absolute read
//            intBuffer.flip();
//            numBytesRead = chanel.read(intBuffer);
//            System.out.println(intBuffer.getInt(0));
//            intBuffer.flip();
//            numBytesRead = chanel.read(intBuffer);
//            System.out.println(intBuffer.getInt(0));

            //Relative read
//            intBuffer.flip();
//            numBytesRead = chanel.read(intBuffer);
//            intBuffer.flip();
//            System.out.println(intBuffer.getInt());
//            intBuffer.flip();
//            numBytesRead = chanel.read(intBuffer);
//            intBuffer.flip();
//            System.out.println(intBuffer.getInt());


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