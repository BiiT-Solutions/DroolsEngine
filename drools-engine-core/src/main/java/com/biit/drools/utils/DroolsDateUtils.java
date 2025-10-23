package com.biit.drools.utils;

/*-
 * #%L
 * Drools Engine Core
 * %%
 * Copyright (C) 2022 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.biit.drools.logger.DroolsEngineLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DroolsDateUtils {

    private static final String DATE_FORMAT = "yyyy-mm-dd";
    private static final int DAY_MILLIS = 1000 * 60 * 60 * 24;
    private static final int MONTHS = 12;

    private DroolsDateUtils() {

    }

    public static Date returnCurrentDateMinusYears(int years) {
        final Calendar now = Calendar.getInstance();
        // Substract the years from the calendar
        now.add(Calendar.YEAR, -years);
        return now.getTime();
    }

    public static Date returnCurrentDateMinusMonths(int months) {
        final Calendar now = Calendar.getInstance();
        // Substract the months from the calendar
        now.add(Calendar.MONTH, -months);
        return now.getTime();
    }

    public static Date returnCurrentDateMinusDays(int days) {
        final Calendar now = Calendar.getInstance();
        // Substract the days from the calendar
        now.add(Calendar.DATE, -days);
        return now.getTime();
    }

    public static Integer returnYearsDistanceFromDate(Object object) {
        if (object instanceof Date) {
            final Calendar now = Calendar.getInstance();
            return Math.abs(returnYearsDistanceFromDate((Date) object, now));
        }
        return null;
    }

    private static Integer returnYearsDistanceFromDate(Date date, Calendar comparation) {
        final Calendar compareDate = Calendar.getInstance();
        compareDate.setTime(date);
        return comparation.get(Calendar.YEAR) - compareDate.get(Calendar.YEAR);
    }

    public static Integer returnMonthsDistanceFromDate(Object object) {
        if (object instanceof Date) {
            final Calendar now = Calendar.getInstance();
            return Math.abs(returnMonthsDistanceFromDate((Date) object, now));
        }
        return null;
    }

    private static Integer returnMonthsDistanceFromDate(Date date, Calendar comparation) {
        final Calendar compareDate = Calendar.getInstance();
        compareDate.setTime(date);
        return ((returnYearsDistanceFromDate(date, comparation) * MONTHS) + compareDate.get(Calendar.MONTH)) - comparation.get(Calendar.MONTH);
    }

    public static Integer returnDaysDistanceFromDate(Object object) {
        if (object instanceof Date) {
            final Calendar now = Calendar.getInstance();
            return returnDaysDistanceFromDate((Date) object, now);
        }
        return null;
    }

    private static Integer returnDaysDistanceFromDate(Date date, Calendar comparation) {
        final Calendar compareDate = Calendar.getInstance();
        compareDate.setTime(date);
        return (int) ((comparation.getTimeInMillis() - compareDate.getTimeInMillis()) / DAY_MILLIS);
    }

    public static Date returnCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date transformLongStringToDate(String time) {
        final Date date = new Date(Long.parseLong(time));
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DATE_FORMAT");
            return new SimpleDateFormat(DATE_FORMAT).parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            DroolsEngineLogger.errorMessage(DroolsDateUtils.class.getName(), e);
        }
        return null;
    }

    public static Integer returnDaysDistanceFromOrigin(Object object) {
        if (object instanceof Date) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(0));
            return returnDaysDistanceFromDate((Date) object, cal);
        }
        return null;
    }

    public static Integer returnMonthsDistanceFromOrigin(Object object) {
        if (object instanceof Date) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(0));
            return Math.abs(returnMonthsDistanceFromDate((Date) object, cal));
        }
        return null;
    }

    public static Integer returnYearsDistanceFromOrigin(Object object) {
        if (object instanceof Date) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(0));
            return Math.abs(returnYearsDistanceFromDate((Date) object, cal));
        }
        return null;
    }
}
