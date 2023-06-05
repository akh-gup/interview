package se.akh.gup.etraveli.se.etraveli.movie.rentals.exception;

public class InvalidCustomerException extends RuntimeException {

    public InvalidCustomerException(String message){
        super(message);
    }

    public InvalidCustomerException(String message, Throwable exception){
        super(message, exception);
    }

    public InvalidCustomerException(Throwable exception){
        super(exception);
    }
}
