package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.StopServerException;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

import java.util.ArrayList;
import java.util.List;

public class RemoveLower implements Command {
    private Server server;
    @Override
    public CommandValues getValue() {
        return CommandValues.ELEMENT;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute(String s) throws StopServerException {
        Ticket newTicket = server.getTicketCreator().createTicketGroup();
        List<Ticket> removeList = new ArrayList<>();
        for(Ticket ticket: server.getListManager().getTicketList()){
            if(ticket.getPrice()< newTicket.getPrice()){
                removeList.add(ticket);
            }
        }
        for(Ticket ticket: removeList){
            server.getListManager().getTicketList().remove(ticket);
        }
        return "successfully";
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    @Override
    public String description() {
        return "удалить из коллекции все элементы, меньшие, чем заданный(по price)";
    }
}
