package study;

import java.nio.ByteBuffer;
import java.util.Arrays;

import protocol.enums.Header;

public class ByteToIntegerTests {

	public static void main(String[] args) {
		new ByteToIntegerTests().test();
	}
	
	private void test() {
		/*byte[] bytes = ByteBuffer.allocate(4).putInt(1000).array();

		for (byte b : bytes) {
		   System.out.format("0x%x ", b);
		}*/
		
		/*byte[] bytes = new byte[14];
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(14);
		byteBuffer.putInt(1)
				  .putInt(2)
				  .putInt(1000)
				  .putShort((short) 1);
		
		System.out.println(Arrays.toString(byteBuffer.array()));*/
		
byte[] bytes = new byte[14];
		
		ByteBuffer HeaderByteBuffer = ByteBuffer.allocate(14);
		HeaderByteBuffer.putInt(10)
				  .putInt(2)
				  .putInt(1000)
				  .putShort((short) 1);
		//System.out.println("header byte buffer: " + Arrays.toString(HeaderByteBuffer.array()));
		
		// String => byte array
		byte[] bytebody = ("aaaa" + "bbbbb").getBytes();
		System.out.println( Arrays.toString(HeaderByteBuffer.array()));
		
		ByteBuffer packetByteBuffer = ByteBuffer.allocate(Header.HEADER.getLength() + bytebody.length);
		/*byte[] byteArr = new byte[packetByteBuffer.remaining()];
		packetByteBuffer.get(byteArr);
		System.out.println(new String(byteArr));*/
		packetByteBuffer
						.put(HeaderByteBuffer.array())
						.put(bytebody);
		
		System.out.println("packet byte buffer: " + Arrays.toString(packetByteBuffer.array()));
		
		
		
		
		
	}
}
