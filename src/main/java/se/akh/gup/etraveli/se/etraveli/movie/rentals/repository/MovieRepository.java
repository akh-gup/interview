package se.akh.gup.etraveli.se.etraveli.movie.rentals.repository;

import javax.annotation.PostConstruct;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.config.MovieRentalConfiguration;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.Movie;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieCode;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieRepository {

    private final MovieRentalConfiguration movieRentalConfiguration;
    private final ObjectMapper objectMapper;

    private final Map<String, Movie> movies = new HashMap<>();

    public Movie findMovieByMovieId(String movieId) {
        return this.movies.get(movieId);
    }

    public void addMovieById(String movieId, Movie movie){
        this.movies.put(movieId, movie);
    }

    @PostConstruct
    public void initMoviesList() {

        for (HashMap<String, String> map: movieRentalConfiguration.getRentals()){
            addMovieById(map.get("id"), new Movie(map.get("title"), objectMapper.convertValue(map.get("code"), MovieCode.class)));
        }
    }
}
