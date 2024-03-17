package server.commands;

import server.commands.interfaces.Command;
import server.utilities.TicketCreator;
import server.managers.ListManager;

public class Add implements Command {
    @Override
    public String execute(){
        ListManager.add(TicketCreator.createTicketGroup());
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
