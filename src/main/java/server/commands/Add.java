package server.commands;

import lombok.Getter;
import lombok.Setter;
import server.Server;
import server.commands.interfaces.Command;
import server.utilities.TicketCreator;
import server.managers.ListManager;

public class Add implements Command {
    private Server server;

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute(){
        server.getListManager().add(server.getTicketCreator().createTicketGroup());
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
