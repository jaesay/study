package study;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class ReservationTests {

	public static void main(String[] args) {
		new ReservationTests().test();
	}

	private void test() {
		
		try {
			LocalDate myDate = LocalDate.of(2019, 1, 25);
			System.out.println(String.valueOf(myDate));

			LocalTime myTime = LocalTime.of(13, 30);
			System.out.println(String.valueOf(myTime));
			
		    String string1 = "20:11:13";
		    Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
		    Calendar calendar1 = Calendar.getInstance();
		    calendar1.setTime(time1);

		    String string2 = "14:49:00";
		    Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.setTime(time2);
//		    calendar2.add(Calendar.DATE, 1);

		    String someRandomTime = "01:00:00";
		    Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
		    Calendar calendar3 = Calendar.getInstance();
		    calendar3.setTime(d);
//		    calendar3.add(Calendar.DATE, 1);
		    
		    String someTime = "24:00:00";
		    Date time4 = new SimpleDateFormat("HH:mm:ss").parse(someTime);
		    Calendar calendar4 = Calendar.getInstance();
		    calendar4.setTime(time4);
		    
		    
		    System.out.println(toString(calendar1));
		    System.out.println(toString(calendar2));
		    System.out.println(toString(calendar3));
		    System.out.println(calendar3.getTime());
		    System.out.println("calendar4.getTime(): " + calendar4.getTime());
		    
		    // 1:00:00 < 14:49:00 < 20:11:13
		    // z < y < x
		    Date x = calendar1.getTime();
		    Date y = calendar2.getTime();
		    Date z = calendar3.getTime();
		    Date a = calendar4.getTime();
		    
		    if (y.after(z) && y.before(x)) {
		        //checkes whether the current time is between 14:49:00 and 20:11:13.
		        System.out.println(true);
		    }
		    
		    
		    
		    // 20:11:13 < 01:00:00 < 14:49:00
		    /*Date x = calendar3.getTime();
		    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
		        //checkes whether the current time is between 14:49:00 and 20:11:13.
		        System.out.println(true);
		    }*/
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		
	}
	
	
	
	public static String toString(Calendar date) {
        return date.get(Calendar.YEAR)+"년 "
                + (date.get(Calendar.MONTH)+1) + "월 "
                + date.get(Calendar.DATE) + "일 "
                + date.get(Calendar.HOUR) + "시 "
                + date.get(Calendar.MINUTE) + "분 "
                + + date.get(Calendar.SECOND) + "초";
    }


}
