package hotel.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

@Entity
@DiscriminatorValue("GUEST")
public class Guest extends User {
    private int roomNum;
    private int nights;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "guest_services",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<HotelService> services = new HashSet<>();

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Receipt> receipts = new ArrayList<>();

    public Guest() {}
    public Guest(String name) { super(name); }


    public int getRoomNum() { return roomNum; }
    public void setRoomNum(int roomNum) { this.roomNum = roomNum; }
    public int getNights() { return nights; }
    public void setNights(int nights) { this.nights = nights; }
    public Passport getPassport() { return passport; }
    public void setPassport(Passport passport) { this.passport = passport; }
    public Set<HotelService> getServices() { return services; }
    public void setServices(Set<HotelService> services) { this.services = services; }
    public List<Receipt> getReceipts() { return receipts; }
    public void setReceipts(List<Receipt> receipts) { this.receipts = receipts; }
    public void addService(HotelService service) {
        this.services.add(service);
    }
}