package cinema.service;

import cinema.model.UserRole;

import java.util.List;

public interface IUserService<T, R> {

    boolean create(T t);
    List<R> readAllByRole(UserRole userRole);



}
