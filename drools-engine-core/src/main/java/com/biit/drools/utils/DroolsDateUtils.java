package com.biit.drools.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.biit.drools.logger.DroolsEngineLogger;

public class DroolsDateUtils {

	private static final String DATE_FORMAT = "yyyy-mm-dd";

	public static Date returnCurrentDateMinusYears(int years) {
		Calendar now = Calendar.getInstance();
		// Substract the years from the calendar
		now.add(Calendar.YEAR, -years);
		return now.getTime();
	}

	public static Date returnCurrentDateMinusMonths(int months) {
		Calendar now = Calendar.getInstance();
		// Substract the months from the calendar
		now.add(Calendar.MONTH, -months);
		return now.getTime();
	}

	public static Date returnCurrentDateMinusDays(int days) {
		Calendar now = Calendar.getInstance();
		// Substract the days from the calendar
		now.add(Calendar.DATE, -days);
		return now.getTime();
	}

	public static Integer returnYearsDistanceFromDate(Object object) {
		if (object instanceof Date) {
			Calendar now = Calendar.getInstance();
			return Math.abs(returnYearsDistanceFromDate((Date) object, now));
		}
		return null;
	}

	private static Integer returnYearsDistanceFromDate(Date date, Calendar comparation) {
		Calendar compareDate = Calendar.getInstance();
		compareDate.setTime(date);
		return comparation.get(Calendar.YEAR) - compareDate.get(Calendar.YEAR);
	}

	public static Integer returnMonthsDistanceFromDate(Object object) {
		if (object instanceof Date) {
			Calendar now = Calendar.getInstance();
			return Math.abs(returnMonthsDistanceFromDate((Date) object, now));
		}
		return null;
	}

	private static Integer returnMonthsDistanceFromDate(Date date, Calendar comparation) {
		Calendar compareDate = Calendar.getInstance();
		compareDate.setTime(date);
		return ((returnYearsDistanceFromDate(date, comparation) * 12) + compareDate.get(Calendar.MONTH)) - comparation.get(Calendar.MONTH);
	}

	public static Integer returnDaysDistanceFromDate(Object object) {
		if (object instanceof Date) {
			Calendar now = Calendar.getInstance();
			return returnDaysDistanceFromDate((Date) object, now);
		}
		return null;
	}

	private static Integer returnDaysDistanceFromDate(Date date, Calendar comparation) {
		Calendar compareDate = Calendar.getInstance();
		compareDate.setTime(date);
		return (int) ((comparation.getTimeInMillis() - compareDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
	}

	public static Date returnCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date transformLongStringToDate(String time) {
		Date date = new Date(Long.parseLong(time));
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DATE_FORMAT");
			return new SimpleDateFormat(DATE_FORMAT).parse(simpleDateFormat.format(date));
		} catch (ParseException e) {
			DroolsEngineLogger.errorMessage(DroolsDateUtils.class.getName(), e);
		}
		return null;
	}

	public static Integer returnDaysDistanceFromOrigin(Object object) {
		if (object instanceof Date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(0));
			return returnDaysDistanceFromDate((Date) object, cal);
		}
		return null;
	}

	public static Integer returnMonthsDistanceFromOrigin(Object object) {
		if (object instanceof Date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(0));
			return Math.abs(returnMonthsDistanceFromDate((Date) object, cal));
		}
		return null;
	}

	public static Integer returnYearsDistanceFromOrigin(Object object) {
		if (object instanceof Date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(0));
			return Math.abs(returnYearsDistanceFromDate((Date) object, cal));
		}
		return null;
	}
}
