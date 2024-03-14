package server.managers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import server.patternclass.Coordinates;
import server.patternclass.Event;
import server.patternclass.Ticket;
import server.patternclass.TicketType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileManager {
    public static List<Ticket> readXML(String filePath) {
        List<Ticket> tickets = new ArrayList<>();
        try {

            // Получаем объект Document из файла
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));

            // Получаем корневой элемент
            Element root = document.getDocumentElement();

            // Получаем список всех элементов с тегом "ticket"
            NodeList ticketList = root.getElementsByTagName("Ticket");

            // Обходим все элементы "ticket" и выводим информацию о них
            for (int i = 0; i < ticketList.getLength(); i++) {
                Node ticketNode = ticketList.item(i);
                if (ticketNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ticketElement = (Element) ticketNode;
                    long ticketId = Long.parseLong(ticketElement.getElementsByTagName("TicketId").item(0).getTextContent());
                    String ticketName = ticketElement.getElementsByTagName("TicketName").item(0).getTextContent();
                    long coordinateX = Long.parseLong(ticketElement.getElementsByTagName("CoordinateX").item(0).getTextContent());
                    long coordinateY = Long.parseLong(ticketElement.getElementsByTagName("CoordinateY").item(0).getTextContent());
                    String date1 = ticketElement.getElementsByTagName("TicketCreationDate").item(0).getTextContent();
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE LLL d k:m:s z u", Locale.ENGLISH);
                    Date date = formatter.parse(date1);
                    int ticketPrice = Integer.parseInt(ticketElement.getElementsByTagName("TicketPrice").item(0).getTextContent());
                    TicketType ticketType = TicketType.valueOf(ticketElement.getElementsByTagName("TicketType").item(0).getTextContent());
                    int eventId = Integer.parseInt(ticketElement.getElementsByTagName("EventId").item(0).getTextContent());
                    String eventName = ticketElement.getElementsByTagName("EventName").item(0).getTextContent();
                    long eventMinAge = Long.parseLong(ticketElement.getElementsByTagName("EventMinAge").item(0).getTextContent());
                    int eventTicketsCount = Integer.parseInt(ticketElement.getElementsByTagName("EventTicketsCount").item(0).getTextContent());
                    String eventDescription = ticketElement.getElementsByTagName("EventDescription").item(0).getTextContent();
                    tickets.add(new Ticket(
                            ticketId,
                            ticketName,
                            new Coordinates(coordinateX,coordinateY),
                            date,
                            ticketPrice,
                            ticketType,
                            new Event(eventId, eventName, eventMinAge, eventTicketsCount, eventDescription)
                            ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Метод для записи данных в XML файл
    public static void writeXML(String filePath, List<Ticket> tickets) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);
            cleanup(document);
            remove(document, "Ticket");
            fill(document, tickets);
            // Записываем содержимое в файл
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new BufferedOutputStream(new FileOutputStream(filePath)));
            transformer.transform(source, result);

            System.out.println("XML файл успешно записан!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void fill(Document document, List<Ticket> tickets){
        Node root = document.getDocumentElement();

        for (Ticket ticket: tickets) {
            List<Element> elementList = new ArrayList<>();
            Element currTicket = document.createElement("Ticket");

            Element ticketId = document.createElement("TicketId");
            ticketId.setTextContent(String.valueOf(ticket.getId()));
            elementList.add(ticketId);

            Element ticketName = document.createElement("TicketName");
            ticketName.setTextContent(ticket.getName());
            elementList.add(ticketName);

            Element coordinateX = document.createElement("CoordinateX");
            coordinateX.setTextContent(String.valueOf(ticket.getCoordinates().getX()));
            elementList.add(coordinateX);

            Element coordinateY = document.createElement("CoordinateY");
            coordinateY.setTextContent(String.valueOf(ticket.getCoordinates().getY()));
            elementList.add(coordinateY);

            Element ticketCreationDate = document.createElement("TicketCreationDate");
            ticketCreationDate.setTextContent(String.valueOf(ticket.getCreationDate()));
            elementList.add(ticketCreationDate);

            Element ticketPrice = document.createElement("TicketPrice");
            ticketPrice.setTextContent(String.valueOf(ticket.getPrice()));
            elementList.add(ticketPrice);

            Element ticketType = document.createElement("TicketType");
            ticketType.setTextContent(String.valueOf(ticket.getType()));
            elementList.add(ticketType);

            Element eventId = document.createElement("EventId");
            eventId.setTextContent(String.valueOf(ticket.getEvent().getId()));
            elementList.add(eventId);

            Element eventName = document.createElement("EventName");
            eventName.setTextContent(ticket.getEvent().getName());
            elementList.add(eventName);

            Element eventMinAge = document.createElement("EventMinAge");
            eventMinAge.setTextContent(String.valueOf(ticket.getEvent().getMinAge()));
            elementList.add(eventMinAge);

            Element eventTicketsCount = document.createElement("EventTicketsCount");
            eventTicketsCount.setTextContent(String.valueOf(ticket.getEvent().getMinAge()));
            elementList.add(eventTicketsCount);

            Element eventDescription = document.createElement("EventDescription");
            eventDescription.setTextContent(ticket.getEvent().getDescription());
            elementList.add(eventDescription);

            for (Element element : elementList) {
                currTicket.appendChild(element);
            }
            root.appendChild(currTicket);
        }
    }
    private static void remove(Document document, String removing) throws XPathExpressionException {
        Element root = document.getDocumentElement();
        NodeList elementList = root.getElementsByTagName(removing);
        Node[] elementsToRemove = new Node[elementList.getLength()];
        for (int i = 0; i < elementList.getLength(); i++) {
            Node node = elementList.item(i);
            elementsToRemove[i] = node;
        }
        for (Node node : elementsToRemove) {
            root.removeChild(node);
        }
    }
    private static void cleanup(Document document) throws XPathExpressionException {
        XPath xp = XPathFactory.newInstance().newXPath();
        NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", document, XPathConstants.NODESET);

        for (int i=0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            node.getParentNode().removeChild(node);
        }
    }
}
