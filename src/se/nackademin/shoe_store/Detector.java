package se.nackademin.shoe_store;

import se.nackademin.shoe_store.tables.Table;

import java.util.Set;

@FunctionalInterface
public interface Detector<T extends Table> {

    boolean check(Set<T> table, String data);
}
