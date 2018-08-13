package edu.pdx.cs410J.natreed.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class CmdLnParser {

    /**
     * Constructor takes command line arguments and creates
     * list of acceptable options.
     * @param args
     */
    CmdLnParser(String [] args) {
        if (args.length == 0) {
            throw new RuntimeException("Missing command line arguments.");
        }
        this.rawArgs = new ArrayList<String>(Arrays.asList(args));
        this.optionsList.add("-print");
        this.optionsList.add("-README");
        this.optionsList.add("-textFile");
        this.optionsList.add("-pretty");
        this.optionsList.add("-search");
        this.optionsList.add("-host");
        this.optionsList.add("-port");
    }

    private ArrayList<String> rawArgs = new ArrayList<String>();
    private ArrayList<String>  optionsList = new ArrayList<String>();
    private CallData callData = new CallData();
    private String phoneBillFile = null;
    private String prettyFile = null;
    private int port = 0;
    private String host = null;

    /**
     * Getter for phonebill file
     * @return
     */
    public String getPhoneBillFile () { return this.phoneBillFile; }
    public String getCustomerName () { return this.callData.customer; }
    public String getPrettyFile () { return this.prettyFile; }
    public int getPort () { return this.port; }
    public String getHost () { return this.host; }

    /**
     * checkArgs is the public face of the CmdLine parser. It is passed
     * an argument string and returns a verified Calldata object
     * which includes: customer name, callee, caller, start, end.
     * @return
     */
    public CallData checkArgs () {
        CallData callData = new CallData();

        //update rawArgs after extracting each element of arglist
        //try {
            extractOptions(rawArgs, callData);
            //Check to see if we have a readme argument
            if (callData.options.contains("-README")) {
                return callData;
            }

            if (rawArgs.size() == 0) {
                return callData;
            }
            //If -search is an option, just get start and end times

            extractName(rawArgs, callData);

            if (rawArgs.size() == 0) {
                return callData;
            }

            if (!callData.options.contains("-search")) {
                setCallerNumber(rawArgs, callData);
                setCalleeNumber(rawArgs, callData);
            }

            extractStartAndEndTimes(rawArgs, callData);


            //check that start time comes before end time
            startTimeIsBeforeEndTime(callData.startTime, callData.endTime);

            //check to see if port or hostname == null
            if (this.host == null) {
                System.out.println("No given host. Attempting to connect to " +
                        "localhost on port: " + this.port);
                this.host = "localhost";
            }
            else if (this.port == 0) {
                throw new RuntimeException("You must include a port with the -port option.");
            }
            if (rawArgs.size() > 0) {
                throw new RuntimeException("Too many arguments.");
            }
//        }
//        catch (Exception e) {
//            System.out.println(e);
//            System.exit(1);
//        }

        return callData;
    }

    /**
     * Returns true if string matches a phone number.
     * @param s
     * @return
     */
    public static boolean isPhoneNumber (String s) {
        return s.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}");
    }


    /**
     * Returns true if string matches a date.
     * @param s
     * @return
     */
    public static boolean isDate (String s) {
        return s.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((18|19|20|21)\\d\\d)");
    }


    /**
     * Returns true if string matches a time.
     * @param s
     * @return
     */
    public static boolean isTime (String s) {

        return s.matches("((0?[1-9])|(1[012])):([0-5][0-9])(\\s)*((am|pm)|(AM|PM))");
    }

    /**
     * Function starts at beginning of args and extracts options
     * @param argList
     * @return  new arglist with options removed
     */
    private ArrayList<String> extractOptions(ArrayList<String> argList, CallData callData) {
        int i = 0;
        while(argList.size() > 0) {
            String arg = argList.get(0);
            if (arg.startsWith("-")) {
                if (this.optionsList.contains(arg)) {
                    callData.options.add(argList.remove(0));
                    if (arg.trim().equals("-pretty")) {
                        checkNonEmpty(argList, "You need to give a filepath after '-pretty'.");
                        this.prettyFile = argList.remove(0);
                    }
                    else if (arg.trim().equals("-port")) {
                        checkNonEmpty(argList, "You need to specify a port after -port option");

                        String portArg = argList.remove(0);
                        try {
                            this.port = Integer.parseInt(portArg);
                        }
                        catch (Exception e){
                            System.out.println("The port needs to be an integer. Cannot parse " + portArg);
                        }
                    }
                    else if (arg.trim().equals("-host")) {
                        checkNonEmpty(argList, "You need to specify a host after -host option");
                        this.host = argList.remove(0);
                    }
                }
                else {
                    System.err.println("Unknown option " + arg);
                    System.exit(1);
                }
            }
            else {
                break;
            }
        }
        return argList;
    }

    /**
     * Checks if start time is before end time
     * @param start
     * @param end
     * @return
     */
    private void startTimeIsBeforeEndTime (DateAndTime start, DateAndTime end) {
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy hh:mm aa");
        Date aDate = null;
        Date bDate = null;
        try {
            aDate = df.parse(start.toString());
            bDate = df.parse(end.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (aDate.after(bDate)) {
            throw new RuntimeException("start time: " + start + " is greater than end time: " + end);
        }
    }

    /**
     * Removes Customer name from argslist and adds to callData
     * A name can be any string of characters
     * @param argList
     * @return
     */
    private ArrayList<String> extractName (ArrayList<String> argList, CallData callData) {
        checkNonEmpty(argList, "No more args. Can't get name");
        callData.customer = argList.remove(0);
        //Parse a
        if (callData.customer.startsWith("\"")) {
            while (!callData.customer.endsWith("\"")) {
                checkNonEmpty(argList, "No more args. Can't get closing quote for name.");
                callData.customer += " " + argList.remove(0);
            }
        }
        return argList;
    }

    /**
     * Verifies that there are still args left to process.
     * Verifies that phone number argument is a phone number.
     * Produces error otherwise.
     * @param argList
     *
     */
    private String extractPhoneNumber(ArrayList<String> argList, String callerOrCallee) {
        checkNonEmpty(argList, "No more args. Can't get caller number.");
        String phoneNumber = argList.remove(0);
        if (isPhoneNumber(phoneNumber)) {
            return phoneNumber;
        }

        throw new RuntimeException(callerOrCallee + " number is not formatted correctly: " +
                phoneNumber + " try: ddd-ddd-dddd");
    }

    /**
     * Adds caller number to callData
     * @param args
     * @param callData
     */
    private void setCallerNumber (ArrayList<String> args, CallData callData) {
        callData.callerNumber =  extractPhoneNumber(args, "Caller");
    }

    /**
     * Adds callee number to callData
     * @param args
     * @param callData
     */
    private void setCalleeNumber (ArrayList<String> args, CallData callData) {
        callData.calleeNumber =  extractPhoneNumber(args, "Callee");
    }


    /**
     * Adds start times and end times to call data. Errors if it runs
     * out of arguments.
     * @param argList
     * @param callData
     */
    private void extractStartAndEndTimes (ArrayList<String> argList, CallData callData) {
        checkNonEmpty(argList, "No more args. Can't get start date.");
        String startDate = argList.remove(0);

        checkNonEmpty(argList, "No more args. Can't get start time.");

        String startTime = argList.remove(0);
        checkNonEmpty(argList, "No more args. Can't get start time.");
        startTime += " " + argList.remove(0);

        callData.startTime = extractDateTime(startDate, startTime);
        checkNonEmpty(argList, "No more args. Can't get end date.");

        String endDate = argList.remove(0);
        checkNonEmpty(argList, "No more args. Can't get end time.");

        String endTime = argList.remove(0);
        checkNonEmpty(argList, "No more args. Can't get end time.");
        endTime += " " + argList.remove(0);
        callData.endTime = extractDateTime(endDate, endTime);

    }

    /**
     * Verifies that date and time are formatted correctly.
     *
     * @param date
     * @param time
     * @return a Date and Time object
     */
    private DateAndTime extractDateTime (String date, String time) {
        DateAndTime dateTime = new DateAndTime(date, time);
        if (!isDate(dateTime.date)) {
            throw new RuntimeException("Date not in correct format. " + dateTime.date);
        }
        if (!isTime(dateTime.time)) {
            throw new RuntimeException("Time not in correct format: " + dateTime.time);
        }
        return new DateAndTime(dateTime.date, dateTime.time);
    }

    /**
     * Checks to see if there are more args to process.
     * @param argList
     * @param msg
     */
    private void checkNonEmpty (ArrayList<String> argList, String msg) {
        if (argList.size() == 0) {
            throw new RuntimeException(msg);
        }
    }
}