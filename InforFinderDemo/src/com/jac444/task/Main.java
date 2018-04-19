package com.jac444.task;

/**
 * @author Chen Huang
 * @version Assignment 2.0
 */

public class Main {

    private final static String newnham = "NewnhamLab";
    private final static String york    = "YorkLab";

    public static void main(String[] args) {

        /* TASK 1 - build labs from files - at least two labs */

        System.out.println("\n\n *" + " TASK 1 " + "*");
        Labs labs = new Labs(2);
        labs.addDevicesToLab(newnham, newnham + ".txt");
        labs.addDevicesToLab(york   , newnham + ".txt");
        /* TASK 2 - ask for a device that is not in any lab inventory */
        System.out.println("\n\n *" + " TASK 2 " + "*");
        MobileDevice Pixel = new MobileDevice("Google Pixel", 2000);
        System.out.println(Pixel.toString());

         /* TASK 3 - ask for a device that is in a lab inventory
         *  issue a rent request and print the device
         *  issue the same rent request and print the device
         *  return the device
         *  issue the rent request with new dates and print the device
         */
        System.out.println("\n\n *" + " TASK 3 " + "*");
        MobileDevice blackberry = new MobileDevice("Blackberry10", 120);
        Lab lab = new Lab(""); // Initialize the object
        Lab lab2 = new Lab("");
        String emptyLabString = "Lab = " + "" + "\n[\n";
        try {
            lab = labs.rentDeviceAvailable(blackberry, "2016-11-03", "2016-11-09");
        } catch (DateFormatException e) {
            e.printStackTrace();
        }
        if(!lab.toString().equals(emptyLabString)) {
            // Rent request no 1
            try {
                lab.rentRequest(blackberry, "2016-11-03", "2016-11-09");
                System.out.println(blackberry.toString());
            } catch (DateFormatException e) {
                e.printStackTrace();
            }
            // Rent request no 2
            try {
                lab.rentRequest(blackberry, "2016-09-12", "2016-11-09");
            } catch (DateFormatException e) {
                System.out.println(blackberry.toString());
                e.printStackTrace();
            }
        }

        try {
            lab2 = labs.rentDeviceAvailable(blackberry, "2016-11-03", "2016-11-09");
        } catch (DateFormatException e) {
            e.printStackTrace();
        }
        if(!lab2.toString().equals(emptyLabString)) {
            // Rent request no 1
            try {
                lab2.rentRequest(blackberry, "2016-11-01", "2017-04-29");
                System.out.println(blackberry.toString());
            } catch (DateFormatException e) {
                e.printStackTrace();
            }
        }
         /* TASK 4 - ask for the same device in all labs
          * if you can find a lab, rent the device from that lab
          */
        //TODO

        /* TASK 5 - calculate maximum value tag for each lab */
        //TODO
    }
}
