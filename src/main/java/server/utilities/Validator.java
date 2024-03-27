package server.utilities;

import server.patternclass.TicketType;

public class Validator {
    private Validator(){}
    public static boolean isValidString(String str) {
        if (str.isEmpty() || str.isBlank() || str.equals("null")) {
            return false;
        }
        return true;
    }

    public static Boolean isValidStringWithNull(String str) {
        if (str.equals("null")) {
            return null;
        }
        if (str.isEmpty() || str.isBlank()) {
            return false;
        }
        return true;
    }

    public static boolean isValidInteger(String strInteger, int limit) {
        try {
            if (strInteger.isEmpty() || strInteger.isBlank() || Integer.parseInt(strInteger) <= limit) {
                return false;
            }
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static Boolean isValidIntegerWithNull(String strInteger, int limit) {
        try {
            if (strInteger.equals("null")) {
                return null;
            }
            if (strInteger.isEmpty() || strInteger.isBlank() || Integer.parseInt(strInteger) <= limit) {
                return false;
            }
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isValidLonger(String strLonger, long limit) {
        try {
            if (strLonger.isEmpty() || strLonger.isBlank() || Long.parseLong(strLonger) <= limit) {
                return false;
            }
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static Boolean isValidLongerWithNull(String strLonger) {
        if (strLonger.equals("null")) {
            return null;
        }
        if (strLonger.isEmpty() || strLonger.isBlank()) {
            return false;
        }
        try {
            Long.parseLong(strLonger);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isValidTicketType(String strTicketType){
        if (strTicketType.isEmpty() || strTicketType.isBlank()) {
            return false;
        }
        try {
            TicketType.valueOf(strTicketType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
