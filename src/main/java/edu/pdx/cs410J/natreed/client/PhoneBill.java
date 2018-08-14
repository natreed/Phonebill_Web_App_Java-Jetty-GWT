package edu.pdx.cs410J.natreed.client;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.*;

/**
 *
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    public PhoneBill() {}
    public PhoneBill (String _customer) {
        this.customer = _customer;
    }
    public PhoneBill (String _customer, Collection<PhoneCall> _calls) {
        this.customer = _customer;
        this.calls = _calls;
    }


    private String customer = "";
    private Collection<PhoneCall> calls = new ArrayList<PhoneCall>( );

    /**
     * grabs list of phonecalls in from phonebill and returns
     * them in a printable string
     * @return
     */
    public String asStringForFile () {
        String phoneBillString = "";
        Iterator<PhoneCall> phoneCallIterator = calls.iterator();

        while (phoneCallIterator.hasNext()) {
            PhoneCall call = phoneCallIterator.next();
            phoneBillString += call.toString();
            phoneBillString = phoneBillString.replaceAll("Phone call from ", "");
            phoneBillString = phoneBillString.replaceAll(" to ", "\t\t");
            phoneBillString = phoneBillString.replaceAll(" from ", "\t");
            phoneBillString += "\t\t" + call.getDuration() + "\n";
        }

        return phoneBillString;
    }

    /**
     * Returns calls between start and end times as a String.
     * @param _start
     * @param _end
     * @return
     */
    public String callsByTimeRange (String _start, String _end) {
        String phoneBillString = this.phoneBillHeader();
        Iterator<PhoneCall> phoneCallIterator = calls.iterator();

        while (phoneCallIterator.hasNext()) {
            PhoneCall call = phoneCallIterator.next();
            Date start = DateAndTime.StringToDate(_start);
            Date end = DateAndTime.StringToDate((_end));
            Date callStart = DateAndTime.StringToDate(call.getStartTimeString());

            if (callStart.after(start) && callStart.before(end)) {
                phoneBillString += call.toString();
                phoneBillString = phoneBillString.replaceAll("Phone call from ", "");
                phoneBillString = phoneBillString.replaceAll(" to ", "\t\t");
                phoneBillString = phoneBillString.replaceAll(" from ", "\t");
                phoneBillString += "\t\t" + call.getDuration() + "\n";
            }
        }

        return phoneBillString;
    }


    public void printPhoneBill () {
        String phoneBill = this.phoneBillHeader();
        phoneBill += this.asStringForFile();
        System.out.println(phoneBill);
    }

    /**
     * Returns the phone bill as String in 'pretty' format
     * @return
     */
    public String getPrettyString () {
        String phoneBill = this.phoneBillHeader();
        phoneBill += this.asStringForFile();
        return phoneBill;
    }

    /**
     *
     * @return
     */
    public String phoneBillHeader () {
        return ("Phonebill for: " + this.customer +
                "\n\nfrom\t\t\t\tto\t\t\t\tstart\t\t\t\t\tfinish\t\t\t\t\tDuration\n" +
                "-------------------------------------------------------------------------------------------------------------\n"
                );
    }

    /**
     *
     * @param phoneBill
     * @return
     */
    //Special case. Merges this phone bill with a phone bill from a file in the case of a single phonecall.
    public PhoneBill appendTo (PhoneBill phoneBill) {
        for (int i =  0; i < this.calls.size(); i ++) {
            ArrayList<PhoneCall> c = (ArrayList<PhoneCall>) calls;
            phoneBill.addPhoneCall(c.get(0));
            sortPhoneCallsAndRemoveDuplicates();
        }
        return phoneBill;
    }

    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
        call.setCallDuration();
        this.calls.add(call);
        sortPhoneCallsAndRemoveDuplicates();
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }

    /**
     * Sort phone calls and remove duplicates
     */
    public void sortPhoneCallsAndRemoveDuplicates() {
        ArrayList<PhoneCall> phoneCalls = new ArrayList<>(this.calls);
        Collections.sort(phoneCalls);
        for (int i = 1; phoneCalls.size() > 1 && i < phoneCalls.size(); i++) {
            int comp = phoneCalls.get(i).compareTo(phoneCalls.get(i-1));
            if (comp == 0) {
                phoneCalls.remove(i-1);
                i--;
            }
        }
        Collection<PhoneCall> phoneCalls1 = phoneCalls;
        this.calls = phoneCalls1;
    }


}



