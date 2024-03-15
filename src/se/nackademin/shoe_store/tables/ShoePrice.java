package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class ShoePrice extends Table {

    private final double swedishFigure;

    public ShoePrice(int id, Timestamp created, Timestamp updated, double swedishFigure) {
        super(id, created, updated);
        this.swedishFigure = swedishFigure;
    }

    public double swedishFigure() {
        return swedishFigure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoePrice shoePrice)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(swedishFigure, shoePrice.swedishFigure) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), swedishFigure);
    }

    @Override
    public String toString() {
        return "%.2f SEK".formatted(swedishFigure);
    }
}
