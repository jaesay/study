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
		String serverDirPath = System.getProperty("user.dir"); // 현재 작업하고 있는 경로
		String fileName = UUID.randomUUID().toString() + new SimpleDateFormat("yyyyMMddHHmmssFFF'.txt'").format(new Date()); // UUID + 현재 시간으로 파일이름 생성하기
		
		String fullPath = serverDirPath + File.separator + "data" + File.separator + "temp" + File.separator + fileName; // 전체 경로
		
		String fileDirPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator)); // 폴더만 추출

		String ext = fullPath.substring(fullPath.lastIndexOf(".") + 1); // 확장자 추출하기

		System.out.println(fullPath); //경로확인
		System.out.println(ext); //확장자확인
		System.out.println(fileDirPath); //폴더확인

		System.out.println(serverDirPath);

		// 폴더생성
		File dir = new File(fileDirPath);
		if (!dir.exists()) // 폴더가 없으면 생성
			dir.mkdirs();

		File file = new File(fullPath);
		
		String newLine = System.getProperty( "line.separator");
		if(resultList.size() > 0) {
			FileWriter fileWriter = new FileWriter(file);
			Map<String, String> resultMap = resultList.get(0);
			
			fileWriter.write("티켓" + newLine);
			fileWriter.write("주문번호: " + resultMap.get("orderId") + newLine);
			fileWriter.write("가격: " + resultMap.get("totalPrice") + newLine);
			fileWriter.write("결제일: " + resultMap.get("orderDate") + newLine);
			fileWriter.write("영화: " + resultMap.get("title") + newLine);
			fileWriter.write("상영일: " + resultMap.get("scheduleDate") + newLine);
			fileWriter.write("상영관: 상영관" + resultMap.get("theaterId") + "(" + resultMap.get("floor") + "층)" + newLine);
			fileWriter.write("좌석번호: ");
			
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
