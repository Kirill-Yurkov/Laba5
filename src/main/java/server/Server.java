package server;

import lombok.Getter;
import lombok.Setter;
import server.managers.FileManager;
import server.patternclass.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Server {
    @Getter
    private static String filePath;
    @Getter
    @Setter
    private static List<Ticket> ticketList = new ArrayList<>();

    public static void add(Ticket ticket) {
        ticketList.add(ticket);
    }

    public static void remove(Ticket ticket) {
        ticketList.remove(ticket);
    }

    public static void setFilePath(String filePath) {
        Server.filePath = filePath;
        ticketList = FileManager.readXML(filePath);
    }
}
