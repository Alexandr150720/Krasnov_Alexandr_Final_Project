package cinema.model;

public class Ticket {

    private int id;
    private UserDTO user;
    private Movie movie;
    private double price;
    private int seatNumber;
    private boolean isBought;

    public Ticket(int id, double price, int seatNumber){
        this.id = id;
        this.price = price;
        this.seatNumber = seatNumber;
    }

    public Ticket(Movie movie, double price, int seatNumber){
        this.movie = movie;
        this.price = price;
        this.seatNumber = seatNumber;
    }

    public Ticket(int id, Movie movie, double price, int seatNumber, boolean isBought){
        this.id = id;
        this.movie = movie;
        this.price = price;
        this.seatNumber = seatNumber;
        this.isBought = isBought;
    }

    public Ticket(int id, UserDTO user, Movie movie, double price, int seatNumber, boolean isBought){
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.price = price;
        this.seatNumber = seatNumber;
        this.isBought = isBought;
    }

    public int getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getPrice() {
        return price;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBought() {
        return isBought;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", price=" + price +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
