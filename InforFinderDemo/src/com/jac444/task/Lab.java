package com.jac444.task;

import java.util.Vector;

public class Lab implements MaxTagValue {


    String labName;
    Vector<MobileDevice> devices;


    public Lab(String labName) {
        if(labName.isEmpty())
            throw new RuntimeException();
        this.labName = labName;
        devices = new Vector<>();
    }

    public void addDevice(MobileDevice md) {
        if(devices == null) {
            devices = new Vector<>();
        }
        if(md == null)
            return;
        devices.add(md);
    }

    @Override
    public String toString() {
        String r = "Lab = " + labName + "\n[\n";
        for(MobileDevice mobileDevice : devices)
            r += mobileDevice.toString();
        return r;
    }

    //
    public boolean isThereDevice(MobileDevice md) {
        boolean found = false;
        for(MobileDevice mobileDevice : devices) {
            if(mobileDevice.equals(md))
                found = true;
        }
        return found;
    }

    public int findMaximumValueTag() {
        final int size = devices.size();
        int[] valueTags = new int[size];
        for(int i = 0; i < size; i++)
            valueTags[i] = devices.get(i).valueTag;
        return Finder.findMaximumValueTag(valueTags);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = labName != null ? labName.hashCode() : 0;
        result = 31 * result + (devices != null ? devices.hashCode() : 0);
        return result;
    }


    public boolean rentRequest(MobileDevice wanted, String requestDate, String dueDate)
            throws DateFormatException {
        int condition = 0;
        final int ok = 3;
        condition += Helper.isValidDate(requestDate)? 1 : 0;
        condition += Helper.isValidDate(dueDate)? 1 : 0;
        condition += Helper.timeDifference(requestDate, dueDate) > 0? 1 : 0;
        return condition == ok;
    }
}
