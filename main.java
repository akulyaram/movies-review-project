import java.util.*;

class Movie {
    private String title;
    private String genre;
    private List<Integer> ratings = new ArrayList<>();
    private List<String> reviews = new ArrayList<>();

    public Movie(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public void addRating(int rating) {
        ratings.add(rating);
    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public double getAverageRating() {
        return ratings.stream().mapToInt(i -> i).average().orElse(0);
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public List<String> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return title + " [" + genre + "] - Avg Rating: " + String.format("%.1f", getAverageRating());
    }
}

class User {
    private String username;
    private Set<String> preferredGenres = new HashSet<>();

    public User(String username) {
        this.username = username;
    }

    public void addPreferredGenre(String genre) {
        preferredGenres.add(genre.toLowerCase());
    }

    public Set<String> getPreferredGenres() {
        return preferredGenres;
    }

    public String getUsername() {
        return username;
    }
}

public class Main {
    private static Map<String, Movie> movies = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        seedMovies();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        currentUser = new User(username);

        System.out.println("Welcome, " + username + "!");

        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. View Movies by Genre");
            System.out.println("2. Rate and Review a Movie");
            System.out.println("3. Get Recommendations");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt(); scanner.nextLine();

            switch (choice) {
                case 1 -> showMoviesByGenre();
                case 2 -> rateAndReviewMovie();
                case 3 -> getRecommendations();
                case 4 -> {
                    running = false;
                    System.out.println("Thank you! have✨ a nice 😊 day.");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void seedMovies() {
        // Action Movies
        movies.put("Vikram", new Movie("Vikram", "action"));
        movies.put("kaithi", new Movie("kaithi", "action"));
        movies.put("billa", new Movie("billa", "action"));

        // Romantic Movies
        movies.put("love today", new Movie("love today", "romantic"));
        movies.put("sweet heart", new Movie("sweet heart", "romantic"));
        movies.put("miss you", new Movie("miss you", "romantic"));

        // Comedy Movies
        movies.put("tourist family", new Movie("tourist family", "comedy"));
        movies.put("Kalakalappu", new Movie("Kalakalappu", "comedy"));
        movies.put("Jigarthanda", new Movie("Jigarthanda", "comedy"));

        // Horror Movies
        movies.put("Demonte Colony", new Movie("Demonte Colony", "horror"));
        movies.put("Aranmanai", new Movie("Aranmanai", "horror"));
        movies.put("Maya", new Movie("Maya", "horror"));
    }

    private static void showMoviesByGenre() {
        System.out.print("Enter genre (action/comedy/romantic/horror): ");
        String genre = scanner.nextLine().toLowerCase();
        currentUser.addPreferredGenre(genre);

        System.out.println("\nAvailable " + genre + " movies:");
        boolean found = false;
        for (Movie m : movies.values()) {
            if (m.getGenre().equalsIgnoreCase(genre)) {
                System.out.println("- " + m.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No movies found in this genre.");
        }
    }

    private static void rateAndReviewMovie() {
        System.out.print("Enter movie title: ");
        String movieTitle = scanner.nextLine();

        Movie movie = movies.get(movieTitle);
        if (movie == null) {
            System.out.println("Movie not found in system.");
            return;
        }

        System.out.print("Enter your rating (1 to 5): ");
        int rating = scanner.nextInt(); scanner.nextLine();
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating.");
            return;
        }

        System.out.print("Write a short review: ");
        String review = scanner.nextLine();

        movie.addRating(rating);
        movie.addReview(review);
        currentUser.addPreferredGenre(movie.getGenre());

        System.out.println("Thanks! Your review has been submitted.");
    }

    private static void getRecommendations() {
        Set<String> genres = currentUser.getPreferredGenres();
        System.out.println("\nRecommended Movies Based on Your Preferences:");
        boolean found = false;

        for (Movie m : movies.values()) {
            if (genres.contains(m.getGenre().toLowerCase()) && m.getAverageRating() >= 3.5) {
                System.out.println("- " + m.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No strong recommendations yet. Rate more movies!");
        }
    }
}
