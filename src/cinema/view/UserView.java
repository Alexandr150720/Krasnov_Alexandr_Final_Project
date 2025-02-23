package cinema.view;

import cinema.model.Movie;
import cinema.model.Ticket;
import cinema.model.UserDTO;
import cinema.service.IMovieService;
import cinema.service.ITicketService;

import java.util.List;
import java.util.Scanner;

public class UserView implements IRoleView<UserDTO> {

    private IMovieService<Movie, Integer, Double> movieService;
    private ITicketService<Ticket, Integer> ticketService;

    public UserView(IMovieService<Movie, Integer, Double> movieService, ITicketService<Ticket, Integer> ticketService) {
        this.movieService = movieService;
        this.ticketService = ticketService;
    }

    @Override
    public void start(UserDTO userDTO) {

        while (true) {
            System.out.println("\nДля просмотра доступных фильмов нажмите 1");
            System.out.println("Для покупки билета нажмите 2");
            System.out.println("Для возврата билета нажмите 3");
            System.out.println("Для просмотра купленных билетов нажмите 4");
            System.out.println("Для выхода из системы нажмите 5");
            Scanner scanner = new Scanner(System.in);
            String pressButton = scanner.nextLine();

            switch (pressButton){
                case "1":
                    System.out.println("Список фильмов: ");
                    List<Movie> movies = movieService.readAll();
                    for(Movie movie: movies){
                        System.out.println(movie);
                    }
                    break;

                case "2":
                    System.out.println("Список фильмов: ");
                    movies = movieService.readAll();
                    for(Movie movie: movies){
                        System.out.println(movie);
                    }
                    System.out.println("Введите id фильма: ");
                    int movieId = scanner.nextInt();
                    List<Ticket> freeTickets = ticketService.readAllFreeByMovie(movieId);
                    for (Ticket ticket: freeTickets){
                        System.out.println(ticket);
                    }
                    System.out.println("Введите id билета с желаемым местом: ");
                    int ticketId = scanner.nextInt();
                    boolean isReserved = ticketService.reserve(ticketId, userDTO.getId());
                    if (isReserved){
                        System.out.println("Место успешно зарезервировано");
                    } else {
                        System.out.println("Возникла проблема с резервированием");
                    }
                    break;

                case "3":
                    System.out.println("Все ваши купленные билеты: ");
                    List<Ticket> userTickets = ticketService.readAllByUser(userDTO.getId());
                    for (Ticket ticket: userTickets){
                        System.out.println(ticket);
                    }
                    System.out.println("Введите id билета, который хотите вернуть");
                    ticketId = scanner.nextInt();
                    boolean isRefunded = ticketService.refund(ticketId);
                    if (isRefunded){
                        System.out.println("Билет успешно возвращен");
                    } else {
                        System.out.println("Что-то пошло не так");
                    }
                    break;

                case "4":
                    System.out.println("Все ваши купленные билеты: ");
                    userTickets = ticketService.readAllByUser(userDTO.getId());
                    for (Ticket ticket: userTickets){
                        System.out.println(ticket);
                    }
                    break;

                case "5":
                    return;
            }

        }

    }
}
