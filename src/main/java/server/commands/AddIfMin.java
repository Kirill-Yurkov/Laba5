package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandValueException;
import server.exceptions.StopServerException;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class AddIfMin implements Command {
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
    public String execute(String s) throws StopServerException, CommandValueException {
        Ticket ticket = server.getTicketCreator().createTicketGroup();
        int mini = Integer.MAX_VALUE;
        for (Ticket localTicket : server.getListManager().getTicketList()) {
            if (localTicket.getPrice() != null && localTicket.getPrice()<mini) {
                mini = localTicket.getPrice();
            }
        }
        if(ticket.getPrice()<mini){
            ticket.setId(server.getIdCounter().getIdForTicket(ticket));
            if(ticket.getEvent()!=null){
                ticket.getEvent().setId(server.getIdCounter().getIdForEvent(ticket.getEvent()));
            }
            return "successfully";
        }
        throw new CommandValueException("price more than minimal");
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String description() {
        return "добавить новый элемент в коллекцию, если его значение меньше (price), чем у наименьшего элемента этой коллекции";
    }
}
