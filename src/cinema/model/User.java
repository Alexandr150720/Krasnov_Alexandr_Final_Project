package cinema.model;

public class User {

    private int id;
    private String login;
    private String password;
    private UserRole role;
    private byte[] salt;

    public User(int id, String login, String password, byte[] salt, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
