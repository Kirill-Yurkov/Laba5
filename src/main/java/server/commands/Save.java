package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.managers.FileManager;
import server.managers.ListManager;

public class Save implements Command {
    @Override
    public String execute() {
        FileManager.ReaderWriter.writeXML(Server.getFilePath(), ListManager.getTicketList());
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
