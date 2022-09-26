package type;

import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

@Data
public class Ground extends Coffee {
    private String methodOfGrinding;

    Ground(String brand, String quality, double weight, int price, String methodOfGrinding) {
        super(brand, quality, weight, price);
        this.methodOfGrinding = methodOfGrinding;
    }

    public Ground() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ground ground = (Ground) o;
        return Objects.equals(methodOfGrinding, ground.methodOfGrinding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), methodOfGrinding);
    }

    @Override
    public String toString() {
        return "Ground{" +
                "methodOfGrinding='" + methodOfGrinding + '\'' +
                ", brand='" + brand + '\'' +
                ", quality='" + quality + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }

    public final void readGroundCoffeeFile(List<Ground> groundCoffeeList) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("groundCoffee.txt"), StandardCharsets.UTF_8)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isBlank()) {
                    Matcher matcher = Pattern.compile("\\w+").matcher(line);
                    List<String> listOfGroundCoffeeFileLine = new ArrayList<>();
                    while (matcher.find()) {
                        listOfGroundCoffeeFileLine.add(matcher.group());
                    }
                    groundCoffeeList.add(new Ground(
                            listOfGroundCoffeeFileLine.get(0),
                            listOfGroundCoffeeFileLine.get(1),
                            Double.parseDouble(listOfGroundCoffeeFileLine.get(2)),
                            Integer.parseInt(listOfGroundCoffeeFileLine.get(3)),
                            listOfGroundCoffeeFileLine.get(4)
                    ));
                }
            }
        }
    }

    public final void sortByCorrelationOfWeightPrice(List<Ground> groundCoffeeList) {
        System.out.println("\nSorted ground coffee and correlation weight by price\n");
        Map<Ground, Double> sortedHashMap = new HashMap<>();
        for (Ground ground: groundCoffeeList) {
            sortedHashMap.put(ground, ground.weight / ground.price);
        }
        sortedHashMap = sortedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x1, x2) -> x1, LinkedHashMap::new));

        for (Ground ground: sortedHashMap.keySet()) {
            Double correlationValue = sortedHashMap.get(ground);
            System.out.println("Coffee: " + ground + "\nCorrelation: " + correlationValue);
        }
    }

    public final List<Ground> findQuality(List<Ground> groundCoffeeList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nQuality (low / medium / high) - ");
        String inputQuality = scanner.next();
        List<Ground> resultGroundCoffeeList = new ArrayList<>();

        switch (inputQuality.toLowerCase()) {
            case "high" -> {
                for (Ground ground: groundCoffeeList) {
                    if (ground.quality.equalsIgnoreCase("high")) {
                        resultGroundCoffeeList.add(ground);
                    }
                }
            }
            case "medium" -> {
                for (Ground ground: groundCoffeeList) {
                    if (ground.quality.equalsIgnoreCase("medium")) {
                        resultGroundCoffeeList.add(ground);
                    }
                }
            }
            case "low" -> {
                for (Ground ground: groundCoffeeList) {
                    if (ground.quality.equalsIgnoreCase("low")) {
                        resultGroundCoffeeList.add(ground);
                    }
                }
            }
            default -> throw new IllegalArgumentException("Incorrect input value");
        }

        if (resultGroundCoffeeList.isEmpty()) {
            System.out.println("List is empty");
        }

        return resultGroundCoffeeList;
    }
}