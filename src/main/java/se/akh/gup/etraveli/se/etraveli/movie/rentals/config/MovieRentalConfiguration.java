package se.akh.gup.etraveli.se.etraveli.movie.rentals.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "movie")
@Data
public class MovieRentalConfiguration {

    ArrayList<HashMap<String, String>> rentals;

}
