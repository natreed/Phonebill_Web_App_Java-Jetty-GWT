package edu.pdx.cs410J.natreed.client;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Comparable;
import java.util.Date;


public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>{


  private String callerNumber;
  private String calleeNumber;
  private Date startTime;
  private Date endTime;
  private String duration;

  public PhoneCall() {}

  public PhoneCall(String _callerNumber, String _calleeNumber, Date _startTime, Date _endTime) {
    callerNumber = _callerNumber;
    calleeNumber = _calleeNumber;
    startTime = _startTime;
    endTime = _endTime;
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
    return DateAndTime.dateToString(startTime);
  }

  @Override
  public String getEndTimeString()  {
    return DateAndTime.dateToString(endTime);
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
    Date aDate = DateAndTime.StringToDate(this.getStartTimeString());
    Date bDate = DateAndTime.StringToDate(o.getStartTimeString());

    int val = aDate.compareTo(bDate);

    if (val == 0) {
      int callerNumberCmp = this.getCaller().compareTo(this.getCaller());
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

  /**
   * Sets the phonecall's call duration in its callData
   */
  public void  setCallDuration () {
    Date aDate = null;
    Date bDate = null;

    aDate = DateAndTime.StringToDate(this.getStartTimeString());
    bDate = DateAndTime.StringToDate(this.getEndTimeString());

    long diff = bDate.getTime() - aDate.getTime();

    if (diff < 0) {
      throw new RuntimeException("Call must start before it ends!");
    }

    long  duration = diff/1000/60;

    this.duration = duration  + " minutes";
  }


}
