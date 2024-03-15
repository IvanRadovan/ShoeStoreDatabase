package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class ShoeSize extends Table {

    private final double euroSize;

    public ShoeSize(int id, Timestamp created, Timestamp updated, double euroSize) {
        super(id, created, updated);
        this.euroSize = euroSize;
    }

    public double euroSize() {
        return euroSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoeSize shoeSize)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(euroSize, shoeSize.euroSize) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), euroSize);
    }

    @Override
    public String toString() {
        return "%.1f".formatted(euroSize);
    }

}
