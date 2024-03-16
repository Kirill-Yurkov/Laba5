package server;

import lombok.Getter;
import lombok.Setter;
import server.managers.FileManager;
import server.managers.ListManager;
import server.patternclass.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Server {
    @Getter
    private static String filePath;

    public static void setFilePath(String filePath) {
        Server.filePath = filePath;
        ListManager.readTicketList(filePath);
    }
}
