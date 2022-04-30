package study;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteBufferTests {

	public static void main(String[] args) {
		test1();
	}

	//ByteBuffer ��� �׽�Ʈ
	private static void test1() {
		
		// Create a byte array
		byte[] bytes = {48, 49, 50, 51, 52};
		 
		// Wrap a byte array into a buffer
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		System.out.println("wrap �׽�Ʈ :" + new String(bytes));
		
		// Retrieve bytes between the position and limit
		// (see Putting Bytes into a ByteBuffer)
		bytes = new byte[buf.remaining()];
		System.out.println("remainging �׽�Ʈ :" + buf.remaining());
		System.out.println("remainging �׽�Ʈ :" + bytes.length);

		// transfer bytes from this buffer into the given destination array
		buf.get(bytes, 0, bytes.length);
		System.out.println("get �׽�Ʈ : " + new String(bytes));
		
		// Retrieve all bytes in the buffer
		buf.clear();
		bytes = new byte[buf.capacity()];
		System.out.println("clear �׽�Ʈ: " + buf.capacity());
		 
		// transfer bytes from this buffer into the given destination array
		buf.get(bytes, 0, bytes.length);
		System.out.println("get �׽�Ʈ : " + new String(bytes));
		
		
		bytes = new byte[30];
		ByteBuffer byteBuffer = ByteBuffer.allocate(30);
		String str = "0123456789";
		String str1 = "aaaaaaaaaa";
		String str2 = "bbbbbbbbbb";
		
		byteBuffer.put(str.getBytes())
				  .put(str1.getBytes())
				  .put(str2.getBytes());
		byteBuffer.flip(); //�������� 0 ���� �����ϰ�, ����Ʈ�� ���� ������ ������ ��ġ�� �����ŵ�ϴ�.
		
		String result = new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
		
		System.out.println("remainging �׽�Ʈ :" + byteBuffer.remaining());
		System.out.println("put �׽�Ʈ : " + result);
		
		byteBuffer.clear();
		String result1 = new String(byteBuffer.array(), 0, 10);
		System.out.println("�׽�Ʈ1 : " + result1);
		String result2 = new String(byteBuffer.array(), 10, 10);
		System.out.println("�׽�Ʈ1 : " + result2);
		String result3 = new String(byteBuffer.array(), 20, 10);
		System.out.println("�׽�Ʈ1 : " + result3);
		System.out.println("�׽�Ʈ2 : " + Arrays.toString(byteBuffer.array()));
		
		
		
	}
}
