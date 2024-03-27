package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.exceptions.CommandValueException;
import server.exceptions.StopCreateTicketException;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class Update implements Command {
    private Server server;
    @Override
    public CommandValues getValue() {
        return CommandValues.VALUE_ELEMENT;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute(String value) throws CommandValueException, CommandCollectionZeroException {
        long id;
        try {
            id = Long.parseLong(value);
        } catch (NumberFormatException ignored){
            throw new CommandValueException("long");
        }
        if(server.getListManager().getTicketList().isEmpty()){
            throw new CommandCollectionZeroException("collection is zero");
        }
        for(Ticket ticket: server.getListManager().getTicketList()){
            if (ticket.getId() == id){
                server.getListManager().remove(ticket);
                try {
                    Ticket newTicket = server.getTicketCreator().createTicketGroup();
                    newTicket.setId(id);
                    if(newTicket.getEvent()!=null){
                        newTicket.getEvent().setId(server.getIdCounter().getIdForEvent(newTicket.getEvent()));
                    }
                    server.getListManager().add(newTicket);
                    return "successfully";
                } catch (StopCreateTicketException e) {
                    return null;
                }
            }
        }
        throw new CommandValueException("id not find");
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String description() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
