package se.nackademin.shoe_store;

import org.junit.jupiter.api.Test;
import se.nackademin.shoe_store.tables.*;

import java.util.*;
import java.util.stream.Collectors;

class LauncherTest {

    private Repository repository = new Repository();
    private static final Scanner SCANNER = new Scanner(System.in);


    Detector<ShoeBrand> checkBrand = (set, data) -> set.stream().anyMatch(shoeBrand -> shoeBrand.brand().equalsIgnoreCase(data));
    Detector<ShoeSize> checkSize = (set, data) -> set.stream().anyMatch(shoeSize -> shoeSize.euroSize() == Double.parseDouble(data));
    Detector<Color> checkColor = (set, data) -> set.stream().anyMatch(color -> color.singleColor().equalsIgnoreCase(data));


    private <T extends Table> String validateInput(Detector<T> detector, String promptMessage, String errorMessage, Set<T> table) {
        String input;

        while (true) {
            System.out.print(promptMessage);
            input = SCANNER.nextLine();
            boolean match = detector.check(table, input);

            if (match)
                return input;
            else
                System.out.println(errorMessage);
        }
    }

    @Test
    void testFindShoe() {
        String[] colors = new String[]{"Grön", "Vit", "Blå"};
        String brand = "ECCO";
        double size = 38.0;

        System.out.println(findShoe(brand, size, colors));
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
                .orElseThrow(() -> new NoSuchElementException("NO shoe"));
    }


    void testingValidator() {
        validateInput(checkBrand, "Enter brand: ", "Brand was not found", repository.shoeBrands());
    }


}