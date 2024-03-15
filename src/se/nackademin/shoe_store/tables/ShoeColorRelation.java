package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class ShoeColorRelation extends Table {
    private final Shoe shoe;
    private final Color color;

    public ShoeColorRelation(int id, Timestamp created, Timestamp updated, Shoe shoe, Color color) {
        super(id, created, updated);
        this.shoe = shoe;
        this.color = color;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoeColorRelation that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(shoe, that.shoe) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shoe, color);
    }
}
