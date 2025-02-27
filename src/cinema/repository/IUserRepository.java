package cinema.repository;

import cinema.model.User;
import cinema.model.UserRole;

import java.util.List;

public interface IUserRepository<T, R, Q, W> {

    boolean create(T t) throws ClassNotFoundException;
    List<R> readAllByRole(UserRole userRole) throws ClassNotFoundException;
    R authorize(T t) throws ClassNotFoundException;
    boolean update(T t, W w) throws ClassNotFoundException;
    boolean delete(Q q, W w) throws ClassNotFoundException;
    T getByLogin(T t) throws ClassNotFoundException;

}
