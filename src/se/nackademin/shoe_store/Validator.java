package se.nackademin.shoe_store;

import se.nackademin.shoe_store.tables.Table;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private final ConsoleColorPrinter printer;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String PROMPT_ERROR = "Illegal input: ";
    private static final String WHITESPACE = " ";

    private final Title title = string -> string.substring(0, 1).toUpperCase().concat(string.substring(1).toLowerCase());

    public Validator() {
        this.printer = new ConsoleColorPrinter();
    }

    private boolean colorExist(String color) {
        return new Repository().colors()
                .stream()
                .anyMatch(c -> c.singleColor().equalsIgnoreCase(color));
    }

    public String[] validateData(String promptMessage, String errorMessage, String regexPattern) {
        final String PROMPT_NO_COLOR_MATCH = "Color was not found";
        String input;
        String[] colors;
        Pattern pattern = Pattern.compile(regexPattern);

        while (true) {
            printer.print(promptMessage, ANSI.BLUE);
            input = SCANNER.nextLine();
            Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) {
                colors = input.split(WHITESPACE);
                for (String color : colors) {
                    boolean exists = colorExist(color);
                    if (exists) {
                        return Arrays.stream(colors).map(title::toTitle).toArray(String[]::new);
                    }
                    else printer.println(PROMPT_NO_COLOR_MATCH, ANSI.RED);
                }
            }
            else printer.println(errorMessage, ANSI.RED);
        }
    }

    public int validQuantity(Predicate<Number> predicate, String promptMessage, String errorMessage) {
        int number = 0;
        boolean valid = false;
        String input;

        while (true) {
            printer.print(promptMessage, ANSI.BLUE);
            input = SCANNER.nextLine();

            try {
                number = Integer.parseInt(input);
                valid = predicate.test(number);
            } catch (NumberFormatException e) {
                printer.print(PROMPT_ERROR, ANSI.RED);
            }
            if (valid) return number;
            else printer.println(errorMessage, ANSI.RED);
        }
    }

    public  <T extends Table> String validateData(Detector<T> detector, String promptMessage, String errorMessage, Set<T> table) {
        String input;

        while (true) {
            printer.print(promptMessage, ANSI.BLUE);
            input = SCANNER.nextLine();
            boolean match = false;

            try {
                match = detector.check(table, input);
            } catch (NumberFormatException e) {
                printer.print(PROMPT_ERROR, ANSI.RED);
            }

            if (match) return input;
            else printer.println(errorMessage, ANSI.RED);
        }
    }
}
