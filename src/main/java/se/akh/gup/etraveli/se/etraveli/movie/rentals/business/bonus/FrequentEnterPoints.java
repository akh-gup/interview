package se.akh.gup.etraveli.se.etraveli.movie.rentals.business.bonus;

import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieCode;

public interface FrequentEnterPoints {

    /***
     * Frequent Enter Points earned by user for movie rent
     * @param movieCode         Movie Code
     * @param days              Number of Days movie rented for.
     * @return                  Frequent Enter Points earned for movie rent.
     */
    int getFrequentEnterPoints(MovieCode movieCode, int days);
}
