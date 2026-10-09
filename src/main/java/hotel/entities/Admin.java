package hotel.entities;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    public Admin() {}
    public Admin(String name) { super(name); }
}