import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;

public class Main {
    public static void main(String[] args) {

        try {
            Pipe pipe = Pipe.open();
            Runnable writer = new Runnable() {
                @Override
                public void run() {
                    try {
                        Pipe.SinkChannel sinkChannel = pipe.sink();
                        ByteBuffer writeBuffer = ByteBuffer.allocate(56);

                        for (int i = 0; i < 10; i++) {
                            String currentTime = "The time now is: " + System.currentTimeMillis();

                            writeBuffer.put(currentTime.getBytes());
                            writeBuffer.flip();

                            while (writeBuffer.hasRemaining()) {
                                sinkChannel.write(writeBuffer);
                            }
                            writeBuffer.flip();
                            Thread.sleep(100);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            Runnable reader = new Runnable() {
                @Override
                public void run() {
                    try {
                        ByteBuffer readBuffer = ByteBuffer.allocate(56);
                        Pipe.SourceChannel sourceChannel = pipe.source();

                        for (int i = 0; i < 10; i++) {
                            int bytesRead = sourceChannel.read(readBuffer);
                            byte[] timeRead = new byte[bytesRead];
                            readBuffer.flip();
                            readBuffer.get(timeRead);
                            System.out.println("Thread Reader: " + new String(timeRead));
                            readBuffer.flip();
                            Thread.sleep(100);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            new Thread(writer).start();
            new Thread(reader).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

 /*       try(FileOutputStream binFile = new FileOutputStream("data.dat");
            FileChannel binChanel = binFile.getChannel();
            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
            FileChannel raChanel = ra.getChannel();
            RandomAccessFile raCopy = new RandomAccessFile("datacopy.dat", "rw");
            FileChannel copyChanel = raCopy.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(100);
            byte[] msg = "Hello World!".getBytes();
            buffer.put(msg);
            long int1pos = msg.length;
            buffer.putInt(213);
            long int2pos = int1pos + Integer.BYTES;
            buffer.putInt(-21321);
            byte[] msg2 = "Nice to meet you!".getBytes();
            buffer.put(msg2);
            long int3pos = int2pos + Integer.BYTES + msg2.length;
            buffer.putInt(1000);
            buffer.flip();
            binChanel.write(buffer);

            ByteBuffer readBuffer = ByteBuffer.allocate(Integer.BYTES);
            raChanel.position(int3pos);
            raChanel.read(readBuffer); // at this time in buffer is only an int3
            readBuffer.flip();
            System.out.println("int3 = " + readBuffer.getInt());

            readBuffer.flip();
            raChanel.position(int2pos);
            raChanel.read(readBuffer);
            readBuffer.flip();
            System.out.println("int2 = " + readBuffer.getInt());

            readBuffer.flip();
            raChanel.position(int1pos);
            raChanel.read(readBuffer);
            readBuffer.flip();
            System.out.println("int1 = " + readBuffer.getInt());

            raChanel.position(0);
            long numTransferred = copyChanel.transferFrom(raChanel, 0, raChanel.size());
            System.out.println("numTransferred = " + numTransferred);

//            byte[] outputString = "Hello World!".getBytes();
//            long str1pos = 0;
//            long newInt1pos = outputString.length;
//            long newInt2pos = newInt1pos + Integer.BYTES;
//            byte[] outputString2 = "Nice to meet you!".getBytes();
//            long str2pos = newInt2pos + Integer.BYTES;
//            long newInt3pos = str2pos + outputString2.length;
//
//            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
//
//            intBuffer.putInt(245);
//            intBuffer.flip();
//            binChanel.position(newInt1pos);
//            binChanel.write(intBuffer);
//
//
//            intBuffer.flip();
//            intBuffer.putInt(-321321);
//            intBuffer.flip();
//            binChanel.position(newInt2pos);
//            binChanel.write(intBuffer);
//
//            intBuffer.flip();
//            intBuffer.putInt(1000);
//            intBuffer.flip();
//            binChanel.position(newInt3pos);
//            binChanel.write(intBuffer);
//
//            binChanel.position(str1pos);
//            binChanel.write(ByteBuffer.wrap(outputString));
//
//            binChanel.position(str2pos);
//            binChanel.write(ByteBuffer.wrap(outputString2));






            *//*ByteBuffer readBuffer = ByteBuffer.allocate(100);
            raChanel.read(readBuffer);
            readBuffer.flip();
            byte[] inputString = new byte[msg.length];
            readBuffer.get(inputString);
            System.out.println("inputString = " + new String(inputString));
            System.out.println("int1 = " + readBuffer.getInt());
            System.out.println("int2 = " + readBuffer.getInt());
            byte[] inputString2 = new byte[msg2.length];
            readBuffer.get(inputString2);
            System.out.println("inputString2 = " + new String(inputString2));*//*

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
        }*/
    }
}