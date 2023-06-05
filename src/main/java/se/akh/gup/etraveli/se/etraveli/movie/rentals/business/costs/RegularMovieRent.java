package se.akh.gup.etraveli.se.etraveli.movie.rentals.business.costs;

public class RegularMovieRent implements MovieRent{

    @Override
    public double rent(int days) {
        return days < 2 ? 2 : 2 + ((days-2) * 1.5);
    }
}
