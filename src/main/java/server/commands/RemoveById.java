package server.commands;

import server.Server;
import server.commands.interfaces.Command;
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
    public String execute(String s) throws CommandValueException {
        long id;
        try {
            id = Long.parseLong(s);
        } catch (NumberFormatException ignored){
            throw new CommandValueException("long");
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
