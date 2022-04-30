package study;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileTests {

	public static void main(String[] args) {

		// getProperty("user.dir") <- ���� �۾��ϰ� �ִ� ���
		String serverDirPath = System.getProperty("user.dir");
		String fileName = UUID.randomUUID().toString() + new SimpleDateFormat("yyyyMMddHHmmssFFF'.txt'").format(new Date()); // UUID + ���� �ð����� �����̸� �����ϱ�
		// ���
		String fullPath = serverDirPath + File.separator + "data" + File.separator + "temp" + File.separator + fileName;
		// ������ ����
		String fileDirPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator));

		// Ȯ���� �����ϱ�
		String ext = fullPath.substring(fullPath.lastIndexOf(".") + 1);

		System.out.println(fullPath); //���Ȯ��
		System.out.println(ext); //Ȯ����Ȯ��
		System.out.println(fileDirPath); //����Ȯ��

		System.out.println(serverDirPath);
		// ��������
		// File(String pathname) �־��� ��θ��� �߻� ��θ����� ��ȯ�Ͽ� ���ο� File ��ü�� �����Ѵ�
		File dir = new File(fileDirPath);
		if (!dir.exists()) // ������ ������ ����
			dir.mkdirs();

		File file = new File(fullPath);
		if (file.exists()) {
			System.out.println("������ ����..."); // ������ �����ϸ� ����
			return;
		}
		try {
			/*FileOutputStream fos = new FileOutputStream(file);
			// ��ü������� 1.�����γѱ��(fullpath) 2.���ϰ�ü�ѱ�� (f1)
			fos.write(65);
			fos.write(66);
			fos.write(67);
			fos.write(68);

			fos.close();*/
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write("Ƽ��");
			fileWriter.write(System.getProperty( "line.separator")); // ���� ����
			fileWriter.write("�ֹ���ȣ: 1");
			fileWriter.write(System.getProperty( "line.separator"));
			fileWriter.write("����: 32000");
			fileWriter.write(System.getProperty( "line.separator"));
			fileWriter.close();

			System.out.println("���ϻ���.....");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void buildFile() {
		try {
			
			String dirPath = System.getProperty("user.dir"); // ���� �۾��ϰ� �ִ� ���
			System.out.println("���� �۾��ϰ� �ִ� ���: " + dirPath);
			
			String fileName = UUID.randomUUID().toString() + new SimpleDateFormat("yyyyMMddHHmmssFFF'.txt'").format(new Date()); // UUID + ���� �ð����� �����̸� �����ϱ�
			System.out.println("������ ���� �̸� : " + fileName);
			
			
			File directory = new File(dirPath + File.separator + "test-foler");
			
			
			
			FileWriter fileWriter = new FileWriter("Foo.txt");
			String newLine = System.getProperty( "line.separator");
			fileWriter.write("Ƽ��" + newLine);
			fileWriter.write("�ֹ���ȣ: 1" + newLine);
			fileWriter.write("����: 32000");
			fileWriter.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void removeFile(File file) {
		if(file.exists()) {
			file.delete();
		}
	}
}
