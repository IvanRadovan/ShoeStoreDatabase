package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class Color extends Table {

    private final String singleColor;

    public Color(int id, Timestamp created, Timestamp updated, String singleColor) {
        super(id, created, updated);
        this.singleColor = singleColor;
    }

    public String singleColor() {
        return singleColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color color)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(singleColor, color.singleColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), singleColor);
    }

    @Override
    public String toString() {
        return singleColor;
    }

}
