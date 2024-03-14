package server.commands;

import server.patternclass.Ticket;

import java.util.List;

public class Add extends Command{

    public void exec(List<Ticket> ticketList, Ticket ticket){
        ticketList.add(ticket);
    }



}
