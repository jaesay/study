package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class DataInputOutputStreamTests {

	public static void main(String[] args) {
		test();
		//testDataOutputStream();
		//testDataInputStream();
	}

	private static void test() {
		byte[] arr = { 0x00, 0x01, 0x01, 0x00, 0x01 };
		ByteBuffer wrapped = ByteBuffer.wrap(arr); // big-endian by default
		System.out.println(Arrays.toString(wrapped.array()));
		/*short num = wrapped.getShort(); // 1

		ByteBuffer dbuf = ByteBuffer.allocate(2);
		dbuf.putShort(num);
		byte[] bytes = dbuf.array(); // { 0, 1 }
*/		
		
		
	}

	private static void testDataOutputStream() {
		FileOutputStream fos = null;
	    DataOutputStream dos = null;

	    try {
	      fos = new FileOutputStream("data.bin");
	      dos = new DataOutputStream(fos);
	   
	      dos.writeBoolean(true);
	      dos.write(5);
	      dos.writeByte((byte)5);
	      dos.writeInt(100);
	      dos.writeDouble(200.5);
	      dos.writeUTF("Hi., Santaclaus.AlexanderII.Ahn...");
	   
	      System.out.println("저장하였습니다.");
	    } catch(Exception ex) {
	      System.out.println(ex);
	    } finally {
	      try {
	        fos.close();
	      } catch (IOException e) {}
	      try {
	        dos.close();
	      } catch (IOException e) {}
	    }
	}
	

	private static void testDataInputStream() {
		FileInputStream fis = null;
	    DataInputStream dis = null;

	    try {
	      fis = new FileInputStream("data.bin");
	      dis = new DataInputStream(fis);
	   
	      boolean b  = dis.readBoolean();
	      int     b2 = dis.read();
	      byte    b3 = dis.readByte();
	      int     i  = dis.readInt();
	      double  d  = dis.readDouble();
	      String  s  = dis.readUTF();
	   
	      System.out.println("boolean: " + b);
	      System.out.println("int    : " + b2);
	      System.out.println("byte   : " + b3);
	      System.out.println("int    : " + i);
	      System.out.println("double : " + d);
	      System.out.println("String : " + s);
	    } catch(Exception ex) {
	      System.out.println(ex);
	    } finally {
	      try {
	        fis.close();
	      } catch (IOException e) {}
	      try {
	        dis.close();
	      } catch (IOException e) {}
	    }
	}
}
