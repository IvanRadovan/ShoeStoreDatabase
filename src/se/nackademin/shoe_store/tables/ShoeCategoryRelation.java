package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class ShoeCategoryRelation extends Table {
    private final Shoe shoe;
    private final Category category;

    public ShoeCategoryRelation(int id, Timestamp created, Timestamp updated, Shoe shoe, Category category) {
        super(id, created, updated);
        this.shoe = shoe;
        this.category = category;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoeCategoryRelation that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(shoe, that.shoe) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shoe, category);
    }

    @Override
    public String toString() {
        return id + " " + shoe + " " + category + " " + created + " " + updated;

    }
}
