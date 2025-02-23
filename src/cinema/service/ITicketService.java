package cinema.service;

import java.util.List;

public interface ITicketService<T, R> {

    List<T> readAllFreeByMovie(R r);
    boolean reserve(R r, R u);
    boolean refund(R r);
    List<T> readAllByUser(R u);
}
