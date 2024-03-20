package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.exceptions.CommandValueException;
import server.patternclass.Ticket;
import server.patternclass.TicketType;
import server.utilities.CommandValues;

public class FilterLessThanType implements Command {
    private Server server;
    @Override
    public CommandValues getValue() {
        return CommandValues.VALUE;
    }

    @Override
    public void setServer(Server server) {
        this.server=server;
    }

    @Override
    public String execute(String s) throws CommandValueException, CommandCollectionZeroException {
        TicketType type;
        StringBuilder str = new StringBuilder();
        try {
            type = TicketType.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new CommandValueException("type");
        }
        if(server.getListManager().getTicketList().isEmpty()){
            throw new CommandCollectionZeroException("collection is empty");
        }
        for(Ticket ticket: server.getListManager().getTicketList()){
            if(type.getPriority()<ticket.getType().getPriority()){
                str.append(ticket).append("\n");
            }
        }
        return String.valueOf(str);
    }

    @Override
    public String getName() {
        return "filter_less_than_type";
    }

    @Override
    public String description() {
        return "вывести элементы, значение поля type (VIP>USUAL>CHEAP) которых меньше заданного";
    }
}
