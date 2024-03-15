package server.patternclass;

import lombok.Getter;

@Getter
public enum TicketType {
    VIP(0),
    USUAL(1),
    CHEAP(2);
    private final int priority;
    TicketType(int priority){
        this.priority = priority;
    }

}