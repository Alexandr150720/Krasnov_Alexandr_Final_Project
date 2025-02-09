import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;
import cinema.repository.IUserRepository;
import cinema.repository.UserRepositoryImpl;
import cinema.service.IUserService;
import cinema.service.UserService;
import cinema.view.CinemaView;
import cinema.view.IView;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://MySQL-8.0/cinema";
        String userName = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        IUserRepository<User, UserDTO> userRepository = new UserRepositoryImpl(url, userName, password, driver);


        IUserService<User, UserDTO> userService = new UserService(userRepository);
        IView cinemaView = new CinemaView(userService);

        cinemaView.start();

//        try{
//            userRepository.UserAuthorize(new User("QWER1", "re1q1"));
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }




    }
}
