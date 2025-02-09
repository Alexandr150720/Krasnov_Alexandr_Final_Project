package cinema.repository;

import cinema.exception.NotUniqueException;
import cinema.exception.UserNotFoundException;
import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements IUserRepository<User, UserDTO> {

    private String url;
    private String userName;
    private String password;
    private String driver;

    public UserRepositoryImpl(String url, String userName, String password, String driver) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.driver = driver;
    }

    @Override
    public boolean create(User user) throws ClassNotFoundException {

        Class.forName(driver);
        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (login, password, role) VALUES (?,?,?)");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().getUserRole());
            stmt.execute();
        }catch (SQLIntegrityConstraintViolationException e) {
            throw new NotUniqueException();
        }
        catch (SQLException e){
            e.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    public List<UserDTO> readAll() throws ClassNotFoundException {
        List<UserDTO> users = new ArrayList<>();
        Class.forName(driver);
        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            PreparedStatement stmt = conn.prepareStatement("SELECT id, login, role FROM users");
            stmt.execute();
            ResultSet res = stmt.getResultSet();
            while (res.next()) {
                users.add(getUserDTO(res));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
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
            e.printStackTrace();
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
}
