package se.akh.gup.etraveli.se.etraveli.movie.rentals.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.Movie;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieCode;
import se.akh.gup.etraveli.se.etraveli.movie.rentals.repository.MovieRepository;

@Service
@RequiredArgsConstructor
public class MovieDetailsServiceImpl implements MovieDetailsService {

    private final MovieRepository movieRepository;

    @Override
    public MovieCode getMovieCode(String movieId) {
        return getMovie(movieId).getCode();
    }

    @Override
    public String getMovieTitle(String movieId) {
        return getMovie(movieId).getTitle();
    }

    /***
     * Fetch Movie details from repository
     * @param movieId           Movie Identifier
     * @return                  Movie object
     */
    private Movie getMovie(String movieId){
        return movieRepository.findMovieByMovieId(movieId);
    }
}
