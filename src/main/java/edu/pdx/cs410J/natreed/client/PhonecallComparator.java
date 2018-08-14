package edu.pdx.cs410J.natreed.client;
import java.util.Comparator;

import java.util.Date;

public class PhonecallComparator implements Comparator<PhoneCall> {
    public int compare(PhoneCall a, PhoneCall b) {
        Date aDate = null;
        Date bDate = null;

        aDate = DateAndTime.StringToDate(a.getStartTimeString());
        bDate = DateAndTime.StringToDate(a.getEndTimeString());

        int val = aDate.compareTo(bDate);

        if (val == 0) {
            int callerNumberCmp = a.getCaller().compareTo(b.getCaller());
            if (callerNumberCmp == 0) {
                return 0;
            }
            else if (callerNumberCmp > 0) {
                return 1;
            }
            return -1;
        }
        else if (val > 0) {
            return 1;
        }
        else return -1;

    }
}

