package se.akh.gup.etraveli.se.etraveli.movie.rentals.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieRentalCosts {

    public MovieRentalCosts(MovieRental movieRental){
        this.movieRental = movieRental;
    }

    MovieRental movieRental;
    double cost;
    int bonus;
}
