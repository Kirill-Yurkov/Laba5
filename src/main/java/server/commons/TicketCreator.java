package server.commons;

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
        createdTicketGroup.setId(readInt("id"));
        createdTicketGroup.setName(readName("owner"));
        createdTicketGroup.setCoordinates(new Coordinates(
                readLong("coordinates X"),
                readLong("coordinates Y")));
        createdTicketGroup.setCreationDate(new Date());
        createdTicketGroup.setPrice(readInt("price"));
        createdTicketGroup.setType(readTicketType(""));
        createdTicketGroup.setEvent(new Event(
                readInt("event id"),
                readName("event"),
                readLong("event min age"),
                readInt("event tickets count"),
                readName("event description")
        ));
        return createdTicketGroup;
    }

    public static String readName(String text){
        System.out.print("Enter " + text + " name:\n~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank()){
            readName(text);
        }
        return str;
    }
    public static int readInt(String text){
        System.out.print("Enter " + text + " int value:\n~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank()){
            readInt(text);
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            System.out.println("incorrect int value");
            return readInt(text);
        }

    }

    public static long readLong(String text){
        System.out.print("Enter " + text + " long value:\n~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank()){
            readLong(text);
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e){
            System.out.println("incorrect long value");
            return readLong(text);
        }

    }

    public static TicketType readTicketType(String text){
        System.out.print("Enter " + text + " ticket type (VIP, USUAL, CHEAP):\n~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank()){
            readLong(text);
        }
        try {
            return TicketType.valueOf(str);
        } catch (IllegalArgumentException e){
            System.out.println("incorrect ticket type value");
            return readTicketType(text);
        }
    }
}
