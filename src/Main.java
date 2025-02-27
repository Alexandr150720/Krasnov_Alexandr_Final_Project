import cinema.model.*;
import cinema.repository.*;
import cinema.service.*;
import cinema.view.*;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://MySQL-8.0/cinema";
        String userName = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        IUserRepository<User, UserDTO, Integer, UserRole> userRepository = new UserRepository(url, userName, password, driver);
        IUserService<User, UserDTO, Integer> userService = new UserService(userRepository);

        IMovieRepository<Movie, Integer> movieRepository = new MovieRepository(url, userName, password, driver);

        ITicketRepository<Ticket, Integer> ticketRepository = new TicketRepository(url, userName, password, driver);
        ITicketService<Ticket, Integer> ticketService = new TicketService(ticketRepository);

        IMovieService<Movie, Integer, Double, Integer> movieService = new MovieService(ticketRepository, movieRepository);

        IAuthorizeService<UserDTO, User> authorizeService = new AuthorizeService(userRepository);

        IRoleView<UserDTO> userView = new UserView(movieService, ticketService);
        IRoleView<UserDTO> managerView = new ManagerView(movieService, ticketService, userService);
        IRoleView<UserDTO> adminView = new AdminView(movieService, ticketService, userService);

        IView cinemaView = new CinemaView(authorizeService, userService, userView, managerView, adminView);

        cinemaView.start();

    }
}
