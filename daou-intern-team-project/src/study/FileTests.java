package study;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileTests {

	public static void main(String[] args) {

		// getProperty("user.dir") <- 현재 작업하고 있는 경로
		String serverDirPath = System.getProperty("user.dir");
		String fileName = UUID.randomUUID().toString() + new SimpleDateFormat("yyyyMMddHHmmssFFF'.txt'").format(new Date()); // UUID + 현재 시간으로 파일이름 생성하기
		// 경로
		String fullPath = serverDirPath + File.separator + "data" + File.separator + "temp" + File.separator + fileName;
		// 폴더만 추출
		String fileDirPath = fullPath.substring(0, fullPath.lastIndexOf(File.separator));

		// 확장자 추출하기
		String ext = fullPath.substring(fullPath.lastIndexOf(".") + 1);

		System.out.println(fullPath); //경로확인
		System.out.println(ext); //확장자확인
		System.out.println(fileDirPath); //폴더확인

		System.out.println(serverDirPath);
		// 폴더생성
		// File(String pathname) 주어진 경로명을 추상 경로명으로 변환하여 새로운 File 객체를 생성한다
		File dir = new File(fileDirPath);
		if (!dir.exists()) // 폴더가 없으면 생성
			dir.mkdirs();

		File file = new File(fullPath);
		if (file.exists()) {
			System.out.println("파일이 존재..."); // 파일이 존재하면 종료
			return;
		}
		try {
			/*FileOutputStream fos = new FileOutputStream(file);
			// 객체생성방법 1.절대경로넘기기(fullpath) 2.파일객체넘기기 (f1)
			fos.write(65);
			fos.write(66);
			fos.write(67);
			fos.write(68);

			fos.close();*/
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write("티켓");
			fileWriter.write(System.getProperty( "line.separator")); // 새줄 삽입
			fileWriter.write("주문번호: 1");
			fileWriter.write(System.getProperty( "line.separator"));
			fileWriter.write("가격: 32000");
			fileWriter.write(System.getProperty( "line.separator"));
			fileWriter.close();

			System.out.println("파일생성.....");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void buildFile() {
		try {
			
			String dirPath = System.getProperty("user.dir"); // 현재 작업하고 있는 경로
			System.out.println("현재 작업하고 있는 경로: " + dirPath);
			
			String fileName = UUID.randomUUID().toString() + new SimpleDateFormat("yyyyMMddHHmmssFFF'.txt'").format(new Date()); // UUID + 현재 시간으로 파일이름 생성하기
			System.out.println("유일한 파일 이름 : " + fileName);
			
			
			File directory = new File(dirPath + File.separator + "test-foler");
			
			
			
			FileWriter fileWriter = new FileWriter("Foo.txt");
			String newLine = System.getProperty( "line.separator");
			fileWriter.write("티켓" + newLine);
			fileWriter.write("주문번호: 1" + newLine);
			fileWriter.write("가격: 32000");
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
