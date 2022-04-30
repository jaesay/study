package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

public class FileBuilder {
	
	public String createFile(List<Map<String, String>> resultList) throws IOException {

		System.out.println("resultList.size(): " + resultList.size());
		String serverDirPath = System.getProperty("user.dir"); // ���� �۾��ϰ� �ִ� ���
		String fileName = UUID.randomUUID().toString() + new SimpleDateFormat("yyyyMMddHHmmssFFF'.txt'").format(new Date()); // UUID + ���� �ð����� �����̸� �����ϱ�
		
		String fullPath = serverDirPath + File.separator + "data" + File.separator + "temp" + File.separator + fileName; // ��ü ���
		
		String fileDirPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator)); // ������ ����

		String ext = fullPath.substring(fullPath.lastIndexOf(".") + 1); // Ȯ���� �����ϱ�

		System.out.println(fullPath); //���Ȯ��
		System.out.println(ext); //Ȯ����Ȯ��
		System.out.println(fileDirPath); //����Ȯ��

		System.out.println(serverDirPath);

		// ��������
		File dir = new File(fileDirPath);
		if (!dir.exists()) // ������ ������ ����
			dir.mkdirs();

		File file = new File(fullPath);
		
		String newLine = System.getProperty( "line.separator");
		if(resultList.size() > 0) {
			FileWriter fileWriter = new FileWriter(file);
			Map<String, String> resultMap = resultList.get(0);
			
			fileWriter.write("Ƽ��" + newLine);
			fileWriter.write("�ֹ���ȣ: " + resultMap.get("orderId") + newLine);
			fileWriter.write("����: " + resultMap.get("totalPrice") + newLine);
			fileWriter.write("������: " + resultMap.get("orderDate") + newLine);
			fileWriter.write("��ȭ: " + resultMap.get("title") + newLine);
			fileWriter.write("����: " + resultMap.get("scheduleDate") + newLine);
			fileWriter.write("�󿵰�: �󿵰�" + resultMap.get("theaterId") + "(" + resultMap.get("floor") + "��)" + newLine);
			fileWriter.write("�¼���ȣ: ");
			
			StringJoiner stringJoiner = new StringJoiner(",");
			for(Map<String, String> map : resultList) {
				stringJoiner.add(map.get("seatId"));
			}
			fileWriter.write(stringJoiner.toString());
			fileWriter.close();
		}

		return fullPath;
	}

	
	public byte[] convertToBytes(String fullPath) throws IOException {
		File file = new File(fullPath);

		byte[] bytes = new byte[(int) file.length()];
		
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();
		
		return bytes;
	}
	
	
	public void deleteFile(String fullPath) {

		File file = new File(fullPath);
		
		if(file.exists()) {
			file.delete();
		}
	}
	
	
}
