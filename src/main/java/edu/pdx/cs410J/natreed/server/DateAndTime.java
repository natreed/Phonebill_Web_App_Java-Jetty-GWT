package edu.pdx.cs410J.natreed.server;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTime {
    DateAndTime () {}
    DateAndTime (String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String toString () {
        return this.date + " " + this.time;
    }

    public String date = "";
    public String time = "";

    /**
     *
     * @param d
     * @return
     */
    public static DateAndTime convertDateToDateAndTime (Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String startTimeString = formatter.format(d);
        String sDate =  startTimeString.substring(0, startTimeString.indexOf(' '));
        String sTime =  startTimeString.substring(startTimeString.indexOf(' ') + 1);
        return new DateAndTime(sDate, sTime);
    }

    /**
     *
     * @param d
     * @return
     */
    public static String dateToString (Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        String date = formatter.format(d);
        return date;
    }

    public static Date StringToDate (String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        Date date = null;

        try {
            date = formatter.parse(dateString);
        }
        catch  (ParseException e){
            e.printStackTrace();
        }

        return date;
    }

    /**
     *
     * @return
     */
    public  Date toDate () {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        Date date = null;

        try {
            date = formatter.parse(this.toString());
        }
        catch  (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     * @param s
     * @return
     */
    public static DateAndTime parseDateString (String s) {
         return DateAndTime.convertDateToDateAndTime(DateAndTime.StringToDate(s));
    }

}
