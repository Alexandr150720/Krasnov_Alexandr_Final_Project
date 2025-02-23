package cinema.service;

import cinema.model.User;
import cinema.model.UserDTO;
import cinema.repository.IUserRepository;

public class AuthorizeService implements IAuthorizeService<UserDTO, User>{

    private IUserRepository<User, UserDTO> userRepository;

    public AuthorizeService(IUserRepository<User, UserDTO> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO authorize(User user) {
        try{
            return userRepository.authorize(user);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
