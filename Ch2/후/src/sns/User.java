package sns;

import java.util.HashSet;
import java.util.Set;

public class User {

    private String name;
    private Set<User> friends;

    public User(String name) {
        this.name = name;
        this.friends = new HashSet<>();
    }

    public void befriend(User other) {
        friends.add(other);
        other.friends.add(this);
    }
}
