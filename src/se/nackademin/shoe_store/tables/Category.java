package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class Category extends Table {

    private final String categoryName;

    public Category(int id, Timestamp created, Timestamp updated, String categoryName) {
        super(id, created, updated);
        this.categoryName = categoryName;
    }

    public String categoryName() {
        return categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoryName);
    }

    @Override
    public String toString() {
        return categoryName;
    }


}
