package server.utilities;

import lombok.Getter;
import server.Server;
import server.patternclass.Coordinates;
import server.patternclass.Event;
import server.patternclass.Ticket;
import server.patternclass.TicketType;

import java.util.Date;
import java.util.Scanner;

public class TicketCreator {
    @Getter
    private Server server;
    private final Scanner src = new Scanner(System.in);

    public TicketCreator(Server server) {
        this.server = server;
    }

    public Ticket createTicketGroup() {
        Ticket createdTicketGroup = new Ticket();
        createdTicketGroup.setName(readName("owner"));
        createdTicketGroup.setCoordinates(new Coordinates(
                readLonger("coordinates X", -503),
                readLonger("coordinates Y", -664)));
        createdTicketGroup.setCreationDate(new Date());
        createdTicketGroup.setPrice(readIntegerWithNull("price", 0));
        createdTicketGroup.setType(readTicketType(""));
        if (makeEvent()) {
            Event event = new Event(
                    readName("event"),
                    readLongerWithNull("event min age"),
                    readInteger("event tickets count", 0),
                    readNameWithNull("event description")
            );
            event.setId(server.getIdCounter().getIdForEvent(event));
            createdTicketGroup.setEvent(event);
        }
        createdTicketGroup.setId(server.getIdCounter().getIdForTicket(createdTicketGroup));
        return createdTicketGroup;
    }

    private String readName(String text) {
        System.out.print("Enter " + text + " name:\n~~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank() || str.equals("null")) {
            return readName(text);
        }
        return str;
    }

    private String readNameWithNull(String text) {
        System.out.print("Enter " + text + " name:\n~~ ");
        String str = src.nextLine();
        if (str.equals("null")) {
            return null;
        }
        if (str.isEmpty() || str.isBlank()) {
            return readName(text);
        }
        return str;
    }

    private Integer readIntegerWithNull(String text, int limit) {
        System.out.print("Enter " + text + " int value (должно быть больше " + limit + "):\n~~ ");
        String str = src.nextLine();

        try {
            if (str.equals("null")) {
                return null;
            }
            if (str.isEmpty() || str.isBlank() || Integer.parseInt(str) <= limit) {
                System.out.println("incorrect int value");
                return readIntegerWithNull(text, limit);
            }
            return Integer.parseInt(str);

        } catch (NumberFormatException e) {
            System.out.println("incorrect int value");
            return readIntegerWithNull(text, limit);
        }
    }

    private Integer readInteger(String text, int limit) {
        System.out.print("Enter " + text + " int value (должно быть больше " + limit + "):\n~~ ");
        String str = src.nextLine();

        try {
            if (str.isEmpty() || str.isBlank() || Integer.parseInt(str) <= limit) {
                System.out.println("incorrect int value");
                return readInteger(text, limit);
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("incorrect int value");
            return readInteger(text, limit);
        }
    }

    private Long readLongerWithNull(String text) {
        System.out.print("Enter " + text + " long value:\n~~ ");
        String str = src.nextLine();
        if (str.equals("null")) {
            return null;
        }
        if (str.isEmpty() || str.isBlank()) {
            System.out.println("incorrect long value");
            return readLongerWithNull(text);
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            System.out.println("incorrect long value");
            return readLongerWithNull(text);
        }

    }

    private Long readLonger(String text, long limit) {
        System.out.print("Enter " + text + " long value (должно быть больше " + limit + " ):\n~~ ");
        String str = src.nextLine();
        try {
            if (str.isEmpty() || str.isBlank() || Long.parseLong(str) <= limit) {
                return readLonger(text, limit);
            }
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            System.out.println("incorrect long value");
            return readLonger(text, limit);
        }

    }

    private TicketType readTicketType(String text) {
        System.out.print("Enter" + text + " ticket type (VIP, USUAL, CHEAP):\n~~ ");
        String str = src.nextLine();
        if (str.isEmpty() || str.isBlank()) {
            return readTicketType(text);
        }
        try {
            return TicketType.valueOf(str);
        } catch (IllegalArgumentException e) {
            System.out.println("incorrect ticket type value");
            return readTicketType(text);
        }
    }

    private boolean makeEvent() {
        System.out.print("Do you want to make event? (yes/no)\n~~ ");
        String str = src.nextLine();
        switch (str) {
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

    private void srcOutput(String text) {

    }
}
