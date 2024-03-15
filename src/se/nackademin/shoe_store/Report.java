package se.nackademin.shoe_store;

import se.nackademin.shoe_store.tables.Customer;
import se.nackademin.shoe_store.tables.Purchase;
import se.nackademin.shoe_store.tables.Shoe;
import se.nackademin.shoe_store.tables.ShoePurchaseRelation;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Report {

    private final Repository repository;
    private final ConsoleColorPrinter printer;
    private final Collator collator;
    private static final int TOP_FIVE = 5;

    private static final String LANGUAGE = "sv";
    private static final String COUNTRY = "SE";

    private static final String AMOUNT = "AMOUNT:";
    private static final String BRAND = "BRAND:";
    private static final String CITY = "CITY:";
    private static final String FIRSTNAME = "FIRSTNAME:";
    private static final String LASTNAME = "LASTNAME:";
    private static final String TOTAL = "TOTAL:";

    private static final String NAME_FORMATTED = "%.2f SEK";
    private static final String CITY_FORMATTED = "%-17s %.2f SEK";
    private static final String TOP_FIVE_FORMATTED = "%-20s %d";

    private static final String CITY_SUM_TITLE = "%-17s %s";
    private static final String BRAND_AMOUNT_TITLE = "%-20s %s";
    private static final String CUSTOMER_AMOUNT_TITLE = "%-17s %-16s %s";
    private static final String CUSTOMER_SUM_TITLE = "%-17s %-16s %s";

    public Report(Repository repository) {
        this.repository = repository;
        this.printer = new ConsoleColorPrinter();
        collator = Collator.getInstance(Locale.of(LANGUAGE, COUNTRY));
    }

    public void printCustomerByOrderAmount() {
        printer.println(CUSTOMER_AMOUNT_TITLE.formatted(FIRSTNAME, LASTNAME, AMOUNT), ANSI.YELLOW);
        repository.shoePurchaseRelations()
                .stream()
                .map(ShoePurchaseRelation::getPurchase)
                .collect(Collectors.groupingBy(Purchase::getCustomer, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingLong((Map.Entry<Customer, Long> e) -> e.getValue())
                        .reversed()
                        .thenComparing(e -> e.getKey().firstName(), collator)
                        .thenComparing(e -> e.getKey().lastName(), collator))
                .forEach(e -> printer.println(e.getKey().nameAndOrderAmount(e.getValue().intValue()), ANSI.GREEN));
    }

    public void printCustomerByOrdersTotalSum() {
        printer.println(CUSTOMER_SUM_TITLE.formatted(FIRSTNAME, LASTNAME, TOTAL), ANSI.YELLOW);
        repository.shoePurchaseRelations()
                .stream()
                .collect(Collectors.groupingBy(relation -> relation.getPurchase().getCustomer(),
                        Collectors.summingDouble(relation -> relation.getShoe().getPrice().swedishFigure() * relation.getQuantity())))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingDouble((Map.Entry<Customer, Double> e) -> e.getValue())
                        .reversed()
                        .thenComparing(e -> e.getKey().firstName(), collator)
                        .thenComparing(e -> e.getKey().lastName(), collator))
                .forEach(e -> printer.println(e.getKey().nameAndOrderAmount(NAME_FORMATTED.formatted(e.getValue())), ANSI.GREEN));
    }

    public void printOrdersTotalSumByCity() {
        printer.println(CITY_SUM_TITLE.formatted(CITY, TOTAL), ANSI.YELLOW);
        repository.shoePurchaseRelations()
                .stream()
                .collect(Collectors.groupingBy(relation -> relation.getPurchase().getCustomer().city(),
                        Collectors.summingDouble(relation -> relation.getShoe().getPrice().swedishFigure() * relation.getQuantity())))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingDouble((Map.Entry<String, Double> e) -> e.getValue())
                        .reversed()
                        .thenComparing(Map.Entry::getKey, collator))
                .forEach(entry -> printer.println(CITY_FORMATTED.formatted(entry.getKey(), entry.getValue()), ANSI.GREEN));
    }

    public void printTopFiveMostSoldShoes() {
        printer.println(BRAND_AMOUNT_TITLE.formatted(BRAND, AMOUNT), ANSI.YELLOW);
        repository.shoePurchaseRelations()
                .stream()
                .collect(Collectors.groupingBy(ShoePurchaseRelation::getShoe,
                        Collectors.summingInt(ShoePurchaseRelation::getQuantity)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Shoe, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(entry -> entry.getKey().getBrand().brand(), collator))
                .limit(TOP_FIVE)
                .forEach(entry -> printer.println(TOP_FIVE_FORMATTED.formatted(entry.getKey().getBrand().brand(), entry.getValue()), ANSI.GREEN));
    }
}
