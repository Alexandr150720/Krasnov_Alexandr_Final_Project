package cinema.model;

import java.time.LocalDateTime;
import java.util.List;

public class Movie {

    private int id;
    private String name;
    private LocalDateTime startedAT;
    private List<Ticket> tickets;

    public Movie(int id, String name, LocalDateTime startedAT, List<Ticket> tickets){
        this.id = id;
        this.name = name;
        this.startedAT = startedAT;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartedAT() {
        return startedAT;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startedAT=" + startedAT +
                ", tickets=" + tickets +
                '}';
    }
}
