package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class Add implements Command {
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
    public String execute(String s) {
        Ticket ticket = server.getTicketCreator().createTicketGroup();
        ticket.setId(server.getIdCounter().getIdForTicket(ticket));
        if(ticket.getEvent()!=null){
            ticket.getEvent().setId(server.getIdCounter().getIdForEvent(ticket.getEvent()));
        }
        server.getListManager().add(ticket);
        return "Successfully created";
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String description() {
        return "добавить новый элемент в коллекцию";
    }
}
