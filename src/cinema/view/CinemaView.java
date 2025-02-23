package cinema.view;

import cinema.exception.NotUniqueException;
import cinema.exception.UserNotFoundException;
import cinema.model.Movie;
import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;
import cinema.service.IAuthorizeService;
import cinema.service.IMovieService;
import cinema.service.IUserService;

import java.util.Scanner;

public class CinemaView implements IView {

    private IAuthorizeService<UserDTO, User> authorizeService;
    private IUserService<User, UserDTO> userService;
    private IRoleView<UserDTO> userView;
    private IRoleView<UserDTO> managerView;

    public CinemaView(IAuthorizeService<UserDTO, User> authorizeService, IUserService<User, UserDTO> userService, IRoleView<UserDTO> userView, IRoleView<UserDTO> managerView) {
        this.authorizeService = authorizeService;
        this.userService = userService;
        this.userView = userView;
        this.managerView = managerView;
    }

    @Override
    public void start() {
        System.out.println("Добро пожаловать в кинотеатр \"Помогите с работой\"");

        while(true){
            System.out.println("Для регистрации нажмите 1");
            System.out.println("Для входа в систему нажмите 2");
            Scanner scanner = new Scanner(System.in);
            String pressedButton = scanner.nextLine();

            switch (pressedButton){
                case "1":
                    System.out.println("Введите логин: ");
                    String login = scanner.nextLine();
                    System.out.println("Введите пароль: ");
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
                case "2":
                    System.out.println("Введите логин: ");
                    login = scanner.nextLine();
                    System.out.println("Введите пароль: ");
                    password = scanner.nextLine();
                    try {
                        UserDTO userDTO = authorizeService.authorize(new User(login, password));
                        if (userDTO.getRole().equals(UserRole.USER)) {
                            userView.start(userDTO);
                        }
                        else if (userDTO.getRole().equals(UserRole.MANAGER)) {
                            managerView.start(userDTO);
                        }
                    } catch (UserNotFoundException e){
                        System.out.println("Неверный пароль или логин");
                    } catch (RuntimeException e) {
                        System.out.println("Произошла неизвестная ошибка");
                    }

                    break;
            }
        }







    }
}
