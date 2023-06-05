package se.akh.gup.etraveli.se.etraveli.movie.rentals.business.costs;


import se.akh.gup.etraveli.se.etraveli.movie.rentals.constants.Constants;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.exception.MovieRentalException;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieCode;

public interface MovieRent {

    /***
     * Rent to be charged for movie based on number of days
     * @param days      Number of days for which movie is rented.
     * @return          Amount to be charged for movie as rent.
     */
    double rent(int days);

    /***
     * Static method to separate business and service implementations.
     * @param movieCode         Movie Code
     * @param days              Number of Days
     * @return                  Rent amount or throws exception for invalid movie code.
     */
    static double movieRentAmount(MovieCode movieCode, int days) {

        // Calculates rent class based on movie code.
            // Rent class estimates rent based on days.

        if (MovieCode.REGULAR.equals(movieCode)) {
            return new RegularMovieRent().rent(days);

        } else if (MovieCode.NEW.equals(movieCode)){
            return new NewMovieRent().rent(days);

        } else if (MovieCode.CHILDRENS.equals(movieCode)) {
            return new ChildrensMovieRent().rent(days);
        }

        throw new MovieRentalException(Constants.INVALID_MOVIE_RENTAL_CODE);
    }
}
