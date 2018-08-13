package edu.pdx.cs410J.natreed.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.natreed.client.PhoneBill;
import edu.pdx.cs410J.natreed.client.PhoneCall;
import edu.pdx.cs410J.natreed.client.PhoneBillService;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
  //add phonecall to phonebill, pretty print, search
  @Override
  public PhoneBill getPhoneBill() {
    PhoneBill phonebill = new PhoneBill();
    phonebill.addPhoneCall(new PhoneCall());
    return phonebill;
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
