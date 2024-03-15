package se.nackademin.shoe_store.tables;

import java.sql.Timestamp;
import java.util.Objects;

public final class Shoe extends Table {
    private final ShoeBrand brand;
    private final ShoeSize size;
    private final ShoePrice price;
    private final int stock;

    public Shoe(int id, Timestamp created, Timestamp updated, ShoeBrand brand, ShoeSize size, ShoePrice price, int stock) {
        super(id, created, updated);
        this.brand = brand;
        this.size = size;
        this.price = price;
        this.stock = stock;
    }

    public ShoeBrand getBrand() {
        return brand;
    }

    public ShoeSize getSize() {
        return size;
    }

    public ShoePrice getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Shoe shoe)) return false;
        if (!super.equals(object)) return false;
        return stock == shoe.stock &&
                Objects.equals(brand, shoe.brand) &&
                Objects.equals(size, shoe.size) &&
                Objects.equals(price, shoe.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), brand, size, price, stock);
    }


    @Override
    public String toString() {
        return "%-19s %-13s %-21s %-18s".formatted(brand, size, price, stock);
    }


}
