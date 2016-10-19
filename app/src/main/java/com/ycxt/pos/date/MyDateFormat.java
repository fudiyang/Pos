package com.ycxt.pos.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat {
	public String simpleDateFormat(int sort, Date date){
		switch (sort) {
		case 0:
			SimpleDateFormat myFmt0=new SimpleDateFormat("yyyyMMddHHmmss");
			return myFmt0.format(date);
		case 1:
			SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMdd");
			return myFmt1.format(date);
		case 2:
			SimpleDateFormat myFmt2=new SimpleDateFormat("yyMMddHHmmss");
			return myFmt2.format(date);
		case 3:
			SimpleDateFormat myFmt3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return myFmt3.format(date);
		case 4:
			SimpleDateFormat myFmt4=new SimpleDateFormat("yyMMddHHmm");
			return myFmt4.format(date);
		default:
			return null;
		}
	}
	
	public Date simpleStringtoDate(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
