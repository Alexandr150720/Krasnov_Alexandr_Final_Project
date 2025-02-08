package cinema.model;

import java.time.LocalDateTime;
import java.util.List;

public class Movie {

    private int id;
    private String name;
    private LocalDateTime startedAt;
    private List<Ticket> tickets;

    public Movie(int id, String name, LocalDateTime startedAt, List<Ticket> tickets){
        this.id = id;
        this.name = name;
        this.startedAt = startedAt;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startedAT=" + startedAt +
                ", tickets=" + tickets +
                '}';
    }
}
