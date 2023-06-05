package se.akh.gup.etraveli.se.etraveli.movie.rentals.service;

import se.akh.gup.etraveli.se.etraveli.movie.rentals.model.MovieCode;

public interface MovieDetailsService {

    /***
     * Fetch Movie code from movie id.
     * @param movieId       Movie Identifier
     * @return              Movie Code
     */
    MovieCode getMovieCode(String movieId);

    /***
     * Fetch movie title from movie id.
     * @param movieId       Movie Identifier
     * @return              Movie title
     */
    String getMovieTitle(String movieId);
}
