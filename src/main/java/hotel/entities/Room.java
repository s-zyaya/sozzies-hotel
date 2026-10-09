package hotel.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
  @Id
  private int number;
  private boolean free = true;
  private boolean needsCleaning = false;
  private int cleaningTimes = 0;
  private int cleaningPrice;
  private int price;
  private String type;

  public Room() {}

  private Room(Builder builder) {
    this.number = builder.number;
    this.price = builder.price;
    this.type = builder.type;
    this.cleaningPrice = builder.cleaningPrice;
  }

  public static class Builder {
    private int number;
    private int price;
    private String type;
    private int cleaningPrice;

    public Builder number(int n) { this.number = n; return this; }
    public Builder price(int p) { this.price = p; return this; }
    public Builder type(String t) { this.type = t; return this; }
    public Builder cleaningPrice(int cp) { this.cleaningPrice = cp; return this; }

    public Room build() { return new Room(this); }
  }

  public int getNumber() { return number; }
  public boolean isFree() { return free; }
  public void setFree(boolean free) { this.free = free; }
  public boolean isNeedsCleaning() { return needsCleaning; }
  public void setNeedsCleaning(boolean needsCleaning) { this.needsCleaning = needsCleaning; }
  public int getCleaningTimes() { return cleaningTimes; }
  public void setCleaningTimes(int cleaningTimes) { this.cleaningTimes = cleaningTimes; }
  public int getCleaningPrice() { return cleaningPrice; }
  public int getPrice() { return price; }
  public String getType() {
    return type;
  }
}