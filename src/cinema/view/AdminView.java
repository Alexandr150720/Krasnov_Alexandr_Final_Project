package cinema.view;

import cinema.checker.DateTimeChecker;
import cinema.exception.InvalidDateTimeException;
import cinema.exception.NotUniqueException;
import cinema.model.*;
import cinema.service.IMovieService;
import cinema.service.ITicketService;
import cinema.service.IUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AdminView implements IRoleView<UserDTO> {

    private IMovieService<Movie, Integer, Double, Integer> movieService;
    private ITicketService<Ticket, Integer> ticketService;
    private IUserService<User, UserDTO, Integer> userService;

    public AdminView(IMovieService<Movie, Integer, Double, Integer> movieService, ITicketService<Ticket, Integer> ticketService,
                     IUserService<User, UserDTO, Integer> userService) {
        this.movieService = movieService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Override
    public void start(UserDTO userDTO) {

        while (true) {
            System.out.println("\nДля просмотра доступных фильмов нажмите 1");
            System.out.println("Для покупки билета нажмите 2");
            System.out.println("Для возврата билета нажмите 3");
            System.out.println("Для просмотра купленных билетов нажмите 4");
            System.out.println("Для редактирования фильмов нажмите 5");
            System.out.println("Для создания фильма нажмите 6");
            System.out.println("Для удаления фильма нажмите 7");
            System.out.println("Для создания пользователя нажмите 8");
            System.out.println("Для удаления пользователя нажмите 9");
            System.out.println("Для редактирования пользователя нажмите 10");
            System.out.println("Для выхода из системы нажмите 11");
            Scanner scanner = new Scanner(System.in);
            String pressButton = scanner.nextLine();

            switch (pressButton) {
                case "1":
                    System.out.println("Список фильмов: ");
                    List<Movie> movies = movieService.readAll();
                    for (Movie movie : movies) {
                        System.out.println(movie);
                    }
                    break;

                case "2":
                    System.out.println("Список фильмов: ");
                    movies = movieService.readAll();
                    for (Movie movie : movies) {
                        System.out.println(movie);
                    }
                    System.out.println("Введите id фильма: ");
                    int movieId = scanner.nextInt();
                    List<Ticket> freeTickets = ticketService.readAllFreeByMovie(movieId);
                    for (Ticket ticket : freeTickets) {
                        System.out.println(ticket);
                    }
                    if (freeTickets.isEmpty()){
                        System.out.println("Билеты на этот фильм распроданы");
                        break;
                    }

                    System.out.println("Введите id билета с желаемым местом: ");
                    int ticketId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Нажмите 1 для выбора пользователя");
                    System.out.println("Нажмите любую другую клавишу для выбора себя");

                    String chosenOption = scanner.nextLine();
                    int userId = userDTO.getId();

                    if (chosenOption.equals("1")) {
                        List<UserDTO> users = userService.readAllByRole(UserRole.USER);
                        for (UserDTO user: users){
                            System.out.println(user);
                        }
                        System.out.println("Введите id пользователя: ");
                        userId = scanner.nextInt();
                    }

                    boolean isReserved = ticketService.reserve(ticketId, userId);
                    if (isReserved) {
                        System.out.println("Место успешно зарезервировано");
                    } else {
                        System.out.println("Возникла проблема с резервированием");
                    }


                    break;

                case "3":

                    System.out.println("Нажмите 1 для выбора пользователя");
                    System.out.println("Нажмите любую другую клавишу для возврата своих билетов");

                    chosenOption = scanner.nextLine();
                    userId = userDTO.getId();

                    if (chosenOption.equals("1")) {
                        List<UserDTO> users = userService.readAllByRole(UserRole.USER);
                        for (UserDTO user: users){
                            System.out.println(user);
                        }
                        System.out.println("Введите id пользователя: ");
                        userId = scanner.nextInt();
                    }

                    System.out.println("Все купленные билеты: ");
                    List<Ticket> userTickets = ticketService.readAllByUser(userId);
                    for (Ticket ticket : userTickets) {
                        System.out.println(ticket);
                    }
                    System.out.println("Введите id билета, который хотите вернуть");
                    ticketId = scanner.nextInt();
                    boolean isRefunded = ticketService.refund(ticketId);
                    if (isRefunded) {
                        System.out.println("Билет успешно возвращен");
                    } else {
                        System.out.println("Что-то пошло не так");
                    }
                    break;

                case "4":
                    System.out.println("Нажмите 1 для выбора пользователя");
                    System.out.println("Нажмите любую другую клавишу для просмотра своих билетов");

                    chosenOption = scanner.nextLine();
                    userId = userDTO.getId();

                    if (chosenOption.equals("1")) {
                        List<UserDTO> users = userService.readAllByRole(UserRole.USER);
                        for (UserDTO user: users){
                            System.out.println(user);
                        }
                        System.out.println("Введите id пользователя: ");
                        userId = scanner.nextInt();
                    }

                    System.out.println("Все купленные билеты: ");
                    userTickets = ticketService.readAllByUser(userId);
                    for (Ticket ticket : userTickets) {
                        System.out.println(ticket);
                    }
                    break;

                case "5":
                    System.out.println("Список фильмов: ");
                    movies = movieService.readAll();
                    for (Movie movie : movies) {
                        System.out.println(movie);
                    }
                    System.out.println("Введите id фильма для редактирования: ");
                    movieId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Введите обновленное название фильма: ");
                    String newMovieName = scanner.nextLine();
                    System.out.println("Введите время начала фильма (в формате ДД.ММ.ГГГГ ЧЧ:ММ): ");
                    String newMovieDateTime = scanner.nextLine();

                    try {
                        LocalDateTime newStartedAt = DateTimeChecker.check(newMovieDateTime);
                        Movie movieForUpdate = new Movie(movieId, newMovieName, newStartedAt);
                        boolean isUpdated = movieService.update(movieForUpdate);
                        if (isUpdated) {
                            System.out.println("Фильм успешно отредактирован");
                        } else {
                            System.out.println("Возникла ошибка при редактировании");
                        }
                    } catch (InvalidDateTimeException e){
                        System.out.println("Некорректная дата и время");
                    }
                    break;

                case "6":
                    System.out.println("Введите название фильма: ");
                    newMovieName = scanner.nextLine();
                    System.out.println("Введите время начала фильма (в формате ДД.ММ.ГГГГ ЧЧ:ММ): ");
                    newMovieDateTime = scanner.nextLine();
                    System.out.println("Введите количество мест на фильм: ");
                    int ticketNumber = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Введите стоимость билета на фильм: ");
                    double ticketPrice = scanner.nextDouble();

                    try {
                        LocalDateTime newStartedAt = DateTimeChecker.check(newMovieDateTime);
                        Movie newMovie = new Movie(newMovieName, newStartedAt);
                        boolean isCreated = movieService.add(newMovie, ticketNumber, ticketPrice);
                        if (isCreated) {
                            System.out.println("Фильм успешно добавлен");
                        } else {
                            System.out.println("Возникла ошибка при добавлении");
                        }
                    } catch (InvalidDateTimeException e){
                        System.out.println("Некорректная дата и время");
                    }

                    break;

                case "7":
                    System.out.println("Список фильмов: ");
                    movies = movieService.readAll();
                    for (Movie movie : movies) {
                        System.out.println(movie);
                    }
                    System.out.println("Введите id фильма для удаления");
                    movieId = scanner.nextInt();
                    boolean isDeleted = movieService.delete(movieId);
                    if (isDeleted){
                        System.out.println("Фильм успешно удалён");
                    } else {
                        System.out.println("Произошла ошибка при удалении");
                    }
                    break;

                case "8":
                    System.out.println("Введите логин пользователя: ");
                    String login = scanner.nextLine();
                    System.out.println("Введите пароль пользователя: ");
                    String password = scanner.nextLine();
                    try {
                        boolean isCreated = userService.create(new User(login, password));
                        if(isCreated){
                            System.out.println("Регистрация прошла успешно");
                        } else {
                            System.out.println("Возникла ошибка при регистрации");
                        }
                    } catch (NotUniqueException e){
                        System.out.println("Пользователь с таким логином уже существует!");
                    } catch (RuntimeException e) {
                        System.out.println("Произошла неизвестная ошибка");
                    }
                    break;

                case "9":
                    System.out.println("Список пользователей: ");
                    List<UserDTO> users = userService.readAllByRole(UserRole.USER);
                    for (UserDTO user: users){
                        System.out.println(user);
                    }
                    System.out.println("Введите id пользователя для удаления");
                    userId = scanner.nextInt();
                    isDeleted = userService.delete(userId);
                    if (isDeleted){
                        System.out.println("Пользователь успешно удалён");
                    } else {
                        System.out.println("Произошла ошибка при удалении");
                    }
                    break;

                case "10":
                    System.out.println("Список пользователей: ");
                    users = userService.readAllByRole(UserRole.USER);
                    for (UserDTO user: users){
                        System.out.println(user);
                    }
                    System.out.println("Введите id пользователя: ");
                    userId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Введите обновлённый пароль: ");
                    String newUserPassword = scanner.nextLine();
                    User userForUpdate = new User(userId, newUserPassword);
                    boolean isUpdated = userService.update(userForUpdate);
                    if (isUpdated) {
                        System.out.println("Пользователь успешно отредактирован");
                    } else {
                        System.out.println("Возникла ошибка при редактировании");
                    }
                    break;
                case "11":
                    return;
            }

        }

    }


}
