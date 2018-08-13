package edu.pdx.cs410J.natreed.server;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Comparable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{
  PhoneCall () {}

  private String callerNumber;
  private String calleeNumber;
  private Date startTime;
  private Date endTime;
  private String duration;


  public PhoneCall(String _callerNumber, String _calleeNumber, DateAndTime _startTime, DateAndTime _endTime) {

    callerNumber = _callerNumber;
    calleeNumber = _calleeNumber;
    startTime = _startTime.toDate();
    endTime = _endTime.toDate();
    setCallDuration();

  }

    @Override
  public String getCaller() {
    return callerNumber;
  }

  @Override
  public String getCallee() {
    return calleeNumber;
  }

  @Override
  public String getStartTimeString() {
    return getDateString(DateAndTime.convertDateToDateAndTime(startTime).date, DateAndTime.convertDateToDateAndTime(startTime).time);
  }

  @Override
  public String getEndTimeString()  {
    return getDateString(DateAndTime.convertDateToDateAndTime(endTime).date, DateAndTime.convertDateToDateAndTime(endTime).time);
  }

  private String getDateString (String _date, String _time) {
    String DateString = _date + " " + _time;
    return DateString;
  }



  public String getDuration () {
    return duration;
  }

  /**
   * Compares first by time, then by date
   * @param o
   * @return
   */
  @Override
  public int compareTo(PhoneCall o) {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    Date aDate = null;
    Date bDate = null;

    try {
      aDate = df.parse(this.getStartTimeString());
      bDate = df.parse(o.getStartTimeString());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    int val = aDate.compareTo(bDate);
    return val;
  }

  /**
   * Sets the phonecall's call duration in its callData
   */
  public void  setCallDuration () {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    Date aDate = null;
    Date bDate = null;
    try {
      aDate = df.parse(this.getStartTimeString());
      bDate = df.parse(this.getEndTimeString());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    long diff = bDate.getTime() - aDate.getTime();

    if (diff < 0) {
      throw new RuntimeException("Call must start before it ends!");
    }

    long  duration = diff/1000/60;

    this.duration = duration  + " minutes";
  }
}
