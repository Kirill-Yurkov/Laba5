package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class Show implements Command {
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
    public String execute(String s) {
        StringBuilder str = new StringBuilder();
        for (Ticket ticket : server.getListManager().getTicketList()) {
            str.append(ticket.toString()).append("\n");
        }
        return String.valueOf(str);
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String description() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
