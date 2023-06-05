package se.akh.gup.etraveli.se.etraveli.movie.rentals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Customer {

    private String name;
    private List<MovieRental> rentals;

}
