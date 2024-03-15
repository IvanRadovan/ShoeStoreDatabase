package se.nackademin.shoe_store;

import se.nackademin.shoe_store.tables.*;

import java.util.Set;

public interface IRepository {

    Set<Category> categories();

    Set<Color> colors();

    Set<Customer> customers();

    Set<Purchase> purchases();

    Set<Shoe> shoes();

    Set<ShoeBrand> shoeBrands();

    Set<ShoeCategoryRelation> shoeCategoryRelations();

    Set<ShoeColorRelation> shoeColorRelations();

    Set<ShoePrice> shoePrices();

    Set<ShoePurchaseRelation> shoePurchaseRelations();

    Set<ShoeSize> shoeSizes();

    String addToCart(int customerId, int shoeId, int purchaseId, int quantity);
}
