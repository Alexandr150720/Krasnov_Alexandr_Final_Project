package cinema.repository;

import cinema.exception.NotUniqueException;
import cinema.exception.UserNotFoundException;
import cinema.model.Movie;
import cinema.model.UserDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements IMovieRepository<Movie, Integer> {

    private String url;
    private String userName;
    private String password;
    private String driver;

    public MovieRepository(String url, String userName, String password, String driver) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.driver = driver;
    }

    @Override
    public List<Movie> readAll() throws ClassNotFoundException {
        List<Movie> movies = new ArrayList<>();
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, name, startedAt FROM movies");
            stmt.execute();
            ResultSet res = stmt.getResultSet();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                Timestamp dateTimestamp = res.getTimestamp("startedAt");
                LocalDateTime startedAt = dateTimestamp.toLocalDateTime();
                movies.add(new Movie(id, name, startedAt));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    @Override
    public Integer add(Movie movie) throws ClassNotFoundException {
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO movies (name, startedAt) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, movie.getName());
            Timestamp dateTimestamp = Timestamp.valueOf(movie.getStartedAt());
            stmt.setTimestamp(2, dateTimestamp);
            stmt.execute();
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            return generatedKeys.getInt(1);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NotUniqueException();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean update(Movie movie) throws ClassNotFoundException {
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE movies SET name = ?, startedAt = ? WHERE id = ?");
            stmt.setString(1, movie.getName());
            Timestamp dateTimestamp = Timestamp.valueOf(movie.getStartedAt());
            stmt.setTimestamp(2, dateTimestamp);
            stmt.setInt(3, movie.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
