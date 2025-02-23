package cinema.service;

import java.util.List;

public interface IMovieService<T, R, U> {

    List<T> readAll();
    boolean add(T t, R r, U u);
    boolean update(T t);
}
