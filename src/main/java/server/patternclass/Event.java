package server.patternclass;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Event {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long minAge; //Поле может быть null
    private int ticketsCount; //Значение поля должно быть больше 0
    private String description; //Строка не может быть пустой, Поле может быть null

    public Event(String name, Long minAge, int ticketsCount, String description) {
        this.name = name;
        this.minAge = minAge;
        this.ticketsCount = ticketsCount;
        this.description = description;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minAge=" + minAge +
                ", ticketsCount=" + ticketsCount +
                ", description='" + description + '\'' +
                '}';
    }
}