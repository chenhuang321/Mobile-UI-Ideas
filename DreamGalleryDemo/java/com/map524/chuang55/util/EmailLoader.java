package com.map524.chuang55.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chen Huang, on Oct 31, 2016
 */

public class EmailLoader {

    private String username;
    private String domain;
    private boolean isValid = false;

    public EmailLoader() {} // Default constructor

    public EmailLoader(String email) {
        Pattern p = Pattern.compile("^(.+)@(.+).(.+)$");
        Matcher m = p.matcher(email);
        isValid = m.find();
        if(isValid) {
            setEmail(email);
        }
    }

    private void setEmail(String email) {
        username = email.substring(0, email.indexOf("@"));
        domain   = email.substring(email.indexOf("@") + 1);
    }

    @Override
    public String toString() {
        return username + "@" + domain;
    }

    public boolean isValid() {
        return isValid;
    }
}
