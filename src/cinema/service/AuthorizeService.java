package cinema.service;

import cinema.constant.Constant;
import cinema.exception.UserNotFoundException;
import cinema.hasher.PasswordHasherPBKDF2;
import cinema.model.User;
import cinema.model.UserDTO;
import cinema.model.UserRole;
import cinema.repository.IUserRepository;

public class AuthorizeService implements IAuthorizeService<UserDTO, User>{

    private IUserRepository<User, UserDTO, Integer, UserRole> userRepository;

    public AuthorizeService(IUserRepository<User, UserDTO, Integer, UserRole> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO authorize(User user) {
        try{
            User userFromDB = userRepository.getByLogin(user);
            boolean isEqual = PasswordHasherPBKDF2.checkPassword(user.getPassword(), userFromDB.getPassword(), userFromDB.getSalt(), Constant.HASH_ITERATION, Constant.HASH_KEY_LENGTH);
            if (isEqual) {
                return new UserDTO(userFromDB.getId(), userFromDB.getLogin(), userFromDB.getRole());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new UserNotFoundException();
    }
}
