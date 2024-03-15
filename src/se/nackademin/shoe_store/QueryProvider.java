package se.nackademin.shoe_store;

public class QueryProvider {

    private static QueryProvider instance;

    public static QueryProvider getInstance() {
        if (instance == null) {
            instance = new QueryProvider();
        }
        return instance;
    }

    public static final String SHOE_QUERY = """
            SELECT
                Shoe.id, Shoe.created, Shoe.updated,
                ShoeBrand.id AS shoeBrandId, ShoeBrand.created AS shoeBrandCreated, ShoeBrand.updated AS shoeBrandUpdated, ShoeBrand.brand,
                ShoeSize.id AS shoeSizeId, ShoeSize.created AS shoeSizeCreated, ShoeSize.updated AS shoeSizeUpdated, ShoeSize.euroSize,
                ShoePrice.id AS shoePriceId, ShoePrice.created AS shoePriceCreated, ShoePrice.updated AS shoePriceUpdated, ShoePrice.swedishFigure,
                Shoe.stock
            FROM Shoe
                INNER JOIN ShoeBrand ON Shoe.brandId = ShoeBrand.id
                INNER JOIN ShoeSize ON Shoe.sizeId = ShoeSize.id
                INNER JOIN ShoePrice ON Shoe.priceId = ShoePrice.id
                INNER JOIN ShoeColorRelation ON ShoeColorRelation.shoeId = Shoe.id
                INNER JOIN Color ON ShoeColorRelation.colorId = Color.id;""";

    public static final String PURCHASE_JOIN_QUERY = """
            SELECT
            	Purchase.id AS purchaseId, Purchase.orderDateTime, Purchase.updated AS purchaseUpdated,
            	Customer.id AS customerId,
                Customer.created AS customerCreated,
                Customer.updated AS customerUpdated,
                Customer.personalNumber,
                Customer.userPassword,
                Customer.firstName,
                Customer.lastName,
                Customer.phoneNumber,
                Customer.email,
            	Customer.address,
            	Customer.postalCode,
            	Customer.city
            FROM Purchase
            	INNER JOIN Customer ON Purchase.customerId = Customer.id;""";

    public static final String SHOE_COLOR_MAP_QUERY = """
            SELECT
            	ShoeColorRelation.id, ShoeColorRelation.created, ShoeColorRelation.updated,
            	Shoe.id AS shoeId, Shoe.created AS shoeCreated, Shoe.updated AS shoeUpdated,
            	ShoeBrand.id AS shoeBrandId, ShoeBrand.created AS shoeBrandCreated, ShoeBrand.updated AS shoeBrandUpdated, ShoeBrand.brand,
            	ShoeSize.id AS shoeSizeId, ShoeSize.created AS shoeSizeCreated, ShoeSize.updated AS shoeSizeUpdated, ShoeSize.euroSize,
                ShoePrice.id AS shoePriceId, ShoePrice.created AS shoePriceCreated, ShoePrice.updated AS shoePriceUpdated, ShoePrice.swedishFigure,
                Shoe.stock,
                Color.id AS colorId, Color.created AS colorCreated, Color.updated AS colorUpdated, Color.singleColor
            FROM ShoeColorRelation
            	INNER JOIN Shoe ON Shoe.id = ShoeColorRelation.shoeId
                INNER JOIN Color ON Color.id = ShoeColorRelation.colorId
                INNER JOIN ShoeBrand ON ShoeBrand.id = Shoe.brandId
                INNER JOIN ShoeSize ON ShoeSize.id = Shoe.sizeId
            	INNER JOIN ShoePrice ON ShoePrice.id = Shoe.priceId;""";

    public static final String SHOE_CATEGORY_MAP_QUERY = """
            SELECT
            	ShoeCategoryRelation.id, ShoeCategoryRelation.created, ShoeCategoryRelation.updated,
            	Shoe.id AS shoeId, Shoe.created AS shoeCreated, Shoe.updated AS shoeUpdated,
            	ShoeBrand.id AS shoeBrandId, ShoeBrand.created AS shoeBrandCreated, ShoeBrand.updated AS shoeBrandUpdated, ShoeBrand.brand,
            	ShoeSize.id AS shoeSizeId, ShoeSize.created AS shoeSizeCreated, ShoeSize.updated AS shoeSizeUpdated, ShoeSize.euroSize,
                ShoePrice.id AS shoePriceId, ShoePrice.created AS shoePriceCreated, ShoePrice.updated AS shoePriceUpdated, ShoePrice.swedishFigure,
                Shoe.stock,
                Category.id AS categoryId, Category.created AS categoryCreated, Category.updated AS categoryUpdated, Category.categoryName
            FROM ShoeCategoryRelation
            	INNER JOIN Shoe ON Shoe.id = ShoeCategoryRelation.shoeId
                INNER JOIN Category ON Category.id = ShoeCategoryRelation.categoryId
                INNER JOIN ShoeBrand ON ShoeBrand.id = Shoe.brandId
                INNER JOIN ShoeSize ON ShoeSize.id = Shoe.sizeId
            	INNER JOIN ShoePrice ON ShoePrice.id = Shoe.priceId;""";

    public static final String SHOE_PURCHASE_MAP_QUERY = """
            SELECT
            	ShoePurchaseRelation.id, ShoePurchaseRelation.created, ShoePurchaseRelation.updated,
            	Shoe.id AS shoeId, Shoe.created AS shoeCreated, Shoe.updated AS shoeUpdated,
            	ShoeBrand.id AS shoeBrandId, ShoeBrand.created AS shoeBrandCreated, ShoeBrand.updated AS shoeBrandUpdated, ShoeBrand.brand,
            	ShoeSize.id AS shoeSizeId, ShoeSize.created AS shoeSizeCreated, ShoeSize.updated AS shoeSizeUpdated, ShoeSize.euroSize,
                ShoePrice.id AS shoePriceId, ShoePrice.created AS shoePriceCreated, ShoePrice.updated AS shoePriceUpdated, ShoePrice.swedishFigure,
                Shoe.stock,
                Purchase.id AS purchaseId, Purchase.orderDateTime, Purchase.updated AS purchaseUpdated,
            	Customer.id AS customerId,
            		Customer.created AS customerCreated,
            		Customer.updated AS customerUpdated,
            		Customer.personalNumber,
            		Customer.userPassword,
            		Customer.firstName,
            		Customer.lastName,
            		Customer.phoneNumber,
            		Customer.email,
            		Customer.address,
            		Customer.postalCode,
            		Customer.city,
                ShoePurchaseRelation.quantity as quantity
            FROM ShoePurchaseRelation
            	INNER JOIN Shoe ON Shoe.id = ShoePurchaseRelation.shoeId
                INNER JOIN ShoeBrand ON ShoeBrand.id = Shoe.brandId
                INNER JOIN ShoeSize ON ShoeSize.id = Shoe.sizeId
            	INNER JOIN ShoePrice ON ShoePrice.id = Shoe.priceId
                INNER JOIN Purchase ON Purchase.id = ShoePurchaseRelation.purchaseId
                INNER JOIN Customer ON Customer.id = Purchase.customerId;""";


    public static final String ID = "id";
    public static final String CREATED = "created";
    public static final String UPDATED = "updated";

    public static final String SHOE_ID = "shoeId";
    public static final String SHOE_CREATED = "shoeCreated";
    public static final String SHOE_UPDATED = "shoeUpdated";
    public static final String SHOE_STOCK = "stock";

    public static final String SHOE_BRAND_ID = "shoeBrandId";
    public static final String SHOE_BRAND_CREATED = "shoeBrandCreated";
    public static final String SHOE_BRAND_UPDATED = "shoeBrandUpdated";
    public static final String SHOE_BRAND_NAME = "brand";

    public static final String SHOE_SIZE_ID = "shoeSizeId";
    public static final String SHOE_SIZE_CREATED = "shoeSizeCreated";
    public static final String SHOE_SIZE_UPDATED = "shoeSizeUpdated";
    public static final String SHOE_SIZE_EURO = "euroSize";

    public static final String SHOE_PRICE_ID = "shoePriceId";
    public static final String SHOE_PRICE_UPDATED = "shoePriceCreated";
    public static final String SHOE_PRICE_CREATED = "shoePriceUpdated";
    public static final String SHOE_PRICE_SWEDISH = "swedishFigure";

    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_UPDATED = "categoryCreated";
    public static final String CATEGORY_CREATED = "categoryUpdated";
    public static final String CATEGORY_NAME = "categoryName";

    public static final String COLOR_ID = "colorId";
    public static final String COLOR_CREATED = "colorCreated";
    public static final String COLOR_UPDATED = "colorUpdated";
    public static final String COLOR_SINGLE = "singleColor";

    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_CREATED = "customerCreated";
    public static final String CUSTOMER_UPDATED = "customerUpdated";
    public static final String PERSONAL_NUMBER = "personalNumber";
    public static final String USER_PASSWORD = "userPassword";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";

    public static final String PURCHASE_ID = "purchaseId";
    public static final String PURCHASE_DATE = "orderDateTime";
    public static final String PURCHASE_UPDATED = "purchaseUpdated";

    public static final String QUANTITY = "quantity";

    public static final String SELECT_ALL_FROM_CUSTOMER = "SELECT * FROM Customer;";
    public static final String SELECT_ALL_FROM_BRAND = "SELECT * FROM ShoeBrand;";
    public static final String SELECT_ALL_FROM_SIZE = "SELECT * FROM ShoeSize;";
    public static final String SELECT_ALL_FROM_PRICE = "SELECT * FROM ShoePrice;";
    public static final String SELECT_ALL_FROM_CATEGORY = "SELECT * FROM Category;";
    public static String SELECT_ALL_FROM_COLOR = "SELECT * FROM Color;";

}
