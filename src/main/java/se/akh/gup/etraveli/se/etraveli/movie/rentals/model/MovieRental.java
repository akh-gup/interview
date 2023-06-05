package se.akh.gup.etraveli.se.etraveli.movie.rentals.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieRental {

    private final String movieId;
    private final int days;
}
