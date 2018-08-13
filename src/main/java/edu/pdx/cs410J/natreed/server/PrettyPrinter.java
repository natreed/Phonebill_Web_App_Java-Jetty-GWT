package edu.pdx.cs410J.natreed.server;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrettyPrinter implements PhoneBillDumper {
    public PrettyPrinter (String _filePath)  {
        this.filePath = _filePath;
    }
    private String filePath;

    /**
     * Takes an abstract phone bill and prints it to a file.
     * @param bill
     * ./src/main/java/edu/pdx/cs410J/natreed/natreed.txt
     * -textFile  ./src/main/java/edu/pdx/cs410J/natreed/natreed-bad-year.txt Project3 123-456-7890 385-284-2342 01/11/2018 11:00 01/11/2018 11:30
     * @throws IOException
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        if (bill == null) {
            return;
        }

        //cast abstract phonebill to Phonebill
        PhoneBill phoneBill = (PhoneBill) bill;
        phoneBill.sortPhoneCallsAndRemoveDuplicates();

        //Created as String method to get the phonecall list in a suitable
        String formattedCallList = phoneBill.asStringForFile();

        File f = new File(filePath);
        FileWriter fw;

        fw = new FileWriter(f, false);
        formattedCallList = phoneBill.phoneBillHeader() + formattedCallList;

        try {
            //now Write bill contents to file.
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(formattedCallList);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Exception Occurred in saveBillToFile.");
            e.printStackTrace();
        }
    }
}
