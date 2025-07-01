package main.model;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer() {}

    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // JSON Manual
    public String toJson() {
        return String.format(
                "{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\"}",
                id, name, email, phone
        );
    }
}