package server.commons;

import org.apache.maven.surefire.shared.lang3.ArrayUtils;
import server.managers.ListManager;
import server.patternclass.Event;
import server.patternclass.Ticket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;

public class IdCounter {
    private static HashMap<Long, Ticket> idTickets = new HashMap<>();
    private static HashMap<Integer, Event> idEvents = new HashMap<>();

    public static void setIdTickets(HashMap<Long, Ticket> idTickets) {
        IdCounter.idTickets = idTickets;
    }

    public static long getIdForTicket(Ticket ticket){
        for(long i: idTickets.keySet()){
            if(idTickets.get(i) == null){
                idTickets.put(i, ticket);
                return i;
            }
        }
        idTickets.put((long) (idTickets.size()+1), ticket);
        return idTickets.size();
    }
    public static void delTicketByID(long i){
        idTickets.put(i, null);
    }
    public static void initializeIdTickets(){
        long maxi = 0;
        for(Ticket ticket : ListManager.getTicketList()){
            if (ticket.getId()>maxi){
                maxi = ticket.getId();
            }
        }
        for(long i = 1; i < maxi; i++){
            idTickets.put(i, null);
        }
        for(Ticket ticket : ListManager.getTicketList()){
            idTickets.put(ticket.getId(), ticket);
        }
    }
}
