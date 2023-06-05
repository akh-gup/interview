package se.akh.gup.etraveli.se.etraveli.movie.rentals;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import se.etraveli.movie.rentals.model.Customer;
import se.etraveli.movie.rentals.model.MovieRental;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.etraveli.movie.rentals.service.RentalInfo;
import java.util.Arrays;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {

    ConfigurableApplicationContext appContext = SpringApplication.run(Main.class, args);
    RentalInfo rentalInfo = appContext.getBean(RentalInfo.class);

    String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

    String result = rentalInfo.statement(new Customer("C. U. Stomer", Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1))));

    if (!result.equals(expected)) {
      throw new AssertionError("Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result);
    }

    System.out.println("Success");
    appContext.close();
  }
}
