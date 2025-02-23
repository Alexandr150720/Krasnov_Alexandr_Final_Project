package cinema.repository;

import java.util.List;

public interface IMovieRepository<T, R> {

    List<T> readAll() throws ClassNotFoundException;
    R add(T t) throws ClassNotFoundException;
    boolean update(T t) throws ClassNotFoundException;


}
