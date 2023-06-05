package se.akh.gup.etraveli.se.etraveli.movie.rentals.business.costs;

public class ChildrensMovieRent implements MovieRent{

    @Override
    public double rent(int days) {
        return days < 3 ? 1.5 : 1.5 + ((days-3) * 1.5);
    }
}
