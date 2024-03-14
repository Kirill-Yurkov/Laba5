package org.example;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import server.commands.Command;
import server.managers.FileManager;
import server.patternclass.Coordinates;
import server.patternclass.Event;
import server.patternclass.Ticket;
import server.patternclass.TicketType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Console console = System.console();


        String filePath = "C:\\Users\\Ender\\IdeaProjects\\Laba5\\src\\main\\resources\\Collection.xml";
        // Запись данных в XML файл

        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket(2,"Sergey", new Coordinates((long) 421, (long) 194), new Date(), 1000, TicketType.VIP, new Event(2, "CINEMA", 18L, 50, "MARVEL")));
        ticketList.add(new Ticket(2,"Sergey", new Coordinates((long) 421, (long) 194), new Date(), 1000, TicketType.VIP, new Event(2, "CINEMA", 18L, 50, "MARVEL")));
        ticketList.add(new Ticket(2,"Sergey", new Coordinates((long) 421, (long) 194), new Date(), 1000, TicketType.VIP, new Event(2, "CINEMA", 18L, 50, "MARVEL")));
        FileManager.writeXML(filePath, ticketList);

        // Чтение данных из XML файл
        ticketList.clear();
        System.out.println(FileManager.readXML(filePath));

    }
}