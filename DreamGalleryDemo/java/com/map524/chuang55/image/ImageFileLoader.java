package com.map524.chuang55.image;

/**
 * Object to load information of each image
 * @author  Chen Huang
 * @version 2016.11.10
 */

public class ImageFileLoader {

    private String fileName;
    private String updateDateTime;
    private String desc;
    private boolean isEmpty = true;
    public final static String[] seperator = { ";", "|" };

    public ImageFileLoader() {
        isEmpty = true;
    }

    /**
     *
     * @param fileName         Image filename
     * @param updateDateTime   Date of a specific image file changed
     * @param desc             Description of the image file
     */
    public ImageFileLoader(String fileName, String updateDateTime, String desc) {
        setFileName(fileName);
        setUpdateDateTime(updateDateTime);
        validateWithClear();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    private void validateWithClear() {
        final String emptyString = "";
        if(fileName.isEmpty() || desc.isEmpty() || updateDateTime.isEmpty()) {
            fileName       = emptyString;
            updateDateTime = emptyString;
            desc           = emptyString;
            isEmpty        = true;
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public String toString() {
        if(!isEmpty()) {
            return fileName + seperator[0] + updateDateTime + seperator[1] + desc;
        }
        return "";
    }

    public void readLine(String line) {
        if(line.contains(seperator[0]) && line.contains(seperator[1])) {
            setFileName(line.substring(0, line.indexOf(seperator[0])));
            setUpdateDateTime(line.substring(line.indexOf(seperator[0]) + 1,
                    line.indexOf(seperator[1])));
            setDesc(line.substring(line.indexOf(seperator[1]) + 1));
        }
    }
}
