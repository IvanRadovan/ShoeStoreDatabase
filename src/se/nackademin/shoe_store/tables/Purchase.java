package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class Purchase extends Table {
    private final Customer customer;

    public Purchase(int id, Timestamp created, Timestamp updated, Customer customer) {
        super(id, created, updated);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase purchase)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(customer, purchase.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customer);
    }

    @Override
    public String toString() {
        return id + " " + customer + " " + created + " " + updated + " ";
    }
}