package edu.pdx.cs410J.natreed.client;

public class README {
    public static String printReadMe () {
        String readmeString  = "";
        readmeString += "Nathan Reed: Project5\n" +
                "This web application is designed to manage phonebills for a database of customers\n\n" +
                "Features include:\n" +
                "- Creating a phonebill for a customer and adding it to the company database.\n" +
                "- Adding a phonecall to an existing customers phonebil.\n" +
                "- Retrieving and displaying a phonebill by customer name.\n" +
                "- Displaying calls made by a customer with a time range\n\n" +
                "To add a phone call select 'Add Phonecall' from the dropdown menu, fill in the required fields (name, " +
                "caller number, callee number, start time and end time) time in the boxes.\n\n" +
                "Start times and end times must include the date formatted as MM/dd/yyyy followed by the time formatted as hh:mm aa\n" +
                "\tFor example, Start Time = 08/20/2018 08:20 am and End Time =  08/20/2018 08:20 am would be acceptable inputs.\n" +
                "\tPhone numbers for the caller and callee must be formatted as 10 digits (i.e. 123-345-6789).\n\n" +
                "To print a phone call just select Print Call from the options, add the customer's name and click submity.\n" +
                "\tIf a phone bill does not exist for that customer you may create one by selecting Add Phonecall and following the steps " +
                "described above.\n\n" +
                "To search for phone calls within a specified date range select search from the drop down menu.\n" +
                "\tYou will be required to fill in the name, start date and end date fields. Format dates as described in the 'Add Phonecalls'" +
                "section.\n\n" +
                "HAVE A NICE DAY!";
        return readmeString;
    }

}
