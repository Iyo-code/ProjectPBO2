package main.model;

public class Booking {
    private int id;
    private int customer;
    private int roomType;
    private String checkinDate;
    private String checkoutDate;
    private int price;
    private Integer voucher; // nullable
    private int finalPrice;
    private String paymentStatus; // waiting, failed, success
    private boolean hasCheckedIn;
    private boolean hasCheckedOut;

    public Booking() {}

    public Booking(int id, int customer, int roomType, String checkinDate, String checkoutDate,
                   int price, Integer voucher, int finalPrice,
                   String paymentStatus, boolean hasCheckedIn, boolean hasCheckedOut) {
        this.id = id;
        this.customer = customer;
        this.roomType = roomType;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.price = price;
        this.voucher = voucher;
        this.finalPrice = finalPrice;
        this.paymentStatus = paymentStatus;
        this.hasCheckedIn = hasCheckedIn;
        this.hasCheckedOut = hasCheckedOut;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomer() { return customer; }
    public void setCustomer(int customer) { this.customer = customer; }

    public int getRoomType() { return roomType; }
    public void setRoomType(int roomType) { this.roomType = roomType; }

    public String getCheckinDate() { return checkinDate; }
    public void setCheckinDate(String checkinDate) { this.checkinDate = checkinDate; }

    public String getCheckoutDate() { return checkoutDate; }
    public void setCheckoutDate(String checkoutDate) { this.checkoutDate = checkoutDate; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public Integer getVoucher() { return voucher; }
    public void setVoucher(Integer voucher) { this.voucher = voucher; }

    public int getFinalPrice() { return finalPrice; }
    public void setFinalPrice(int finalPrice) { this.finalPrice = finalPrice; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public boolean isHasCheckedIn() { return hasCheckedIn; }
    public void setHasCheckedIn(boolean hasCheckedIn) { this.hasCheckedIn = hasCheckedIn; }

    public boolean isHasCheckedOut() { return hasCheckedOut; }
    public void setHasCheckedOut(boolean hasCheckedOut) { this.hasCheckedOut = hasCheckedOut; }
}