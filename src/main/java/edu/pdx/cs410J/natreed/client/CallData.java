package edu.pdx.cs410J.natreed.client;

import edu.pdx.cs410J.natreed.client.DateAndTime;

import java.util.ArrayList;

public class CallData {

    ArrayList<String> options = new ArrayList<>();
    String customer;
    String callerNumber;
    String calleeNumber;
    DateAndTime startTime;
    DateAndTime endTime ;
    String duration;

    /**
     *
     * @return
     */
    public ArrayList<String> getOptionsList () {
        return this.options;
    }
}
