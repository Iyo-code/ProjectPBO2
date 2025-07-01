package main.model;

public class RoomType {
    private int id;
    private int villa;
    private String name;
    private int quantity;
    private int capacity;
    private int price;
    private String bedSize;
    private boolean hasDesk;
    private boolean hasAc;
    private boolean hasTv;
    private boolean hasWifi;
    private boolean hasShower;
    private boolean hasHotwater;
    private boolean hasFridge;

    public RoomType() {}

    public RoomType(int id, int villa, String name, int quantity, int capacity, int price,
                    String bedSize, boolean hasDesk, boolean hasAc, boolean hasTv,
                    boolean hasWifi, boolean hasShower, boolean hasHotwater, boolean hasFridge) {
        this.id = id;
        this.villa = villa;
        this.name = name;
        this.quantity = quantity;
        this.capacity = capacity;
        this.price = price;
        this.bedSize = bedSize;
        this.hasDesk = hasDesk;
        this.hasAc = hasAc;
        this.hasTv = hasTv;
        this.hasWifi = hasWifi;
        this.hasShower = hasShower;
        this.hasHotwater = hasHotwater;
        this.hasFridge = hasFridge;
    }

    // Getter & Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVilla() {
        return villa;
    }

    public void setVilla(int villa) {
        this.villa = villa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBedSize() {
        return bedSize;
    }

    public void setBedSize(String bedSize) {
        this.bedSize = bedSize;
    }

    public boolean isHasDesk() {
        return hasDesk;
    }

    public void setHasDesk(boolean hasDesk) {
        this.hasDesk = hasDesk;
    }

    public boolean isHasAc() {
        return hasAc;
    }

    public void setHasAc(boolean hasAc) {
        this.hasAc = hasAc;
    }

    public boolean isHasTv() {
        return hasTv;
    }

    public void setHasTv(boolean hasTv) {
        this.hasTv = hasTv;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean isHasShower() {
        return hasShower;
    }

    public void setHasShower(boolean hasShower) {
        this.hasShower = hasShower;
    }

    public boolean isHasHotwater() {
        return hasHotwater;
    }

    public void setHasHotwater(boolean hasHotwater) {
        this.hasHotwater = hasHotwater;
    }

    public boolean isHasFridge() {
        return hasFridge;
    }

    public void setHasFridge(boolean hasFridge) {
        this.hasFridge = hasFridge;
    }

    // Manual JSON
    public String toJson() {
        return String.format(
                "{" +
                        "\"id\":%d," +
                        "\"villa\":%d," +
                        "\"name\":\"%s\"," +
                        "\"quantity\":%d," +
                        "\"capacity\":%d," +
                        "\"price\":%d," +
                        "\"bedSize\":\"%s\"," +
                        "\"hasDesk\":%b," +
                        "\"hasAc\":%b," +
                        "\"hasTv\":%b," +
                        "\"hasWifi\":%b," +
                        "\"hasShower\":%b," +
                        "\"hasHotwater\":%b," +
                        "\"hasFridge\":%b" +
                        "}",
                id, villa, name, quantity, capacity, price, bedSize,
                hasDesk, hasAc, hasTv, hasWifi, hasShower, hasHotwater, hasFridge
        );
    }
}