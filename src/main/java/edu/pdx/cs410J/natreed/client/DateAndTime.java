package edu.pdx.cs410J.natreed.client;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;

import java.text.ParseException;
import java.util.Date;

public class DateAndTime {
//    DateAndTime () {}
//    DateAndTime (String date, String time) {
//        this.date = date;
//        this.time = time;
//    }
//
//    public String toString () {
//        return this.date + " " + this.time;
//    }
//
//    public String date = "";
//    public String time = "";

//    /**
//     *
//     * @param d
//     * @return
//     */
//    public static DateAndTime convertDateToDateAndTime (Date d) {
//        DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
//        DateTimeFormat formatter = new DateTimeFormat("MM/dd/yyyy hh:mm aa", info) {};
//        String startTimeString = formatter.format(d);
//        String sDate =  startTimeString.substring(0, startTimeString.indexOf(' '));
//        String sTime =  startTimeString.substring(startTimeString.indexOf(' ') + 1);
//        return new DateAndTime(sDate, sTime);
//    }

    /**
     *
     * @param d
     * @return
     */
    public static String dateToString (Date d) {
        DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
        DateTimeFormat formatter = new DateTimeFormat("MM/dd/yyyy hh:mm aa", info) {};
        String date = formatter.format(d);
        return date;
    }

    public static Date StringToDate (String dateString) {
        DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
        DateTimeFormat formatter = new DateTimeFormat("MM/dd/yyyy hh:mm aa", info) {};
        Date date = null;

        try {
            date = formatter.parse(dateString);
        }
        catch (Exception e){
            new RuntimeException("Could not parse date: " + dateString + " format needs to be MM/dd/yyyy hh:mm aa");
        }


        return date;
    }

//    /**
//     *
//     * @return
//     */
//    public  Date toDate () {
//        DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
//        DateTimeFormat formatter = new DateTimeFormat("MM/dd/yyyy hh:mm aa", info) {};
//        Date date = null;
//
//
//        date = formatter.parse(this.toString());
//
//        return date;
//    }

}
