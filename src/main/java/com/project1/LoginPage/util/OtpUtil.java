package com.project1.LoginPage.util;

import java.security.SecureRandom;

public class OtpUtil {

    private static final String NUMBERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOPT(){
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for(int i=0; i<OTP_LENGTH; i++){
            otp.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }
        return otp.toString();
    }
}
