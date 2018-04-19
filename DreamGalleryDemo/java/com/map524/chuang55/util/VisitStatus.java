package com.map524.chuang55.util;

import java.text.DateFormat;
import java.util.Date;

public class VisitStatus {

    private String time;
    private String ip_address;
    final static public String TIME_KEY         = "LastVisitTime";
    final static public String IP_KEY           = "LastIpAddress";

    public VisitStatus() {
        time         = "Unknown time";
        ip_address   = "Unknown Status";
    } // Default constructor

    public void refreshTime() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Date date = new Date();
        time = dateFormat.format(date);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIpAddress(String ip_address) {
        this.ip_address = ip_address;
    }

    public void refreshIPAddress() {
        ip_address = "4G Network";
    }

    public String getTime() {
        return time;
    }

    public String getIpAddress() {
        return ip_address;
    }
}
