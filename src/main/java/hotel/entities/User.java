package hotel.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", indexes = @Index(name = "idx_user_name", columnList = "name"))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;

    public User() {}
    public User(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }
    @Override
    public int hashCode() { return Objects.hash(name); }
}