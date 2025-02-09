package cinema.service;

public interface IUserService<T, R> {

    boolean create(T t);
    R authorize(T t);


}
