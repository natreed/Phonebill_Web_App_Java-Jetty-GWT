package edu.pdx.cs410J.natreed.server;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.natreed.client.PhoneBill;
import edu.pdx.cs410J.natreed.client.PhoneBillService;
import edu.pdx.cs410J.natreed.client.PhoneCall;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
  static final String CUSTOMER_PARAMETER = "customer";
  private static final String CALLER_PARAMETER = "caller";
  private static final String CALLEE_PARAMETER = "callee";
  private static final String START_TIME_PARAMETER = "startTime";
  private static final String END_TIME_PARAMETER = "endTime";


  private final Map<String, PhoneBill> PhoneBillDataBase = new HashMap<>();

  @Override
  public PhoneBill getPhoneBill() {
    PhoneBill phonebill = new PhoneBill();
    phonebill.addPhoneCall(new PhoneCall());
    return phonebill;
  }

  /**
   * Gets phone bill by customer name.
   * @param customer
   * @return
   */
  public PhoneBill getPhoneBillFor(String customer) {
    return this.PhoneBillDataBase.get(customer);
  }

  /**
   *
   * @param phoneBill
   */
  public void addPhoneBill(PhoneBill phoneBill) {
    this.PhoneBillDataBase.put(phoneBill.getCustomer(), phoneBill);
  }

  /**
   *
   * @param customer
   * @param phoneCall
   */
  public void  addPhoneCall (String customer, PhoneCall phoneCall) {
    PhoneBill phoneBill = getPhoneBillFor(customer);
    if (phoneBill == null) {
      phoneBill = new PhoneBill(customer);
      phoneBill.addPhoneCall(phoneCall);
      addPhoneBill(phoneBill);
    }
    else {
      phoneBill.addPhoneCall(phoneCall);
    }

  }

  /**
   *
   * @param customer
   * @return
   */
  public String printBill (String customer) {
    PhoneBill phoneBill = getPhoneBillFor(customer);
    if (phoneBill == null) {
      throw new RuntimeException("No call records exist for " + customer + ". If you would like" +
              " to create a new phone bill choose add call and enter the call information.");
    }
    else {
        return phoneBill.getPrettyString();
    }
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }



}
