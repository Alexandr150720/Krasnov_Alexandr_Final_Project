package cinema.service;

public interface IAuthorizeService<R, T> {

    R authorize(T t);
}
