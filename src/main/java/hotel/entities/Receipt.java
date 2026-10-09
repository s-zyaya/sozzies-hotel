package hotel.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id") // Стовпчик у таблиці БД для зв'язку
    private Guest guest;

    private int roomNum;
    private int paidAmount;

    public Receipt() {}
    public Receipt(Guest guest, int roomNum, int paidAmount) {
        this.guest = guest;
        this.roomNum = roomNum;
        this.paidAmount = paidAmount;
    }

    @Override
    public String toString() {
        return "Guest: " + (guest != null ? guest.getName() : "Unknown") + " | Room №" + roomNum + " | Paid: " + paidAmount + "$";
    }
}