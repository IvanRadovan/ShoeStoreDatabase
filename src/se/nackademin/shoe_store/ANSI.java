package se.nackademin.shoe_store;

public enum ANSI {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    RESET("\u001B[0m");

    private final String color;

    ANSI(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
