package server.utilities;

import server.managers.ListManager;
import server.patternclass.Event;
import server.patternclass.Ticket;

import java.util.HashMap;

public class IdCounter {
    private static HashMap<Long, Ticket> idTickets = new HashMap<>();
    private static HashMap<Integer, Event> idEvents = new HashMap<>();


    public static long getIdForTicket(Ticket ticket) {
        for (long i : idTickets.keySet()) {
            if (idTickets.get(i) == null) {
                idTickets.put(i, ticket);
                return i;
            }
        }
        idTickets.put((long) (idTickets.size() + 1), ticket);
        return idTickets.size();
    }

    public static void delTicketByID(long i) {
        idTickets.put(i, null);
    }

    public static void initializeIdTickets() {
        long maxi = 0;
        for (Ticket ticket : ListManager.getTicketList()) {
            if (ticket.getId() > maxi) {
                maxi = ticket.getId();
            }
        }
        for (long i = 1; i < Math.max(maxi,idTickets.size()+1); i++) {
            idTickets.put(i, null);
        }
        for (Ticket ticket : ListManager.getTicketList()) {
            idTickets.put(ticket.getId(), ticket);
        }
        initializeIdEvents();
    }

    public static int getIdForEvent(Event event) {
        for (int i : idEvents.keySet()) {
            if (idEvents.get(i) == null) {
                idEvents.put(i, event);
                return i;
            }
        }
        idEvents.put(idEvents.size() + 1, event);
        return idEvents.size();
    }
    public static void initializeIdEvents() {
        int maxi = 0;
        for (Ticket ticket : ListManager.getTicketList()) {
            try {
                if (ticket.getEvent().getId() > maxi) {
                    maxi = ticket.getEvent().getId();
                }
            }catch (NullPointerException ignored){

            }

        }
        for (int i = 1; i < Math.max(maxi,idEvents.size()+1); i++) {
            idEvents.put(i, null);
        }
        for (Ticket ticket : ListManager.getTicketList()) {
            try {
                idEvents.put(ticket.getEvent().getId(), ticket.getEvent());
            } catch (NullPointerException ignored) {

            }
        }
    }
}
