package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.exceptions.CommandValueException;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class RemoveById implements Command {
    private Server server;
    @Override
    public CommandValues getValue() {
        return CommandValues.VALUE;
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
            throw new CommandCollectionZeroException("collection is empty");
        }
        for(Ticket ticket: server.getListManager().getTicketList()){
            if(ticket.getId() == id){
                server.getListManager().remove(ticket);
                return "successfully";
            }
        }
        throw new CommandValueException("id not find");
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String description() {
        return "удалить элемент из коллекции по его id";
    }
}
