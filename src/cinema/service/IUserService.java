package cinema.service;

import cinema.model.UserRole;

import java.util.List;

public interface IUserService<T, R, Q> {

    boolean create(T t);
    List<R> readAllByRole(UserRole userRole);
    boolean update(T t);
    boolean delete(Q q);



}
