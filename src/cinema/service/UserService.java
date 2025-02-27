package cinema.service;

import cinema.constant.Constant;
import cinema.hasher.PasswordHasherPBKDF2;
import cinema.model.Movie;
import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;
import cinema.repository.IUserRepository;

import java.util.List;

public class UserService implements IUserService<User, UserDTO, Integer> {

    private IUserRepository<User, UserDTO, Integer, UserRole> userRepository;

    public UserService(IUserRepository<User, UserDTO, Integer, UserRole> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean create(User user){
        try{
            byte[] salt = PasswordHasherPBKDF2.getSalt();
            String hashedPassword = PasswordHasherPBKDF2.hashPassword(user.getPassword(), salt, Constant.HASH_ITERATION, Constant.HASH_ITERATION);
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setRole(UserRole.USER);
            return userRepository.create(user);
        }catch (ClassNotFoundException e){
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    @Override
    public boolean update(User user) {
        try {
            byte[] salt = PasswordHasherPBKDF2.getSalt();
            String hashedPassword = PasswordHasherPBKDF2.hashPassword(user.getPassword(), salt, Constant.HASH_ITERATION, Constant.HASH_KEY_LENGTH);
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            return userRepository.update(user, UserRole.USER);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return userRepository.delete(id, UserRole.USER);
        } catch (ClassNotFoundException e){
            return false;
        }
    }

}
