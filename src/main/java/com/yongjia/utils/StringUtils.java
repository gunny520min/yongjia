package com.yongjia.utils;

public class StringUtils {

    public static String getSecurityName(String name) {
        if (name != null) {
            StringBuffer securityName = new StringBuffer();
            securityName.append(name.charAt(0));
            for (int i = 1; i < name.length(); i++) {
                securityName.append("*");
            }

            return securityName.toString();
        }

        return name;
    }
    
    public static String getSecurityMobile(String mobile) {
        if (mobile != null || mobile.length()!=11) {
            StringBuffer securityMobile = new StringBuffer();
            securityMobile.append(mobile.charAt(0));
            securityMobile.append(mobile.charAt(1));
            securityMobile.append(mobile.charAt(2));
            securityMobile.append("*");
            securityMobile.append("*");
            securityMobile.append("*");
            securityMobile.append("*");
            securityMobile.append(mobile.charAt(7));
            securityMobile.append(mobile.charAt(8));
            securityMobile.append(mobile.charAt(9));
            securityMobile.append(mobile.charAt(10));
            

            return securityMobile.toString();
        }

        return mobile;
    }
}