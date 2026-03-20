package ru.shmelev.stomatologyapp.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneUtils {

    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public static String normalize(String rawPhone) {
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(rawPhone, "RU"); // или "US"

            if (!phoneUtil.isValidNumber(number)) {
                throw new IllegalArgumentException("Invalid phone number");
            }

            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

        } catch (NumberParseException e) {
            throw new IllegalArgumentException("Invalid phone format", e);
        }
    }
}