package com.jac444.task;

class MobileDevice {

    String       deviceName;  // the device name
    int          valueTag;    // an integer between -100 and 100
    Lab          lab;         // the lab having this device it its inventory
    RentSettings rs;          // rent settings

    public MobileDevice() {} // Default constructor

    /**
     * @param deviceName The name of the mobile device
     * @param valueTag   The value of the mobile device
     * @throws RuntimeException Validates deviceName string and value integer
     */
    public MobileDevice(String deviceName, int valueTag) {
        if(deviceName.isEmpty())
            throw new RuntimeException("Empty string is not allowed here!");
        if(valueTag < -100 || valueTag > 100)
            throw new RuntimeException("The integer is out of the range!");
        this.deviceName = deviceName;
        this.valueTag = valueTag;
        //System.out.println(toString());
    }

    /**
     *
     * @param lab The object lab for setter method
     * @throws NullPointerException Throws the error message if lab object is null
     */
    public void setLab(Lab lab) {
        if(lab == null)
            throw new NullPointerException();
        this.lab = lab;
    }

    // set the rent dates; if dates are not valid catch DateFormatException and return false,
    // if rentDate > dueDate catch RentPeriodException and return false
    // if one the exceptions occur there is no RentSettings object

    /**
     *
     * @param rentDate The date the customer rents the mobile
     * @param dueDate  The date the customer should return the mobile
     * @param lab      The lab period which has been chosen
     * @return         If true, success, if false, failed
     */
    public boolean rentDevice(String rentDate, String dueDate, Lab lab) {
        RentSettings rs = null;
        try {
            rs = new RentSettings(rentDate, dueDate, lab);
        } catch (DateFormatException | RentPeriodException e) {
            e.printStackTrace();
        }
        setRs(rs);
        return true;
    }


    // destroy the RentSettings object for this device
    public void returnDevice(Lab lab) {
        rs = null;
    }

    /**
     *
     * @param lab The lab object index
     * @return return the date when this device is available
     */
    public String availableDate(Lab lab) {
        return Helper.printAvailable(this, rs.rentDate, lab);
    }

    /**
     *
     * @return returns true if the current date is greater than the due date
     */
    public boolean isDeviceOverdue() throws DateFormatException {
        long diff = Helper.timeDifference(rs.dueDate, Helper.getCurrentDate());
        return diff > 0;
    }

    /**
     *
     * @param lab The lab object index
     * @return if the mobile is rented or still available
     */
    public boolean isRented(Lab lab) {
        return lab.isThereDevice(this);
    }

    public RentSettings getRs() {
        return rs;
    }

    /**
     *
     * @param rs Rental source setting
     */
    public void setRs(RentSettings rs) {
        this.rs = rs;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = deviceName != null ? deviceName.hashCode() : 0;
        result = 31 * result + valueTag;
        return result;
    }

    /**
     * Convert this object to a string value
     * @return the string format of this object
     */
    @Override
    public String toString() {
        String s = "";

        final char NEWLINE = '\n';
        s += "DeviceName: "  + deviceName + NEWLINE;
        s += "DeviceValue:"  + valueTag   + NEWLINE;
        if(lab != null)
            s += lab.toString()  + NEWLINE;
        System.out.println(s);
        s += rs .toString();
        return s;
    }

    public String deviceName() {
        return "(" + deviceName + ", " + valueTag + ')';
    }

    private class RentSettings {

        private String rentDate;          // date when the item is requested
        private String dueDate;           // date when the item must be returned
        private boolean borrowed = false; // true if the item is rented

        /**
         * Default constructor
         * @throws DateFormatException Validate the format of dates
         */
        private RentSettings() throws DateFormatException {
            rentDate = Helper.getCurrentDate();
            dueDate  = Helper.getCurrentDate();
            borrowed = false;
        }

        /**
         * private ctr must throw DateFormatException and RentPeriodException
         * @param rentDate Index resource of rent date
         * @param dueDate  Index resource of due dare
         * @param lab      Index resource of lab object
         * @throws DateFormatException throws if the date format is invalid
         * @throws RentPeriodException  throws if the rent date is later than due date
         */
        private RentSettings(String rentDate, String dueDate, Lab lab) throws DateFormatException, RentPeriodException {
            try {
                long diff = Helper.timeDifference(rentDate, dueDate);
                if(Helper.isValidDate(rentDate) || !Helper.isValidDate(dueDate))
                    throw new DateFormatException("Date format error!");
                if(diff < 0)
                    throw new RentPeriodException("Due date should be setup later than rent date!");
            } catch (RentPeriodException | DateFormatException e) {
                e.printStackTrace();
            }
            borrowed = true;

        }

        @Override
        public String toString() {
            return "RentSettings{" +
                    "rentDate='" + rentDate + '\'' +
                    ", dueDate='" + dueDate + '\'' + MobileDevice.this.lab.labName +
                    ", borrowed=" + borrowed +
                    '}';
        }
    }
}

