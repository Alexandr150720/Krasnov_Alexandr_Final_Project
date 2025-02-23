package cinema.repository;

import cinema.exception.NotUniqueException;
import cinema.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements ITicketRepository<Ticket, Integer> {

    private String url;
    private String userName;
    private String password;
    private String driver;

    public TicketRepository(String url, String userName, String password, String driver) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.driver = driver;
    }

    @Override
    public List<Ticket> readAllFreeByMovie(Integer movieId) throws ClassNotFoundException {
        List<Ticket> freeTickets = new ArrayList<>();
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, price, seatNumber FROM tickets WHERE movieId = ? AND isBought = ?");
            stmt.setInt(1, movieId);
            stmt.setBoolean(2, false);
            stmt.execute();
            ResultSet res = stmt.getResultSet();

            while (res.next()) {
                int id = res.getInt("id");
                Double price = res.getDouble("price");
                int seatNumber = res.getInt("seatNumber");

                freeTickets.add(new Ticket(id, price, seatNumber));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return freeTickets;
    }

    @Override
    public boolean reserve(Integer ticketId, Integer userId) throws ClassNotFoundException {
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE tickets SET userId = ?, isBought = ? WHERE id = ?");
            stmt.setInt(1, userId);
            stmt.setBoolean(2, true);
            stmt.setInt(3, ticketId);
            stmt.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean refund(Integer ticketId) throws ClassNotFoundException {
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE tickets SET userId = ?, isBought = ? WHERE id = ?");
            stmt.setNull(1, Types.NULL);
            stmt.setBoolean(2, false);
            stmt.setInt(3, ticketId);
            stmt.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Ticket> readAllByUser(Integer userId) throws ClassNotFoundException {
        List<Ticket> userTickets = new ArrayList<>();
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, price, seatNumber FROM tickets WHERE userId = ?");
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet res = stmt.getResultSet();

            while (res.next()) {
                int id = res.getInt("id");
                Double price = res.getDouble("price");
                int seatNumber = res.getInt("seatNumber");

                userTickets.add(new Ticket(id, price, seatNumber));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userTickets;
    }

    @Override
    public boolean add(Ticket ticket) throws ClassNotFoundException {
        Class.forName(driver);
        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO tickets (movieId, price, seatNumber, isBought) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, ticket.getMovie().getId());
            stmt.setDouble(2, ticket.getPrice());
            stmt.setInt(3, ticket.getSeatNumber());
            stmt.setBoolean(4, false);
            stmt.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NotUniqueException();
        } catch (SQLException e){
            return false;
        }

        return true;
    }


}
