package se.nackademin.shoe_store;

import se.nackademin.shoe_store.tables.*;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Repository implements IRepository {

    private final Connectable databaseConnection;

    private static final String ADDED_TO_CART_MESSAGE = "Shoe added to the cart successfully.";
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    private static final int FOURTH_INDEX = 4;


    public Repository() {
        this.databaseConnection = new DatabaseConnection();
    }

    private <T extends Table> Set<T> fetchTable(String query, Provider<T> provider) {
        Set<T> table = new HashSet<>();
        try (ResultSet resultSet = databaseConnection.getTable(query)) {
            while (resultSet.next()) {
                table.add(provider.fetch(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return table;
    }

    @Override
    public Set<Category> categories() {
        return fetchTable(QueryProvider.SELECT_ALL_FROM_CATEGORY, resultSet -> new Category(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                resultSet.getString(QueryProvider.CATEGORY_NAME)));
    }

    @Override
    public Set<Color> colors() {
        return fetchTable(QueryProvider.SELECT_ALL_FROM_COLOR, resultSet -> new Color(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                resultSet.getString(QueryProvider.COLOR_SINGLE)));
    }

    @Override
    public Set<Customer> customers() {
        return fetchTable(QueryProvider.SELECT_ALL_FROM_CUSTOMER, resultSet -> new Customer(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                resultSet.getString(QueryProvider.PERSONAL_NUMBER),
                resultSet.getString(QueryProvider.USER_PASSWORD),
                resultSet.getString(QueryProvider.FIRSTNAME),
                resultSet.getString(QueryProvider.LASTNAME),
                resultSet.getString(QueryProvider.PHONE_NUMBER),
                resultSet.getString(QueryProvider.EMAIL),
                resultSet.getString(QueryProvider.ADDRESS),
                resultSet.getString(QueryProvider.POSTAL_CODE),
                resultSet.getString(QueryProvider.CITY)));
    }

    @Override
    public Set<Purchase> purchases() {
        return fetchTable(QueryProvider.PURCHASE_JOIN_QUERY, resultSet -> new Purchase(
                resultSet.getInt(QueryProvider.PURCHASE_ID),
                resultSet.getTimestamp(QueryProvider.PURCHASE_DATE),
                resultSet.getTimestamp(QueryProvider.PURCHASE_UPDATED),
                new Customer(
                        resultSet.getInt(QueryProvider.CUSTOMER_ID),
                        resultSet.getTimestamp(QueryProvider.CUSTOMER_CREATED),
                        resultSet.getTimestamp(QueryProvider.CUSTOMER_UPDATED),
                        resultSet.getString(QueryProvider.PERSONAL_NUMBER),
                        resultSet.getString(QueryProvider.USER_PASSWORD),
                        resultSet.getString(QueryProvider.FIRSTNAME),
                        resultSet.getString(QueryProvider.LASTNAME),
                        resultSet.getString(QueryProvider.PHONE_NUMBER),
                        resultSet.getString(QueryProvider.EMAIL),
                        resultSet.getString(QueryProvider.ADDRESS),
                        resultSet.getString(QueryProvider.POSTAL_CODE),
                        resultSet.getString(QueryProvider.CITY))));
    }

    @Override
    public Set<Shoe> shoes() {
        return fetchTable(QueryProvider.SHOE_QUERY, resultSet -> new Shoe(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                new ShoeBrand(resultSet.getInt(QueryProvider.SHOE_BRAND_ID),
                        resultSet.getTimestamp(QueryProvider.SHOE_BRAND_CREATED),
                        resultSet.getTimestamp(QueryProvider.SHOE_BRAND_UPDATED),
                        resultSet.getString(QueryProvider.SHOE_BRAND_NAME)),
                new ShoeSize(resultSet.getInt(QueryProvider.SHOE_SIZE_ID),
                        resultSet.getTimestamp(QueryProvider.SHOE_SIZE_UPDATED),
                        resultSet.getTimestamp(QueryProvider.SHOE_SIZE_CREATED),
                        resultSet.getDouble(QueryProvider.SHOE_SIZE_EURO)),
                new ShoePrice(resultSet.getInt(QueryProvider.SHOE_PRICE_ID),
                        resultSet.getTimestamp(QueryProvider.SHOE_PRICE_CREATED),
                        resultSet.getTimestamp(QueryProvider.SHOE_PRICE_UPDATED),
                        resultSet.getDouble(QueryProvider.SHOE_PRICE_SWEDISH)),
                resultSet.getInt(QueryProvider.SHOE_STOCK)));
    }

    @Override
    public Set<ShoeBrand> shoeBrands() {
        return fetchTable(QueryProvider.SELECT_ALL_FROM_BRAND, resultSet -> new ShoeBrand(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                resultSet.getString(QueryProvider.SHOE_BRAND_NAME)));
    }

    @Override
    public Set<ShoeCategoryRelation> shoeCategoryRelations() {
        return fetchTable(QueryProvider.SHOE_CATEGORY_MAP_QUERY, resultSet -> new ShoeCategoryRelation(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                new Shoe(resultSet.getInt(QueryProvider.SHOE_ID),
                        resultSet.getTimestamp(QueryProvider.SHOE_CREATED),
                        resultSet.getTimestamp(QueryProvider.SHOE_UPDATED),
                        new ShoeBrand(resultSet.getInt(QueryProvider.SHOE_BRAND_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_BRAND_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_BRAND_UPDATED),
                                resultSet.getString(QueryProvider.SHOE_BRAND_NAME)),
                        new ShoeSize(resultSet.getInt(QueryProvider.SHOE_SIZE_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_SIZE_UPDATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_SIZE_CREATED),
                                resultSet.getDouble(QueryProvider.SHOE_SIZE_EURO)),
                        new ShoePrice(resultSet.getInt(QueryProvider.SHOE_PRICE_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_PRICE_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_PRICE_UPDATED),
                                resultSet.getDouble(QueryProvider.SHOE_PRICE_SWEDISH)),
                        resultSet.getInt(QueryProvider.SHOE_STOCK)),
                new Category(resultSet.getInt(QueryProvider.CATEGORY_ID),
                        resultSet.getTimestamp(QueryProvider.CATEGORY_CREATED),
                        resultSet.getTimestamp(QueryProvider.CATEGORY_UPDATED),
                        resultSet.getString(QueryProvider.CATEGORY_NAME))));
    }

    @Override
    public Set<ShoeColorRelation> shoeColorRelations() {
        return fetchTable(QueryProvider.SHOE_COLOR_MAP_QUERY, resultSet -> new ShoeColorRelation(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                new Shoe(resultSet.getInt(QueryProvider.SHOE_ID),
                        resultSet.getTimestamp(QueryProvider.SHOE_CREATED),
                        resultSet.getTimestamp(QueryProvider.SHOE_UPDATED),
                        new ShoeBrand(resultSet.getInt(QueryProvider.SHOE_BRAND_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_BRAND_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_BRAND_UPDATED),
                                resultSet.getString(QueryProvider.SHOE_BRAND_NAME)),
                        new ShoeSize(resultSet.getInt(QueryProvider.SHOE_SIZE_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_SIZE_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_SIZE_UPDATED),
                                resultSet.getDouble(QueryProvider.SHOE_SIZE_EURO)),
                        new ShoePrice(resultSet.getInt(QueryProvider.SHOE_PRICE_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_PRICE_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_PRICE_UPDATED),
                                resultSet.getDouble(QueryProvider.SHOE_PRICE_SWEDISH)),
                        resultSet.getInt(QueryProvider.SHOE_STOCK)),
                new Color(resultSet.getInt(QueryProvider.COLOR_ID),
                        resultSet.getTimestamp(QueryProvider.COLOR_CREATED),
                        resultSet.getTimestamp(QueryProvider.COLOR_UPDATED),
                        resultSet.getString(QueryProvider.COLOR_SINGLE))));
    }

    @Override
    public Set<ShoePrice> shoePrices() {
        return fetchTable(QueryProvider.SELECT_ALL_FROM_PRICE, resultSet -> new ShoePrice(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                resultSet.getDouble(QueryProvider.SHOE_PRICE_SWEDISH)));
    }

    @Override
    public Set<ShoePurchaseRelation> shoePurchaseRelations() {
        return fetchTable(QueryProvider.SHOE_PURCHASE_MAP_QUERY, resultSet -> new ShoePurchaseRelation(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                new Shoe(resultSet.getInt(QueryProvider.SHOE_ID),
                        resultSet.getTimestamp(QueryProvider.SHOE_CREATED),
                        resultSet.getTimestamp(QueryProvider.SHOE_UPDATED),
                        new ShoeBrand(resultSet.getInt(QueryProvider.SHOE_BRAND_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_BRAND_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_BRAND_UPDATED),
                                resultSet.getString(QueryProvider.SHOE_BRAND_NAME)),
                        new ShoeSize(resultSet.getInt(QueryProvider.SHOE_SIZE_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_SIZE_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_SIZE_UPDATED),
                                resultSet.getDouble(QueryProvider.SHOE_SIZE_EURO)),
                        new ShoePrice(resultSet.getInt(QueryProvider.SHOE_PRICE_ID),
                                resultSet.getTimestamp(QueryProvider.SHOE_PRICE_CREATED),
                                resultSet.getTimestamp(QueryProvider.SHOE_PRICE_UPDATED),
                                resultSet.getDouble(QueryProvider.SHOE_PRICE_SWEDISH)),
                        resultSet.getInt(QueryProvider.SHOE_STOCK)),
                new Purchase(resultSet.getInt(QueryProvider.PURCHASE_ID),
                        resultSet.getTimestamp(QueryProvider.PURCHASE_DATE),
                        resultSet.getTimestamp(QueryProvider.PURCHASE_UPDATED),
                        new Customer(resultSet.getInt(QueryProvider.CUSTOMER_ID),
                                resultSet.getTimestamp(QueryProvider.CUSTOMER_CREATED),
                                resultSet.getTimestamp(QueryProvider.CUSTOMER_UPDATED),
                                resultSet.getString(QueryProvider.PERSONAL_NUMBER),
                                resultSet.getString(QueryProvider.USER_PASSWORD),
                                resultSet.getString(QueryProvider.FIRSTNAME),
                                resultSet.getString(QueryProvider.LASTNAME),
                                resultSet.getString(QueryProvider.PHONE_NUMBER),
                                resultSet.getString(QueryProvider.EMAIL),
                                resultSet.getString(QueryProvider.ADDRESS),
                                resultSet.getString(QueryProvider.POSTAL_CODE),
                                resultSet.getString(QueryProvider.CITY))),
                resultSet.getInt(QueryProvider.QUANTITY)));
    }

    @Override
    public Set<ShoeSize> shoeSizes() {
        return fetchTable(QueryProvider.SELECT_ALL_FROM_SIZE, resultSet -> new ShoeSize(
                resultSet.getInt(QueryProvider.ID),
                resultSet.getTimestamp(QueryProvider.CREATED),
                resultSet.getTimestamp(QueryProvider.UPDATED),
                resultSet.getDouble(QueryProvider.SHOE_SIZE_EURO)));
    }

    @Override
    public String addToCart(int customerId, int purchaseId, int shoeId, int quantity) {
        CallableStatement callableStatement = databaseConnection.getStoredProcedure();
        try {
            callableStatement.setInt(FIRST_INDEX, customerId);
            callableStatement.setInt(SECOND_INDEX, purchaseId);
            callableStatement.setInt(THIRD_INDEX, shoeId);
            callableStatement.setInt(FOURTH_INDEX, quantity);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ADDED_TO_CART_MESSAGE;
    }


}
