package hotel.entities;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("MAID")
public class Maid extends User {
  private int cleanedRooms;
  private int wage;

  public Maid() {}
  public Maid(String name) {
    super(name);
    this.cleanedRooms = 0;
    this.wage = 0;
  }

  public int getCleanedRooms() { return cleanedRooms; }
  public int getWage() { return wage; }
  public void addCleanedRoom() { this.cleanedRooms++; }
  public void addWage(int amount) { this.wage += amount; }
  public void resetWage() { this.wage = 0; }
}