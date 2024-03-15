package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class Customer extends Table {
    private final String personalNumber;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;
    private final String address;
    private final String postalCode;
    private final String city;

    public Customer(int id, Timestamp created, Timestamp updated, String personalNumber, String password, String firstName,
                    String lastName, String phoneNumber, String email, String address, String postalCode, String city) {
        super(id, created, updated);
        this.personalNumber = personalNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String personalNumber() {
        return personalNumber;
    }

    public String password() {
        return password;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String email() {
        return email;
    }

    public String address() {
        return address;
    }

    public String postalCode() {
        return postalCode;
    }

    public String city() {
        return city;
    }

    public String nameAndAddress() {
        return "%-16s %-16s %-25s %-20s %s%n".formatted(firstName, lastName, address, postalCode, city);
    }

    public <T> String nameAndOrderAmount(T type) {
        return "%-17s %-16s %s".formatted(firstName, lastName, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(personalNumber, customer.personalNumber) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(phoneNumber, customer.phoneNumber) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(postalCode, customer.postalCode) &&
                Objects.equals(city, customer.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                personalNumber, password, firstName, lastName, phoneNumber, email, address, postalCode, city);
    }

    @Override
    public String toString() {
        return "%-16s %-16s %-15s %-15s %-37s %-25s %-11s %-18s"
                .formatted(personalNumber, firstName, lastName, phoneNumber, email, address, postalCode, city);
    }


}
