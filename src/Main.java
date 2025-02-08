import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;
import cinema.repository.IUserRepository;
import cinema.repository.UserRepositoryImpl;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://MySQL-8.0/cinema";
        String userName = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        User pasha = new User("QWER1", "rewq1", UserRole.ADMIN);
        IUserRepository<User, UserDTO> userRepository = new UserRepositoryImpl(url, userName, password, driver);

        try {
            System.out.println(userRepository.readAll());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }






    }
}
