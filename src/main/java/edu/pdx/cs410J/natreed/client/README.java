package edu.pdx.cs410J.natreed.client;

public class README {
    public static void printReadMe () {
        System.out.println("Nathan Reed: Project3");
        System.out.println("This program is a phonebill will add calls to or print a phonebill.\n");
        System.out.println("Project4 moves the phonebills to remote server with a database of customer" +
                "phone bills.\n");
        System.out.println("In the command line you must provide a\n" +
                "host url\n" +
                "host port\n" +
                "customer name: ascii format no special characters\n" +
                "\tTo query a customer bill:  -host <host url> -port <port number> <customer name>\n" +
                "\tTo query within a date range use: -host <host url> -port <port number> <customer name> <start time> <end time>\n" +
                "\tTo add a phonecall use: -host <host url> -port <port number> <customer name> <caller number> \n\t<callee number> <start time> <end time>");


        System.out.println();
        System.out.println("Usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
                "   args are (in this order):\n" +
                "       customer               Person whose phone bill we’re modeling\n" +
                "       callerNumber           Phone number of caller\n" +
                "       calleeNumber           Phone number of person who was called\n" +
                "       startTime              Date and time must be formatted as [mm/dd/yyyy hh:mm:ss am/pm]\n" +
                "       endTime                Date and time must be formatted as [mm/dd/yyyy hh:mm:ss am/pm]\n\n" +
                "       options are (options may appear in any order):\n" +
                "       -print                 Prints a description of the new phone call\n" +
                "       -README                Prints a this README and exits\n" +
                "       -textFile              Followed by a relative filepath will write phonebill to file\n" +
                "       -pretty(- or filepath) - prints to screen, filepath argument prints to file.\n");
    }

}
