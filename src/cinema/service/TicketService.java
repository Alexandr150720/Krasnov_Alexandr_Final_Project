package cinema.service;

import cinema.model.Ticket;
import cinema.model.UserRole;
import cinema.repository.ITicketRepository;

import java.util.List;

public class TicketService implements ITicketService<Ticket, Integer> {

    private ITicketRepository<Ticket, Integer> ticketRepository;

    public TicketService(ITicketRepository<Ticket, Integer> ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> readAllFreeByMovie(Integer movieId) {
        try {
            return ticketRepository.readAllFreeByMovie(movieId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean reserve(Integer ticketId, Integer userId) {
        try{
            return ticketRepository.reserve(ticketId, userId);
        }catch (ClassNotFoundException e){
            return false;
        }
    }

    @Override
    public boolean refund(Integer ticketId) {
        try{
            return ticketRepository.refund(ticketId);
        }catch (ClassNotFoundException e){
            return false;
        }
    }

    @Override
    public List<Ticket> readAllByUser(Integer userId) {
        try {
            return ticketRepository.readAllByUser(userId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
