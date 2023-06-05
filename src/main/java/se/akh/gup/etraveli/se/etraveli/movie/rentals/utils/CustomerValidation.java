package se.akh.gup.etraveli.se.etraveli.movie.rentals.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.constants.Constants;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.exception.InvalidCustomerException;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.exception.MovieRentalException;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.Customer;

@UtilityClass
public class CustomerValidation {

    /***
     * Validates if customer and customer data is valid.
     *      In case of empty/null values, exception is thrown.
     *
     * @param customer      Customer details
     */
    public static void isValidCustomer(Customer customer){

        if(null == customer){

            throw new InvalidCustomerException(Constants.EMPTY_CUSTOMER);

        } else if (!StringUtils.hasLength(customer.getName())){

            throw new InvalidCustomerException(Constants.EMPTY_CUSTOMER_NAME);

        } else if (null == customer.getRentals() || customer.getRentals().isEmpty()){

            throw new MovieRentalException(Constants.EMPTY_MOVIE_RENTALS);

        } else if (!customer.getRentals().stream().allMatch(movieRental -> movieRental.getDays() != 0 && StringUtils.hasLength(movieRental.getMovieId()))){

            throw new MovieRentalException(Constants.INVALID_MOVIE_RENTAL);
        }

    }
}
