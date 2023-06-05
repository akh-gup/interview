package se.akh.gup.etraveli.se.etraveli.movie.rentals.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {

    private String title;
    private MovieCode code;

}
