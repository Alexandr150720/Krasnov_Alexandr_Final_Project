package cinema.model;

public enum UserRole {

    USER("user"),
    MANAGER("manager"),
    ADMIN("admin");

    private String userRole;
    private UserRole(String userRole){
        this.userRole = userRole;
    }

    public String getUserRole(){
        return this.userRole;
    }
}
