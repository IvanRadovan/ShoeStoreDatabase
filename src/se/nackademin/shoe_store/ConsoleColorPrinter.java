package se.nackademin.shoe_store;

public class ConsoleColorPrinter implements Printable {

    @Override
    public <T> void print(T text, ANSI color) {
        System.out.printf("%s%s%s", color, text, ANSI.RESET);
    }

    @Override
    public <T> void println(T text, ANSI color) {
        System.out.printf("%s%s%s%n", color, text, ANSI.RESET);
    }
}
