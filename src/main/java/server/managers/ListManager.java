package server.managers;

import lombok.Getter;
import server.Server;
import server.patternclass.Ticket;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListManager {
    private List<Ticket> ticketList = new ArrayList<>();
    private Server server;

    public ListManager(Server server) {
        this.server = server;
    }

    public void setTicketList(List<Ticket> tickets) {
        ticketList = tickets;
        server.getIdCounter().initializeIdTickets();
    }

    public void add(Ticket ticket) {
        ticketList.add(ticket);
    }

    public void remove(Ticket ticket) {
        ticketList.remove(ticket);
        server.getIdCounter().delTicketByID(ticket.getId());
    }

    public void readTicketList() {
        ticketList = server.getReaderWriter().getCollectionTicket();
        server.getIdCounter().initializeIdTickets();
    }
}
