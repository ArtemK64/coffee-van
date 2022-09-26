import type.*;

import java.io.IOException;
import java.util.*;

public class CoffeeVanMain {
    public static void main(String[] args) throws IOException {
        Grain grain = new Grain();
        List<Grain> grainCoffeeList = new ArrayList<>();
        grain.readGrainCoffeeFile(grainCoffeeList);
        grain.sortByCorrelationOfWeightPrice(grainCoffeeList);
        Coffee.printList(grain.findQuality(grainCoffeeList));

        Ground ground = new Ground();
        List<Ground> groundCoffeeList = new ArrayList<>();
        ground.readGroundCoffeeFile(groundCoffeeList);
        ground.sortByCorrelationOfWeightPrice(groundCoffeeList);
        Coffee.printList(ground.findQuality(groundCoffeeList));

        Instant instant = new Instant();
        List<Instant> instantCoffeeList = new ArrayList<>();
        instant.readInstantCoffeeFile(instantCoffeeList);
        instant.sortByCorrelationOfWeightPrice(instantCoffeeList);
        Coffee.printList(instant.findQuality(instantCoffeeList));

        System.out.print("\nInput weight for van: ");
        Scanner scanner = new Scanner(System.in);
        int inputWeight = scanner.nextInt();

        Van.loadVan(grainCoffeeList, groundCoffeeList, instantCoffeeList, inputWeight);
    }
}