package test;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class abc {

	public static void main(String[] args) throws ParseException {
		
		
		String dob="1999-12-31";
		
		Date date= new Date();
		
		
		
		Calendar cal= Calendar.getInstance();
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(sdf.parse(dob));
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		
		
		System.out.println(year);
		
		
		
		
		
	    
	   
		
		
		
		
		
		
		
		
		
		

	}

}
