package server.commands;

import server.Server;
import server.commands.interfaces.Command;
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
    public String execute(String s) throws CommandValueException {
        int count = 0;
        int ticketsCount;
        try {
            ticketsCount = Integer.parseInt(s);
        } catch (NumberFormatException ignored) {
            throw new CommandValueException("int");
        }
        for (Ticket ticket : server.getListManager().getTicketList()) {
            if (ticket.getEvent() != null && ticket.getEvent().getTicketsCount() > ticketsCount) {
                count += 1;
            }
        }
        return "Count events greater than " + s + " by ticket count: " + count + "\n";
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
