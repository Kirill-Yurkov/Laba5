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

    public static boolean isValidInteger(String str, int limit) {
        try {
            if (str.isEmpty() || str.isBlank() || Integer.parseInt(str) <= limit) {
                return false;
            }
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static Boolean isValidIntegerWithNull(String str, int limit) {
        try {
            if (str.equals("null")) {
                return null;
            }
            if (str.isEmpty() || str.isBlank() || Integer.parseInt(str) <= limit) {
                return false;
            }
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isValidLonger(String str, long limit) {
        try {
            if (str.isEmpty() || str.isBlank() || Long.parseLong(str) <= limit) {
                return false;
            }
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static Boolean isValidLongerWithNull(String str) {
        if (str.equals("null")) {
            return null;
        }
        if (str.isEmpty() || str.isBlank()) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isValidTicketType(String str){
        if (str.isEmpty() || str.isBlank()) {
            return false;
        }
        try {
            TicketType.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
