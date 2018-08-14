package edu.pdx.cs410J.natreed.server;

import edu.pdx.cs410J.natreed.client.DateAndTime;
import edu.pdx.cs410J.natreed.client.PhoneBill;
import edu.pdx.cs410J.natreed.client.PhoneCall;
import edu.pdx.cs410J.natreed.server.PhoneBillServiceImpl;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneBillServiceImplTest {

  @Test
  @Ignore
  public void serviceReturnsExpectedPhoneBill() {

    //Create a phone bill and add calls
    PhoneCall pc1 = new PhoneCall("503-789-7890", "123-456-7890",
            DateAndTime.StringToDate("09/28/2014 08:21 am"),  DateAndTime.StringToDate("09/28/2014 08:22 am"));
    PhoneCall pc2 = new PhoneCall("503-789-7890", "123-456-7890",
            DateAndTime.StringToDate("09/26/2014 08:00 am"),  DateAndTime.StringToDate("09/26/2014 08:01 am"));
    PhoneCall pc3 = new PhoneCall("503-789-7890", "123-456-7890",
            DateAndTime.StringToDate("09/25/2014 08:21 am"),  DateAndTime.StringToDate("09/25/2014 08:22 am"));
    PhoneCall pc4 = new PhoneCall("503-789-7890", "234-456-7890",
            DateAndTime.StringToDate("09/25/2014 08:21 am"),  DateAndTime.StringToDate("09/25/2014 08:22 am"));
    PhoneCall pc5 = new PhoneCall("503-789-7890", "123-456-7890",
            DateAndTime.StringToDate("09/27/2014 07:21 am"),  DateAndTime.StringToDate("09/27/2014 07:22 am"));
    PhoneCall pc6 = new PhoneCall("503-789-7890", "123-456-7890",
            DateAndTime.StringToDate("09/26/2014 08:21 am"),  DateAndTime.StringToDate("09/26/2014 08:22 am"));


    PhoneBillServiceImpl service = new PhoneBillServiceImpl();
    service.addPhoneCall("Bill", pc1);
    service.addPhoneCall("Bill", pc2);
    service.addPhoneCall("Bill", pc3);
    service.addPhoneCall("Bill", pc4);
    service.addPhoneCall("Bill", pc5);
    service.addPhoneCall("Bill", pc6);


    PhoneBill bill = service.getPhoneBillFor("Bill");
    System.out.println(bill.getCustomer());
    System.out.println(bill.asStringForFile());
    MatcherAssert.assertThat(bill.getPhoneCalls().size(), CoreMatchers.equalTo(5));

    //perform search

  }


}
