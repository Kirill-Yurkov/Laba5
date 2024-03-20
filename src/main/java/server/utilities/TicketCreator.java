package server.utilities;

import lombok.Getter;
import server.Server;
import server.patternclass.Coordinates;
import server.patternclass.Event;
import server.patternclass.Ticket;
import server.patternclass.TicketType;

import java.util.Date;

@Getter
public class TicketCreator {
    private Server server;

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
            createdTicketGroup.setEvent(event);
        }
        return createdTicketGroup;
    }

    private String readName(String text) {
        server.outPut("Enter " + text + " name:\n~~ ");
        String str = server.inPut();
        if (!Validator.isValidString(str)) {
            server.outPut("Incorrect string value\n");
            return readName(text);
        }
        return str;
    }

    private String readNameWithNull(String text) {
        server.outPut("Enter " + text + " name:\n~~ ");
        String str = server.inPut();
        if (Validator.isValidStringWithNull(str) == null) {
            return null;
        } else if (!Boolean.TRUE.equals(Validator.isValidStringWithNull(str))) {
            server.outPut("Incorrect string value\n");
            return readNameWithNull(text);
        }
        return str;
    }

    private Integer readInteger(String text, int limit) {
        server.outPut("Enter " + text + " int value (должно быть больше " + limit + "):\n~~ ");
        String str = server.inPut();
        if (!Validator.isValidInteger(str, limit)) {
            server.outPut("Incorrect int value\n");
            return readInteger(text, limit);
        }
        return Integer.parseInt(str);
    }

    private Integer readIntegerWithNull(String text, int limit) {
        server.outPut("Enter " + text + " int value (должно быть больше " + limit + "):\n~~ ");
        String str = server.inPut();
        if (Validator.isValidIntegerWithNull(str, limit) == null) {
            return null;
        } else if (!Boolean.TRUE.equals(Validator.isValidIntegerWithNull(str, limit))) {
            server.outPut("Incorrect int value\n");
            return readIntegerWithNull(text, limit);
        }
        return Integer.parseInt(str);
    }


    private Long readLonger(String text, long limit) {
        server.outPut("Enter " + text + " long value (должно быть больше " + limit + " ):\n~~ ");
        String str = server.inPut();
        if (!Validator.isValidLonger(str, limit)) {
            server.outPut("Incorrect long value\n");
            return readLonger(text, limit);
        }
        return Long.parseLong(str);
    }

    private Long readLongerWithNull(String text) {
        server.outPut("Enter " + text + " long value:\n~~ ");
        String str = server.inPut();
        if (Validator.isValidLongerWithNull(str) == null) {
            return null;
        } else if (!Boolean.TRUE.equals(Validator.isValidLongerWithNull(str))) {
            server.outPut("Incorrect long value\n");
            return readLongerWithNull(text);
        }
        return Long.parseLong(str);
    }

    private TicketType readTicketType(String text) {
        server.outPut("Enter" + text + " ticket type (VIP, USUAL, CHEAP):\n~~ ");
        String str = server.inPut();
        if(!Validator.isValidTicketType(str)){
            server.outPut("incorrect ticket type value\n");
            return readTicketType(text);
        }
        return TicketType.valueOf(str);
    }

    private boolean makeEvent() {
        server.outPut("Do you want to make event? (yes/no)\n~~ ");
        String str = server.inPut();
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
}
