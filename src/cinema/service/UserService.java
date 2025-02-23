package cinema.service;

import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;
import cinema.repository.IUserRepository;

import java.util.List;

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
    public List<UserDTO> readAllByRole(UserRole userRole) {
        try {
            return userRepository.readAllByRole(userRole);
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

}
