package edu.pdx.cs410J.natreed.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.natreed.server.PhoneBill;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

  /**
   * Return the current date/time on the server
   */
  void getPhoneBill(AsyncCallback<PhoneBill> async);

  /**
   * Gets phonebill for customer
   */
  void getPhoneBillFor(String name, AsyncCallback<PhoneBill> async);

  /**
   * Add phonebill to server-side database
   */
  void addPhoneBill(PhoneBill phoneBill,  AsyncCallback async);

  /**
   * Always throws an exception so that we can see how to handle uncaught
   * exceptions in GWT.
   */
  void throwUndeclaredException(AsyncCallback<Void> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException(AsyncCallback<Void> async);

}
