import lombok.Data;
import type.*;

import java.util.*;

@Data
public class Van {
    public static void loadVan(List<Grain> grainCoffeeList, List<Ground> groundCoffeeList, List<Instant> instantCoffeeList, int weightLimit) {

        System.out.println("\nOur load van. Coffee\n");

        int grainCoffeeWeightSum = weightLimit / 3;
        for (Grain grain: grainCoffeeList) {
            if (grainCoffeeWeightSum > 0) {
                System.out.println(grain);
                grainCoffeeWeightSum -= grain.getWeight();
            }
        }

        int groundCoffeeWeightSum = weightLimit / 3;
        for (Ground ground: groundCoffeeList) {
            if (groundCoffeeWeightSum > 0) {
                System.out.println(ground);
                groundCoffeeWeightSum -= ground.getWeight();
            }
        }

        int instantCoffeeWeightSum = weightLimit / 3;
        for (Instant instant: instantCoffeeList) {
            if (instantCoffeeWeightSum > 0) {
                System.out.println(instant);
                instantCoffeeWeightSum -= instant.getWeight();
            }
        }
    }
}