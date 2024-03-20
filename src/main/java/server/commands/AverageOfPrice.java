package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class AverageOfPrice implements Command {
    private Server server;

    @Override
    public CommandValues getValue() {
        return CommandValues.NOTHING;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute(String s) throws CommandCollectionZeroException {
        int price = 0;
        for (Ticket ticket : server.getListManager().getTicketList()) {
            if (ticket.getPrice() != null) {
                price += ticket.getPrice();
            }
        }
        if (server.getListManager().getTicketList().isEmpty()) {
            throw new CommandCollectionZeroException("collection is zero");
        } else {
            return String.valueOf(price / server.getListManager().getTicketList().size());
        }
    }

    @Override
    public String getName() {
        return "average_of_price";
    }

    @Override
    public String description() {
        return "вывести среднее значение поля price для всех элементов коллекции";
    }
}
