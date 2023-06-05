package se.akh.gup.etraveli.se.etraveli.movie.rentals.business.costs;

public class NewMovieRent implements MovieRent{

    @Override
    public double rent(int days) {
        return days * 3;
    }
}
