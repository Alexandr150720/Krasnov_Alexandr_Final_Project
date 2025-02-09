package cinema.service;

import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;
import cinema.repository.IUserRepository;

public class UserService implements IUserService<User, UserDTO> {

    private IUserRepository<User, UserDTO> userRepository;

    public UserService(IUserRepository<User, UserDTO> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean create(User user){
        try{
            user.setRole(UserRole.USER);
            return userRepository.create(user);
        }catch (ClassNotFoundException e){
            return false;
        }
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
