package se.akh.gup.etraveli.se.etraveli.movie.rentals.exception;

public class MovieRentalException extends RuntimeException{

    public MovieRentalException(String message){
        super(message);
    }

    public MovieRentalException(String message, Throwable exception){
        super(message, exception);
    }

    public MovieRentalException(Throwable exception){
        super(exception);
    }
}
