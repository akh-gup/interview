package se.akh.gup.etraveli.se.etraveli.movie.rentals.service;

import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieRental;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieRentalCosts;

public interface RentalCostsCalcService {

    /***
     *
     * Calculates Movie rental amount and bonus
     *      - Based on movie type and days, rent amount is calculated.
     *      - Based on days, bonus is calculated.
     *
     * @param movieRental       Movie Rental object having movie id and days.
     * @return                  Returns calculated movie rental costs object.
     */
    MovieRentalCosts calcRentalAmountAndBonus(MovieRental movieRental);
}
