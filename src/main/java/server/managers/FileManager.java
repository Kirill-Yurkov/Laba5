package server.managers;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import server.Server;
import server.patternclass.Coordinates;
import server.patternclass.Event;
import server.patternclass.Ticket;
import server.patternclass.TicketType;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileManager {
    @Getter
    private Server server;
    @Getter
    private String filePath;
    private Document document;

    public void setFilePath(String filePath) {
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        this.filePath = filePath;
    }

    public FileManager(Server server) {
        this.server = server;
    }
    @Getter
    @Setter
    public class InputOutput{
        private BufferedReader reader;
        private BufferedWriter writer;
        public String outPut(String text){
            return text;
        }
    }

    public class ReaderWriter {
        @Getter
        @Setter
        private List<String> collectionInfo = new ArrayList<>();

        @Setter
        @Getter
        private List<Ticket> collectionTicket = new ArrayList<>();

        public void readXML() {
            setCollectionTicket(readTickets(document));
            try {
                setCollectionInfo(readCollectionInfo(document));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        private List<Ticket> readTickets(Document document) {
            List<Ticket> tickets = new ArrayList<>();
            try {
                Element root = document.getDocumentElement();
                NodeList ticketList = root.getElementsByTagName("Ticket");
                for (int i = 0; i < ticketList.getLength(); i++) {
                    Node ticketNode = ticketList.item(i);
                    if (ticketNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element ticketElement = (Element) ticketNode;
                        long ticketId = Long.parseLong(ticketElement.getElementsByTagName("TicketId").item(0).getTextContent());
                        String ticketName = ticketElement.getElementsByTagName("TicketName").item(0).getTextContent();
                        long coordinateX = Long.parseLong(ticketElement.getElementsByTagName("CoordinateX").item(0).getTextContent());
                        long coordinateY = Long.parseLong(ticketElement.getElementsByTagName("CoordinateY").item(0).getTextContent());
                        String date1 = ticketElement.getElementsByTagName("TicketCreationDate").item(0).getTextContent();
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE LLL d HH:m:s z yyyy", Locale.ENGLISH);
                        Date date = formatter.parse(date1);
                        Integer ticketPrice;
                        if (ticketElement.getElementsByTagName("TicketPrice").item(0).getTextContent().equals("null")) {
                            ticketPrice = null;
                        } else {
                            ticketPrice = Integer.parseInt(ticketElement.getElementsByTagName("TicketPrice").item(0).getTextContent());
                        }
                        TicketType ticketType = TicketType.valueOf(ticketElement.getElementsByTagName("TicketType").item(0).getTextContent());
                        try {
                            Integer eventId = Integer.parseInt(ticketElement.getElementsByTagName("EventId").item(0).getTextContent());
                            String eventName = ticketElement.getElementsByTagName("EventName").item(0).getTextContent();
                            Long eventMinAge;
                            if (ticketElement.getElementsByTagName("EventMinAge").item(0).getTextContent().equals("null")) {
                                eventMinAge = null;
                            } else {
                                eventMinAge = Long.parseLong(ticketElement.getElementsByTagName("EventMinAge").item(0).getTextContent());
                            }
                            int eventTicketsCount = Integer.parseInt(ticketElement.getElementsByTagName("EventTicketsCount").item(0).getTextContent());
                            String eventDescription = ticketElement.getElementsByTagName("EventDescription").item(0).getTextContent();
                            Event event = new Event(eventName, eventMinAge, eventTicketsCount, eventDescription);
                            event.setId(eventId);
                            Ticket ticket = new Ticket(
                                    ticketName,
                                    new Coordinates(coordinateX, coordinateY),
                                    date,
                                    ticketPrice,
                                    ticketType,
                                    event
                            );
                            ticket.setId(ticketId);
                            tickets.add(ticket);
                        } catch (NullPointerException ignored) {
                            Ticket ticket = new Ticket(
                                    ticketName,
                                    new Coordinates(coordinateX, coordinateY),
                                    date,
                                    ticketPrice,
                                    ticketType,
                                    null
                            );
                            ticket.setId(ticketId);
                            tickets.add(ticket);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tickets;
        }

        public void writeXML(String filePath, List<Ticket> tickets) {
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
                cleanup(document);
                remove(document,"Ticket");
                remove(document, "Collection");
                fillCollectionInfo(document);
                fill(document, tickets);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new BufferedOutputStream(new FileOutputStream(filePath)));
                transformer.transform(source, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void fill(Document document, List<Ticket> tickets) {
            Node root = document.getDocumentElement();

            for (Ticket ticket : tickets) {
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
                try {
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
                    eventTicketsCount.setTextContent(String.valueOf(ticket.getEvent().getTicketsCount()));
                    elementList.add(eventTicketsCount);

                    Element eventDescription = document.createElement("EventDescription");
                    eventDescription.setTextContent(ticket.getEvent().getDescription());
                    elementList.add(eventDescription);
                } catch (NullPointerException ignored) {
                }

                for (Element element : elementList) {
                    currTicket.appendChild(element);
                }
                root.appendChild(currTicket);
            }
        }

        private void remove(Document document, String removing) {
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

        private void cleanup(Document document) throws XPathExpressionException {
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", document, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); ++i) {
                Node node = nl.item(i);
                node.getParentNode().removeChild(node);
            }
        }

        private List<String> readCollectionInfo(Document document) throws ParseException {
            Element root = document.getDocumentElement();
            Node ticketNode = root.getElementsByTagName("Collection").item(0);
            if (ticketNode.getNodeType() == Node.ELEMENT_NODE) {
                Element collection = (Element) ticketNode;
                String typeCollection = collection.getElementsByTagName("CollectionType").item(0).getTextContent();
                String date1 = collection.getElementsByTagName("CollectionCreationDate").item(0).getTextContent();
                SimpleDateFormat formatter = new SimpleDateFormat("EEE LLL d HH:m:s z yyyy", Locale.ENGLISH);
                formatter.setLenient(false);
                Date date = formatter.parse(date1);
                int countCollection = Integer.parseInt(collection.getElementsByTagName("CollectionCount").item(0).getTextContent());
                String xmlVersionCollection = collection.getElementsByTagName("CollectionXmlVersion").item(0).getTextContent();
                String xmlEncoding = collection.getElementsByTagName("CollectionXmlEncoding").item(0).getTextContent();
                List<String> list = new ArrayList<>();
                list.add(typeCollection);
                list.add(String.valueOf(date));
                list.add(String.valueOf(countCollection));
                list.add(xmlVersionCollection);
                list.add(xmlEncoding);
                return list;
            }
            return new ArrayList<>();
        }

        private void fillCollectionInfo(Document document) {
            List<Element> list = new ArrayList<>();
            Node root = document.getDocumentElement();
            Element collection = document.createElement("Collection");
            Element typeCollection = collection.getOwnerDocument().createElement("CollectionType");
            typeCollection.setTextContent("CSV");
            list.add(typeCollection);

            Element date = collection.getOwnerDocument().createElement("CollectionCreationDate");
            date.setTextContent(String.valueOf(new Date()));
            list.add(date);

            Element countCollection = collection.getOwnerDocument().createElement("CollectionCount");
            countCollection.setTextContent(String.valueOf(server.getListManager().getTicketList().size()));
            list.add(countCollection);

            Element xmlVersionCollection = collection.getOwnerDocument().createElement("CollectionXmlVersion");
            xmlVersionCollection.setTextContent(document.getXmlVersion());
            list.add(xmlVersionCollection);

            Element xmlEncoding = collection.getOwnerDocument().createElement("CollectionXmlEncoding");
            xmlEncoding.setTextContent(document.getXmlEncoding());
            list.add(xmlEncoding);
            for (Element element : list) {
                collection.appendChild(element);
            }
            root.appendChild(collection);
        }
    }
}
