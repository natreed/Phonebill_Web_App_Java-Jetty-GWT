package edu.pdx.cs410J.natreed.client;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DefaultDateTimeFormatInfo;
import java.util.Date;


public class Parser {



    public static void callerCalleeCheck (String callerNumber, String calleeNumber) {
        if (!isPhoneNumber(callerNumber)) {
            throw new RuntimeException("Caller number:" +
                    callerNumber + " is not formatted correctly. Try ddd-ddd-dddd.");
        }
        if (!isPhoneNumber(calleeNumber)) {
            throw new RuntimeException("Caller number:" +
                    calleeNumber + " is not formatted correctly. Try ddd-ddd-dddd.");
        }
    }


    /**
     * Returns true if string matches a phone number.
     * @param s
     * @return
     */
    public static boolean isPhoneNumber (String s) {
        return s.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}");
    }


    /**
     * Returns true if string matches a date.
     * @param s
     * @return
     */
    public static boolean isDate (String s) {
        return s.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((18|19|20|21)\\d\\d)");
    }


    /**
     * Returns true if string matches a time.
     * @param s
     * @return
     */
    public static boolean isTime (String s) {

        return s.matches("((0?[1-9])|(1[012])):([0-5][0-9])(\\s)*((am|pm)|(AM|PM))");
    }

    /**
     * Checks if start time is before end time
     * @param start
     * @param end
     * @return
     */
    private void startTimeIsBeforeEndTime (String start, String end) {
        DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
        DateTimeFormat df = new DateTimeFormat("MM/dd/yyyy hh:mm aa", info) {};
        Date aDate = null;
        Date bDate = null;

        aDate = df.parse(start.toString());
        bDate = df.parse(end.toString());


        if (aDate.after(bDate)) {
            throw new RuntimeException("start time: " + start + " is greater than end time: " + end);
        }
    }

}