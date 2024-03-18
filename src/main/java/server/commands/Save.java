package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.utilities.CommandValues;

public class Save implements Command {
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
        server.getReaderWriter().writeXML(server.getFileManager().getFilePath(), server.getListManager().getTicketList());
        return "successfully";
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String description() {
        return "сохранить коллекцию в файл";
    }
}
