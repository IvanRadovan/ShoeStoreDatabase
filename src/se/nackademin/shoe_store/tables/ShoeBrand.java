package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class ShoeBrand extends Table {

    private final String brand;

    public ShoeBrand(int id, Timestamp created, Timestamp updated, String brand) {
        super(id, created, updated);
        this.brand = brand;
    }

    public String brand() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoeBrand shoeBrand)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(brand, shoeBrand.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brand);
    }

    @Override
    public String toString() {
        return brand;
    }

}
