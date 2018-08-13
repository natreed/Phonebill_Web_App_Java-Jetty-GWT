package edu.pdx.cs410J.natreed.client;
import java.text.ParseException;
import com.google.gwt.i18n.shared.DateTimeFormat;
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
        DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aa");
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
        DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aa");
        String date = formatter.format(d);
        return date;
    }

    public static Date StringToDate (String dateString) {
        DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aa");
        Date date = null;

        date = formatter.parse(dateString);

        return date;
    }

    /**
     *
     * @return
     */
    public  Date toDate () {
        DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aa");
        Date date = null;


        date = formatter.parse(this.toString());

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
