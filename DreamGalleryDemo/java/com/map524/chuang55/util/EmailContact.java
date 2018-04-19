package com.map524.chuang55.util;

/**
 * Created by Chen Huang on 22:04 , Oct 27, 2016
 */

public class EmailContact {

    private String firstName;
    private String lastName;
    private String job_title;
    private EmailLoader email;
    private int numOfValidMembers = 0;
    private boolean isFull = false;

    private void setFormat() {
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName  = lastName .substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        job_title = job_title.substring(0, 1).toUpperCase() + job_title.substring(1).toLowerCase();
    }

    public EmailContact() {} // Non-member constructor

    public EmailContact(String firstName, String lastName, String job_title,
                        String email) {
        final int ok_num = 4;
        setFirstName(firstName);
        setLastName(lastName);
        setJobTitle(job_title);
        setEmail(email);
        if(numOfValidMembers == ok_num) {
            isFull = true;
            setFormat();
        }
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName.trim();
        if(!this.firstName.isEmpty())
            numOfValidMembers += 1;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName.trim();
        numOfValidMembers += 1;
    }

    public void setName(String name) {
        if(name.contains(" ")) {
            firstName = name.substring(0, name.indexOf(" ")).trim();
            lastName  = name.substring(name.indexOf(" ")).trim();
        } else {
            firstName = name;
            lastName  = "";
        }
    }

    public void setJobTitle(String job_title) {
        this.job_title = job_title;
        if(this.job_title.isEmpty())
            this.job_title = "Job Unknown";
        numOfValidMembers += 1;
    }

    public void setEmail(String email) {
        this.email = new EmailLoader(email);
        if(this.email.isValid())
            numOfValidMembers += 1;
    }

    public boolean isValid() {
        return isFull;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email.toString();
    }

    public String getJobTitle() {
        return job_title;
    }

    @Override
    public String toString() {
        return getFullName() + ";" + getJobTitle() + ";" + getEmail();
    }
}
