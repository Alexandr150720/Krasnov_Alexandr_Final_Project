package cinema.repository;

import java.util.List;

public interface ITicketRepository<T, R> {

    List<T> readAllFreeByMovie(R r) throws ClassNotFoundException;
    boolean reserve(R r, R u) throws ClassNotFoundException;
    boolean refund(R r) throws ClassNotFoundException;
    List<T> readAllByUser(R u) throws ClassNotFoundException;
    boolean add(T t) throws ClassNotFoundException;
}
