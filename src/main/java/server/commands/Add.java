package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.commons.TicketCreator;

public class Add implements Command {
    @Override
    public String execute(){
        Server.add(TicketCreator.createTicketGroup());
        return "Successfully created";
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String description() {
        return "добавить новый элемент в коллекцию";
    }
}
