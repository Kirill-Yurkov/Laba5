package server.patternclass;

public class Event {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long minAge; //Поле может быть null
    private int ticketsCount; //Значение поля должно быть больше 0
    private String description; //Строка не может быть пустой, Поле может быть null

    public Event(Integer id, String name, Long minAge, int ticketsCount, String description) {
        this.id = id;
        this.name = name;
        this.minAge = minAge;
        this.ticketsCount = ticketsCount;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getMinAge() {
        return minAge;
    }

    public int getTicketsCount() {
        return ticketsCount;
    }

    public String getDescription() {
        return description;
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