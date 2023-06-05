package se.akh.gup.king.se.king.game.score.model;

public class User {

    private String userId;

    /**
     * Instantiates a new User.
     *
     * @param userId the User name/id
     */
    public User(String userId) {
        this.userId = userId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return this.userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
