package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.managers.FileManager;

public class Save implements Command {
    @Override
    public String execute() {
        FileManager.writeXML(Server.getFilePath(),Server.getTicketList());
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
