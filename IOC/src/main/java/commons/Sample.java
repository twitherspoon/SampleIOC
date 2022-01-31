package commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Sample {
	public static void main(String[] args) throws ParseException {


	/*	String fiscalDate = "12/15/2021";
		String[] arr1 = fiscalDate.split("/");
		String dayToday = arr1[1];
		String monthToday = arr1[0];
		String yearToday = arr1[2];


		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		Date todaysDate = new SimpleDateFormat("yyyy/MM/dd").parse(dtf.format(now));
 

		SimpleDateFormat sdfSource = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdfSource.parse(fiscalDate);

		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate today = todaysDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		long days = ChronoUnit.DAYS.between(today, localDate);
		String year = null;
		String dateOfExp = null;

		if(days<=90 && days>0) {
			year = Integer.toString(Integer.parseInt(yearToday)+1);
			System.out.println(dayToday+"/"+monthToday+"/"+year);
			dateOfExp = addingDaysFromToday(new SimpleDateFormat("MM/dd/yyyy").parse(dayToday+"/"+monthToday+"/"+year),90);
			System.out.println(dateOfExp);
		}

		else {
			dateOfExp = addingDaysFromToday(new SimpleDateFormat("MM/dd/yyyy").parse(fiscalDate),90);
			System.out.println(dateOfExp);
		}
	*/
		String parentAns = "12/12/2025";
		DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = originalFormat.parse(parentAns);
		parentAns = targetFormat.format(date); 
		System.out.println(parentAns);
		
		System.out.println("Any \\\"yes\\\" answers on the application");
	}

	public static String addingDaysFromToday(Date d, int daysToAdd) {
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(d); 
		c.add(Calendar.DATE, daysToAdd);
		dt = c.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String date = simpleDateFormat.format(dt);
		return date;
	}
}