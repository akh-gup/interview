package se.akh.gup.etraveli.se.etraveli.movie.rentals.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.constants.Constants;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.Customer;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieRental;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieRentalCosts;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.utils.CustomerValidation;

@Service
@RequiredArgsConstructor
public class RentalInfo {

  private final MovieDetailsService movieDetailsService;
  private final RentalCostsCalcService rentalCostsCalcService;

  /***
   * Prepares statement based on movies rented by customer.
   *    1. Validates if customer and its data is valid,
   *    2. Initialise statement specifying customer name,
   *    3. Calculates each rented movie cost and bonus based on movie type and rented days,
   *    4. Append statement for each rental of movie,
   *    5. Aggregates total costs and bonus for all movies,
   *    6. Add footer: specifies total cost of rent and bonus earned by customer.
   *
   * @param customer      Customer object having movie rental details
   * @return              Statement specifying each movie cost, as well as total costs & bonuses of all movies.
   */
  public String statement(Customer customer) {

    // Validates customer and movie rental details against null/empty values.
    CustomerValidation.isValidCustomer(customer);

    double totalAmount = 0;  int totalFrequentPoints = 0;
    StringBuilder result = new StringBuilder();

    result.append(Constants.RENTAL_RECORD_FOR_STR)
            .append(customer.getName())
            .append(Constants.LINE_SEPARATOR_STR);

    // Iterates on all rented movies by customer.
    for (MovieRental rental : customer.getRentals()) {

      // Calculate amount incurred by movie rental and bonus earned by rent.
      MovieRentalCosts movieRentalCosts = rentalCostsCalcService.calcRentalAmountAndBonus(rental);

      // Adds statement depicting movie and it's cost.
      result.append(Constants.TAB_STR)
              .append(movieDetailsService.getMovieTitle(rental.getMovieId()))
              .append(Constants.TAB_STR)
              .append(movieRentalCosts.getCost())
              .append(Constants.LINE_SEPARATOR_STR);

      // Aggregates total amount incurred
      totalAmount += movieRentalCosts.getCost();

      // Aggregates total bonus earned
      totalFrequentPoints += movieRentalCosts.getBonus();
    }

    // Add footer: Specifies Total cost owed by customer and Total bonus earned
    result.append(Constants.AMOUNT_OWED_IS_STR)
            .append(totalAmount)
            .append(Constants.LINE_SEPARATOR_STR)
            .append(Constants.YOU_EARNED_STR)
            .append(totalFrequentPoints)
            .append(Constants.FREQUENT_POINTS_STR)
            .append(Constants.LINE_SEPARATOR_STR);

    return result.toString();
  }
}
