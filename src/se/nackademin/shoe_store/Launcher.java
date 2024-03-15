package se.nackademin.shoe_store;

import se.nackademin.shoe_store.tables.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Launcher {

    private Repository repository;
    private final Report report;
    private final Printable printer;
    private final Validator validator;

    private static final int NEW_ORDER = -1;
    private static final int ZERO = 0;
    private static final Scanner SCANNER = new Scanner(System.in);

    private static final String PROMPT_USERNAME = "Enter personal number: ";
    private static final String PROMPT_PASSWORD = "Enter password: ";
    private static final String PROMPT_DENIED = "Access denied.";
    private static final String PROMPT_OPTION = "Choose option: ";
    private static final String PROMPT_DEFAULT = "Choose between options 1 to %s.";
    private static final String CUSTOMER_EXCEPTION = "Customer does not exists";
    private static final String SHOE_EXCEPTION = "No shoes was found: Order was canceled";

    private static final String BRAND = "BRAND:";
    private static final String SIZE = "SIZE:";
    private static final String PRICE = "PRICE:";
    private static final String STOCK = "STOCK:";
    private static final String PROMPT_TITLES = "%-19s %-13s %-21s %-18s";

    private static final String OPTION_ONE = "1";
    private static final String OPTION_TWO = "2";
    private static final String OPTION_THEE = "3";
    private static final String OPTION_FOUR = "4";
    private static final String OPTION_FIVE = "5";

    private static final String PROMPT_BRAND = "Enter brand: ";
    private static final String PROMPT_BRAND_NOT_FOUND = "Brand does not exist in the database.";
    private static final String PROMPT_SIZE = "Enter size: ";
    private static final String PROMPT_SIZE_NOT_FOUND = "Size does not exist in the database.";
    private static final String PROMPT_QUANTITY = "Enter quantity: ";
    private static final String PROMPT_PROMPT_QUANTITY_EXCEPTION = "Quantity has to be higher than zero.";
    private static final String PROMPT_COLORS = "Enter color(s) (separated by whitespace): ";
    private static final String PROMPT_COLORS_EXCEPTION = "Separated each color by a single whitespace.";
    private static final String COLOR_PATTERN = "^\\S+( \\S+)*$";


    private static final String MENU = """
            1. Add to Cart
            2. List Shoes
            3. Reports
            4. Exit""";

    private static final String REPORT_MENU = """
            1. List customer(s) by number of orders
            2. List customer(s) by total order amount
            3. List cities by total order
            4. List top 5 most sold shoes
            5. Exit""";

    private final Detector<ShoeBrand> brandDetector = (set, input) -> set.stream().anyMatch(shoeBrand -> shoeBrand.brand().equalsIgnoreCase(input));
    private final Detector<ShoeSize> sizeDetector = (set, input) -> set.stream().anyMatch(shoeSize -> shoeSize.euroSize() == Double.parseDouble(input));
    private final Predicate<Number> checkQuantity = input -> input.doubleValue() > ZERO;

    public Launcher() {
        repository = new Repository();
        report = new Report(repository);
        printer = new ConsoleColorPrinter();
        validator = new Validator();
    }

    public void run() {
        while (true) {
            printer.print(PROMPT_USERNAME, ANSI.BLUE);
            String personalNumber = SCANNER.nextLine();

            printer.print(PROMPT_PASSWORD, ANSI.BLUE);
            String password = SCANNER.nextLine();

            boolean loginPassed;
            loginPassed = repository.customers()
                    .stream()
                    .anyMatch(customer -> customer.personalNumber().equals(personalNumber) && customer.password().equals(password));

            mainMenu(loginPassed, personalNumber);
            printer.println(PROMPT_DENIED, ANSI.RED);
        }
    }

    private void reportMenu() {
        reportLoop:
        while (true) {
            printReportMenu();
            printer.print(PROMPT_OPTION, ANSI.BLUE);
            String choice = SCANNER.nextLine();
            switch (choice) {
                case OPTION_ONE -> report.printCustomerByOrderAmount();
                case OPTION_TWO -> report.printCustomerByOrdersTotalSum();
                case OPTION_THEE -> report.printOrdersTotalSumByCity();
                case OPTION_FOUR -> report.printTopFiveMostSoldShoes();
                case OPTION_FIVE -> {
                    break reportLoop;
                }
                default -> printer.println(PROMPT_DEFAULT.formatted(OPTION_FIVE), ANSI.RED);
            }
        }
    }

    private void mainMenu(boolean loginPassed, String personalNumber) {
        while (loginPassed) {
            printMenu();
            printer.print(PROMPT_OPTION, ANSI.BLUE);
            String choice = SCANNER.nextLine();

            switch (choice) {
                case OPTION_ONE -> addToCart(personalNumber);
                case OPTION_TWO -> printShoes();
                case OPTION_THEE -> reportMenu();
                case OPTION_FOUR -> System.exit(ZERO);
                default -> printer.println(PROMPT_DEFAULT.formatted(OPTION_FOUR), ANSI.RED);
            }
        }
    }

    private void addToCart(String personalNumber) {
        String brand = validator.validateData(
                brandDetector,
                PROMPT_BRAND,
                PROMPT_BRAND_NOT_FOUND,
                repository.shoeBrands());
        double size = Double.parseDouble(validator.validateData(
                sizeDetector,
                PROMPT_SIZE,
                PROMPT_SIZE_NOT_FOUND,
                repository.shoeSizes()));
        String[] colors = validator.validateData(
                PROMPT_COLORS,
                PROMPT_COLORS_EXCEPTION,
                COLOR_PATTERN);
        int quantity = validator.validQuantity(
                checkQuantity,
                PROMPT_QUANTITY,
                PROMPT_PROMPT_QUANTITY_EXCEPTION);

        try {
            printer.println(repository.addToCart(currentCustomer(personalNumber).id(),
                    NEW_ORDER,
                    findShoe(brand, size, colors).id(),
                    quantity), ANSI.GREEN);
            repository = new Repository();
        } catch (RuntimeException e) {
            printer.println(e.getMessage(), ANSI.RED);
        }
    }

    private void printMenu() {
        printer.println(MENU, ANSI.YELLOW);
    }

    private void printReportMenu() {
        printer.println(REPORT_MENU, ANSI.YELLOW);
    }

    private void printShoes() {
        printer.println(PROMPT_TITLES.formatted(BRAND, SIZE, PRICE, STOCK), ANSI.YELLOW);
        repository.shoes()
                .stream()
                .sorted(Comparator.comparing((Shoe shoe) -> shoe.getBrand().brand())
                        .thenComparing((Shoe shoe) -> shoe.getSize().euroSize(), Comparator.reverseOrder())
                        .thenComparing((Shoe shoe) -> shoe.getPrice().swedishFigure(), Comparator.reverseOrder())
                        .thenComparing(Shoe::getStock, Comparator.reverseOrder()))
                .forEach(shoe -> printer.println(shoe, ANSI.GREEN));
    }

    private Shoe findShoe(String brand, double size, String[] colors) {
        return repository.shoeColorRelations()
                .stream()
                .filter(relation -> relation.getShoe().getBrand().brand().equalsIgnoreCase(brand) && relation.getShoe().getSize().euroSize() == size)
                .collect(Collectors.groupingBy(ShoeColorRelation::getShoe))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().stream()
                        .map(ShoeColorRelation::getColor)
                        .map(Color::singleColor)
                        .allMatch(Arrays.stream(colors).toList()::contains))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(SHOE_EXCEPTION));
    }

    private Customer currentCustomer(String personalNumber) {
        return repository.customers()
                .stream()
                .filter(customer -> customer.personalNumber().equals(personalNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(CUSTOMER_EXCEPTION));
    }

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.run();
    }
}
