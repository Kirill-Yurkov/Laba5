package server.commands;

import lombok.Getter;
import lombok.Setter;
import server.Server;
import server.commands.interfaces.Command;
import server.managers.FileManager;
import server.managers.ListManager;
public class Save implements Command {
    private Server server;
    @Override
    public void setServer(Server server) {
        this.server = server;
    }
    @Override
    public String execute() {
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
