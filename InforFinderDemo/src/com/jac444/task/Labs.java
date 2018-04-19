package com.jac444.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Labs {

    public Lab[] labs;        // a collection of labs of type array
    public int numberOfLabs;  // number of labs in collection
    public static final String location = "G:/Seneca College/Java Space/Assignment2/";

    /**
     * Constructor with Integer value
     * @param numberOfLabs parameter to initialize the object of Labs
     */
    public Labs(int numberOfLabs) {
        if(numberOfLabs <= 0)
            return;
        this.numberOfLabs = numberOfLabs;
        labs = new Lab[numberOfLabs];
    }

    public Lab addDevicesToLab(String labName, String labFileName) {
        //System.out.println(labFileName);
        Lab lab = buildLabFromFile(labName, labFileName);
        System.out.println("Lab = " + labName + "\n[\n" + lab + "]");
        return lab;
    }

    /**
     *
     * @param  labName    The name of the lab
     * @param  fileName   The name of the txt file
     * @return the lab object which we built from file
     */
    public Lab buildLabFromFile(String labName, String fileName) {

        Lab lab = new Lab(labName);
        MobileDevice md;
        String s;

        try (BufferedReader br = new BufferedReader(new FileReader(location + fileName))) {
            while ((s = br.readLine()) != null) {
                String deviceNameTmp = s.substring(0, s.indexOf(","));
                int    deviceTagTmp  = Integer.parseInt(s.substring(s.indexOf(",") + 1));
                md = new MobileDevice(deviceNameTmp, deviceTagTmp); // Create MobileDevice object
                md.setLab(lab); // Set lab object
                lab.addDevice(md);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lab;
    }

    public Lab isThereDeviceInLabs(MobileDevice md) {
        Lab foundLab = null;
        for(Lab lab : labs) {
            if(lab.isThereDevice(md))
                foundLab = lab;
        }
        return foundLab;
    }


    public Lab rentDeviceAvailable(MobileDevice md, String requestDate, String dueDate)
            throws DateFormatException {
        Lab foundLab = null;
        for(Lab lab : labs) {
            if(lab.rentRequest(md, requestDate, dueDate))
                foundLab = lab;
        }
        return foundLab;
    }
}

