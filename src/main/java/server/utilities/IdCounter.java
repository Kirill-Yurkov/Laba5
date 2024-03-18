package server.utilities;

import lombok.Getter;
import server.Server;
import server.patternclass.Event;
import server.patternclass.Ticket;

import java.util.HashMap;

public class IdCounter {
    private HashMap<Long, Ticket> idTickets = new HashMap<>();
    private HashMap<Integer, Event> idEvents = new HashMap<>();
    @Getter
    private Server server;

    public IdCounter(Server server) {
        this.server = server;
    }

    public long getIdForTicket(Ticket ticket) {
        for (long i : idTickets.keySet()) {
            if (idTickets.get(i) == null) {
                idTickets.put(i, ticket);
                return i;
            }
        }
        idTickets.put((long) (idTickets.size() + 1), ticket);
        return idTickets.size();
    }

    public void delTicketByID(long i) {
        idTickets.put(i, null);
    }

    public void initializeIdTickets() {
        long maxi = 0;
        for (Ticket ticket : server.getListManager().getTicketList()) {
            if (ticket.getId() > maxi) {
                maxi = ticket.getId();
            }
        }
        for (long i = 1; i < Math.max(maxi, idTickets.size() + 1); i++) {
            idTickets.put(i, null);
        }
        for (Ticket ticket : server.getListManager().getTicketList()) {
            idTickets.put(ticket.getId(), ticket);
        }
        initializeIdEvents();
    }

    public int getIdForEvent(Event event) {
        for (int i : idEvents.keySet()) {
            if (idEvents.get(i) == null) {
                idEvents.put(i, event);
                return i;
            }
        }
        idEvents.put(idEvents.size() + 1, event);
        return idEvents.size();
    }

    public void initializeIdEvents() {
        int maxi = 0;
        for (Ticket ticket : server.getListManager().getTicketList()) {
            try {
                if (ticket.getEvent().getId() > maxi) {
                    maxi = ticket.getEvent().getId();
                }
            } catch (NullPointerException ignored) {

            }

        }
        for (int i = 1; i < Math.max(maxi, idEvents.size() + 1); i++) {
            idEvents.put(i, null);
        }
        for (Ticket ticket : server.getListManager().getTicketList()) {
            try {
                idEvents.put(ticket.getEvent().getId(), ticket.getEvent());
            } catch (NullPointerException ignored) {
            }
        }
    }
}
