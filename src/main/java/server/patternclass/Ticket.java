package server.patternclass;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Ticket implements Comparable<Ticket> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer price; //Поле может быть null, Значение поля должно быть больше 0
    private TicketType type; //Поле не может быть null
    private Event event; //Поле может быть null

    public Ticket() {}

    public Event getEvent() throws NullPointerException {
        return event;
    }

    public Ticket(String name, Coordinates coordinates, Date creationDate, Integer price, TicketType type, Event event) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.type = type;
        this.event = event;
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(Ticket ticket) {
        return Integer.compare(ticket.type.getPriority(), this.type.getPriority());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", type=" + type +
                ", event=" + event +
                '}';
    }

}