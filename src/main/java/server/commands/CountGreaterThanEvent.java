package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.exceptions.CommandValueException;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class CountGreaterThanEvent implements Command {
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
        int count = 0;
        int ticketsCount;
        try {
            ticketsCount = Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            throw new CommandValueException("int");
        }
        if(server.getListManager().getTicketList().isEmpty()){
            throw new CommandCollectionZeroException("collection is zero");
        }
        for (Ticket ticket : server.getListManager().getTicketList()) {
            if (ticket.getEvent() != null && ticket.getEvent().getTicketsCount() > ticketsCount) {
                count += 1;
            }
        }
        return "Count events greater than " + value + " by ticket count: " + count + "\n";
    }

    @Override
    public String getName() {
        return "count_greater_than_event";
    }

    @Override
    public String description() {
        return "вывести количество элементов, значение поля event(tickets count) которых больше заданного";
    }
}
