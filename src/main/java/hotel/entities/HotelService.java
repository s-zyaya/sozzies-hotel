package hotel.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "hotel_services")
public class HotelService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;

    @ManyToMany(mappedBy = "services")
    private Set<Guest> guests = new HashSet<>();

    public HotelService() {}

    public HotelService(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public Set<Guest> getGuests() {
        return guests;
    }
    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }
}