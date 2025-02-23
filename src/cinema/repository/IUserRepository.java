package cinema.repository;

import cinema.model.UserRole;

import java.util.List;

public interface IUserRepository<T, R> {

    boolean create(T t) throws ClassNotFoundException;
    List<R> readAllByRole(UserRole userRole) throws ClassNotFoundException;
    R authorize(T t) throws ClassNotFoundException;
}
