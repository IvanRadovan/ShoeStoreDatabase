package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class ShoePurchaseRelation extends Table {
    private final Shoe shoe;
    private final Purchase purchase;
    private final int quantity;

    public ShoePurchaseRelation(int id, Timestamp created, Timestamp updated, Shoe shoe, Purchase purchase, int quantity) {
        super(id, created, updated);
        this.shoe = shoe;
        this.purchase = purchase;
        this.quantity = quantity;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoePurchaseRelation that)) return false;
        if (!super.equals(o)) return false;
        return quantity == that.quantity
                && Objects.equals(shoe, that.shoe)
                && Objects.equals(purchase, that.purchase);
    }

    @Override
    public String toString() {
        return "%s %s %s %s %s %s".formatted(id, shoe, purchase, quantity, created, updated);

    }
}
