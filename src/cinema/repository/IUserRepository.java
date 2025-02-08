package cinema.repository;

import java.util.List;

public interface IUserRepository<T, R> {

    boolean create(T t) throws ClassNotFoundException;
    List<R> readAll() throws ClassNotFoundException;
}
