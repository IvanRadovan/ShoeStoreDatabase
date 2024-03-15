package se.nackademin.shoe_store;

public interface Printable {

        <T> void print(T text, ANSI color);

        <T> void println(T text, ANSI color);
}
