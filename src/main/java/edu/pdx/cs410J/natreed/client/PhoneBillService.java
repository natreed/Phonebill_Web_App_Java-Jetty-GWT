package edu.pdx.cs410J.natreed.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.natreed.server.PhoneBill;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

  /**
   * Returns the a dummy Phone Bill
   */
  public PhoneBill getPhoneBill();

  /**
   *
   */
  public PhoneBill getPhoneBillFor(String name);

  /**
   *
   */
  void addPhoneBill(PhoneBill phoneBill);

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException();

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException() throws IllegalStateException;

}
