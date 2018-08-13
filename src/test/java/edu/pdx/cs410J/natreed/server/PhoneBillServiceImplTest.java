package edu.pdx.cs410J.natreed.server;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneBillServiceImplTest {

  @Test
  public void serviceReturnsExpectedPhoneBill() {
    PhoneCall phoneCall = new PhoneCall("503-789-7890", "123-456-7890",
            new DateAndTime("09/26/2014", "08:21 am"), new DateAndTime("09/26/2014", "08:22 am"));
    PhoneBillServiceImpl service = new PhoneBillServiceImpl();
    service.addPhoneCall("Bill", phoneCall);


    PhoneBill bill = service.getPhoneBillFor("Bill");
    System.out.println(bill.getCustomer());
    System.out.println(bill.asStringForFile());
    assertThat(bill.getPhoneCalls().size(), equalTo(1));
  }
}
