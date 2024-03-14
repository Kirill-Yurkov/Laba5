package server.patternclass;

public enum TicketType {
    VIP(0),
    USUAL(1),
    CHEAP(2);
    private int priority;
    TicketType(int priority){
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}