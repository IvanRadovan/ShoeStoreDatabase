package se.nackademin.shoe_store;

import org.junit.jupiter.api.Test;
import se.nackademin.shoe_store.tables.*;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {

    Repository repository = new Repository();


    private <T> boolean setContainsData(Set<T> table, String name){
        return table.stream()
                .map(Objects::toString)
                .anyMatch(row -> row.equals(name));
    }

    private <T extends Table> int setRowCount(Set<T> table){
        return table.size();
    }

    @Test
    void categories() {
        Set<Category> set = repository.categories();
        //set.forEach(System.out::println);
        String dataToString = "Category[id=1, categoryName=Damskor, created=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 8;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void colors() {
        Set<Color> set = repository.colors();
        //set.forEach(System.out::println);
        String dataToString = "Color[id=2, singleColor=Grön, created=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 9;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void customers() {
        Set<Customer> set = repository.customers();
        //set.forEach(System.out::println);
        String dataToString = "Customer[id=3, personalNumber=7710245050, password=123, firstName=Julia, lastName=Lind, phoneNumber=739871221, email=julia.lind@yahoo.com, address=Olaus Magnus Väg 66, postalCode=12745, city=Johanneshov, created=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 5;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void getPurchases() {
        Set<Purchase> set = repository.purchases();
        //set.forEach(System.out::println);
        String dataToString = "Purchase[id=1, customerId=1, orderDateTime=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 8;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void shoes() {
        Set<Shoe> set = repository.shoes();
        //set.forEach(System.out::println);
        String dataToString = "Shoe[id=6, brandId=6, sizeId=7, priceId=2, stock=50, created=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 9;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void shoeBrands() {
        Set<ShoeBrand> set = repository.shoeBrands();
        //set.forEach(System.out::println);
        String dataToString = "ShoeBrand[id=8, brand=Brooks, created=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 9;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void shoeCategoryRelations() {
        Set<ShoeCategoryRelation> set = repository.shoeCategoryRelations();
        //set.forEach(System.out::println);
        String dataToString = "ShoeCategoryRelation[id=12, shoeId=7, categoryId=7, orderDateTime=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 15;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void shoeColorRelations() {
        Set<ShoeColorRelation> set = repository.shoeColorRelations();
        //set.forEach(System.out::println);
        String dataToString = "ShoeColorRelation[id=3, shoeId=1, colorId=8, orderDateTime=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 15;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void shoePrices() {
        Set<ShoePrice> set = repository.shoePrices();
        //set.forEach(System.out::println);
        String dataToString = "ShoePrice[id=5, swedishFigure=995.0, created=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 8;

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void shoePurchaseRelations() {
        Set<ShoePurchaseRelation> set = repository.shoePurchaseRelations();
        //set.forEach(System.out::println);
        String dataToString = "ShoePurchaseRelation[id=1, shoeId=2, purchaseId=1, quantity=1, orderDateTime=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 10;      // <--- +1 with addToCart()

        assertEquals(setRowCount(set), rowCount);
    }

    @Test
    void shoeSizes() {
        Set<ShoeSize> set = repository.shoeSizes();
        //set.forEach(System.out::println);
        String dataToString = "ShoeSize[id=7, euroSize=42.0, created=2024-02-02 17:18:11.0, updated=2024-02-02 17:18:11.0]";
        int rowCount = 8;

        assertEquals(setRowCount(set), rowCount);
    }
}