package cinema.repository;

import cinema.exception.NotUniqueException;
import cinema.exception.UserNotFoundException;
import cinema.model.Movie;
import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class UserRepository implements IUserRepository<User, UserDTO, Integer, UserRole> {

    private String url;
    private String userName;
    private String password;
    private String driver;

    public UserRepository(String url, String userName, String password, String driver) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.driver = driver;
    }

    @Override
    public boolean create(User user) throws ClassNotFoundException {
        Class.forName(driver);
        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (login, password, role, salt) VALUES (?,?,?,?)");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().getUserRole());

            String salt = Base64.getEncoder().encodeToString(user.getSalt());;
            stmt.setString(4, salt);
            stmt.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NotUniqueException();
        } catch (SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public List<UserDTO> readAllByRole(UserRole userRole) throws ClassNotFoundException {
        List<UserDTO> users = new ArrayList<>();
        Class.forName(driver);
        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            PreparedStatement stmt = conn.prepareStatement("SELECT id, login, role FROM users WHERE role = ?");
            stmt.setString(1, userRole.getUserRole());
            stmt.execute();
            ResultSet res = stmt.getResultSet();
            while (res.next()) {
                users.add(getUserDTO(res));
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public UserDTO authorize(User user) throws ClassNotFoundException {
        Class.forName(driver);
        try(Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.execute();
            ResultSet res = stmt.getResultSet();
            if (res.next()) {
                return getUserDTO(res);
            } else {
               throw new UserNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserDTO getUserDTO(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String stringRole = resultSet.getString("role");
        UserRole role = UserRole.valueOf(stringRole.toUpperCase());

        return new UserDTO(id, login, role);
    }

    @Override
    public boolean update(User user, UserRole userRole) throws ClassNotFoundException {
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ?, salt = ? WHERE id = ? AND role = ?");
            stmt.setString(1, user.getPassword());
            String salt = Base64.getEncoder().encodeToString(user.getSalt());;
            stmt.setString(2, salt);
            stmt.setInt(3, user.getId());
            stmt.setString(4, userRole.getUserRole());
            stmt.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Integer id, UserRole userRole) throws ClassNotFoundException {
        Class.forName(driver);
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ? AND role = ?");
            stmt.setInt(1, id);
            stmt.setString(2, userRole.getUserRole());
            stmt.execute();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public User getByLogin(User user) throws ClassNotFoundException {
        Class.forName(driver);
        try(Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
            stmt.setString(1, user.getLogin());
            stmt.execute();
            ResultSet res = stmt.getResultSet();
            if (res.next()) {
                int id = res.getInt("id");
                String login = res.getString("login");
                String password = res.getString("password");
                String stringRole = res.getString("role");
                UserRole role = UserRole.valueOf(stringRole.toUpperCase());
                String salt = res.getString("salt");
                return new User(id, login, password, Base64.getDecoder().decode(salt), role);
            } else {
                throw new UserNotFoundException();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
