package server.managers;

import lombok.Getter;
import lombok.Setter;
import server.commons.IdCounter;
import server.patternclass.Ticket;

import java.util.ArrayList;
import java.util.List;

public class ListManager {
    @Getter
    private static List<Ticket> ticketList = new ArrayList<>();

    public static void setTicketList(List<Ticket> ticketList) {
        ListManager.ticketList = ticketList;
    }

    public static void add(Ticket ticket) {
        ticketList.add(ticket);
    }

    public static void remove(Ticket ticket) {
        ticketList.remove(ticket);
    }
    public static void readTicketList(String filePath){
        ticketList = FileManager.ReaderWriter.readXML(filePath);
        IdCounter.initializeIdTickets();
    }
}
