package study;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteBufferTests {

	public static void main(String[] args) {
		test1();
	}

	//ByteBuffer 사용 테스트
	private static void test1() {
		
		// Create a byte array
		byte[] bytes = {48, 49, 50, 51, 52};
		 
		// Wrap a byte array into a buffer
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		System.out.println("wrap 테스트 :" + new String(bytes));
		
		// Retrieve bytes between the position and limit
		// (see Putting Bytes into a ByteBuffer)
		bytes = new byte[buf.remaining()];
		System.out.println("remainging 테스트 :" + buf.remaining());
		System.out.println("remainging 테스트 :" + bytes.length);

		// transfer bytes from this buffer into the given destination array
		buf.get(bytes, 0, bytes.length);
		System.out.println("get 테스트 : " + new String(bytes));
		
		// Retrieve all bytes in the buffer
		buf.clear();
		bytes = new byte[buf.capacity()];
		System.out.println("clear 테스트: " + buf.capacity());
		 
		// transfer bytes from this buffer into the given destination array
		buf.get(bytes, 0, bytes.length);
		System.out.println("get 테스트 : " + new String(bytes));
		
		
		bytes = new byte[30];
		ByteBuffer byteBuffer = ByteBuffer.allocate(30);
		String str = "0123456789";
		String str1 = "aaaaaaaaaa";
		String str2 = "bbbbbbbbbb";
		
		byteBuffer.put(str.getBytes())
				  .put(str1.getBytes())
				  .put(str2.getBytes());
		byteBuffer.flip(); //포지션을 0 으로 설정하고, 리미트를 현재 내용의 마지막 위치로 압축시킵니다.
		
		String result = new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
		
		System.out.println("remainging 테스트 :" + byteBuffer.remaining());
		System.out.println("put 테스트 : " + result);
		
		byteBuffer.clear();
		String result1 = new String(byteBuffer.array(), 0, 10);
		System.out.println("테스트1 : " + result1);
		String result2 = new String(byteBuffer.array(), 10, 10);
		System.out.println("테스트1 : " + result2);
		String result3 = new String(byteBuffer.array(), 20, 10);
		System.out.println("테스트1 : " + result3);
		System.out.println("테스트2 : " + Arrays.toString(byteBuffer.array()));
		
		
		
	}
}
