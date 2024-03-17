package server.utilities;

import server.patternclass.Coordinates;
import server.patternclass.Event;
import server.patternclass.Ticket;
import server.patternclass.TicketType;

import java.util.Date;
import java.util.Scanner;

public class TicketCreator {
    private static final Scanner src = new Scanner(System.in);
    private TicketCreator(){

    }
    public static Ticket createTicketGroup(){
        Ticket createdTicketGroup = new Ticket();
        createdTicketGroup.setName(readName("owner"));
        createdTicketGroup.setCoordinates(new Coordinates(
                readLonger("coordinates X", -503),
                readLonger("coordinates Y", -664)));
        createdTicketGroup.setCreationDate(new Date());
        createdTicketGroup.setPrice(readIntegerWithNull("price", 0));
        createdTicketGroup.setType(readTicketType(""));
        if(makeEvent()){
            Event event = new Event(
                    readName("event"),
                    readLongerWithNull("event min age"),
                    readInteger("event tickets count", 0),
                    readNameWithNull("event description")
            );
            event.setId(IdCounter.getIdForEvent(event));
            createdTicketGroup.setEvent(event);
        }
        createdTicketGroup.setId(IdCounter.getIdForTicket(createdTicketGroup));
        return createdTicketGroup;
    }

    private static String readName(String text){
        System.out.print("Enter " + text + " name:\n~~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank() || str.equals("null")){
            readName(text);
        }
        return str;
    }
    private static String readNameWithNull(String text){
        System.out.print("Enter " + text + " name:\n~~ ");
        String str = src.nextLine();
        if (str.equals("null")){
            return null;
        }
        if (str.isEmpty() || str.isBlank()){
            readName(text);
        }
        return str;
    }
    private static Integer readIntegerWithNull(String text, int limit){
        System.out.print("Enter " + text + " int value (должно быть больше " + limit + "):\n~~ ");
        String str = src.nextLine();

        try {
            if (str.equals("null")){
                return null;
            }
            if (str.isEmpty() || str.isBlank() || Integer.parseInt(str)<limit){
                readIntegerWithNull(text, limit);
            }

            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            System.out.println("incorrect int value");
            return readIntegerWithNull(text, limit);
        }
    }
    private static Integer readInteger(String text, int limit){
        System.out.print("Enter " + text + " int value (должно быть больше " + limit + "):\n~~ ");
        String str = src.nextLine();

        try {
            if (str.isEmpty() || str.isBlank() || Integer.parseInt(str)<limit){
                System.out.println("incorrect int value");
                readInteger(text, limit);
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            System.out.println("incorrect int value");
            return readInteger(text, limit);
        }
    }

    private static Long readLongerWithNull(String text){
        System.out.print("Enter " + text + " long value:\n~~ ");
        String str = src.nextLine();
        if (str.equals("null")){
            return null;
        }
        if (str.isEmpty() || str.isBlank()){
            System.out.println("incorrect long value");
            readLongerWithNull(text);
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e){
            System.out.println("incorrect long value");
            return readLongerWithNull(text);
        }

    }
    private static Long readLonger(String text, long limit){
        System.out.print("Enter " + text + " long value (должно быть больше " + limit + " ):\n~~ ");
        String str = src.nextLine();

        try {
            if (str.isEmpty() || str.isBlank() || Long.parseLong(str)<limit){
                readLonger(text, limit);
            }
            return Long.parseLong(str);
        } catch (NumberFormatException e){
            System.out.println("incorrect long value");
            return readLonger(text, limit);
        }

    }

    private static TicketType readTicketType(String text){
        System.out.print("Enter" + text + " ticket type (VIP, USUAL, CHEAP):\n~~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank()){
            readTicketType(text);
        }
        try {
            return TicketType.valueOf(str);
        } catch (IllegalArgumentException e){
            System.out.println("incorrect ticket type value");
            return readTicketType(text);
        }
    }

    private static boolean makeEvent(){
        System.out.println("Do you want to make event? (yes/no)");
        String str = src.nextLine();
        switch (str){
            case "yes" -> {
                return true;
            }
            case "no" -> {
                return false;
            }
            default -> {
                return makeEvent();
            }
        }
    }
}
