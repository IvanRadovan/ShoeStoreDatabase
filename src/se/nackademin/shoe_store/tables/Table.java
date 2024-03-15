package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public abstract class Table {

    protected final int id;
    protected Timestamp created;
    protected Timestamp updated;

    public Table(int id, Timestamp created, Timestamp updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
    }

    public int id() {
        return id;
    }

    public Timestamp created() {
        return created;
    }

    public Timestamp updated() {
        return updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table table)) return false;
        return id == table.id
                && Objects.equals(created, table.created)
                && Objects.equals(updated, table.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated);
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
