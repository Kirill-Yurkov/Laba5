package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.patternclass.Ticket;

public class Show implements Command {
    private Server server;

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute() {
        StringBuilder s = new StringBuilder();
        for (Ticket ticket : server.getListManager().getTicketList()) {
            s.append("\n").append(ticket.toString());
        }
        return String.valueOf(s);
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
