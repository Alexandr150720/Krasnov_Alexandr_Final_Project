package cinema.service;

import cinema.model.Movie;
import cinema.model.Ticket;
import cinema.repository.IMovieRepository;
import cinema.repository.ITicketRepository;

import java.util.List;

public class MovieService implements IMovieService<Movie, Integer, Double, Integer> {

    private ITicketRepository<Ticket, Integer> ticketRepository;
    private IMovieRepository<Movie, Integer> movieRepository;

    public MovieService(ITicketRepository<Ticket, Integer> ticketRepository, IMovieRepository<Movie, Integer> movieRepository) {
        this.ticketRepository = ticketRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> readAll() {
        try {
            return movieRepository.readAll();
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(Movie movie, Integer ticketsNumber, Double price) {
        try{
            Integer movieId = movieRepository.add(movie);
            movie.setId(movieId);
            for (int i = 1; i < ticketsNumber + 1; i++) {
                ticketRepository.add(new Ticket(movie, price, i));
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Movie movie) {
        try {
            return movieRepository.update(movie);
        } catch (ClassNotFoundException e){
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return movieRepository.delete(id);
        } catch (ClassNotFoundException e){
            return false;
        }
    }


}
