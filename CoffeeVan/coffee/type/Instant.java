package type;

import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

@Data
public class Instant extends Coffee {
    private double percentOfAdditives;

    Instant(String brand, String quality, double weight, int price, double percentOfAdditives) {
        super(brand, quality, weight, price);
        this.percentOfAdditives = percentOfAdditives;
    }

    public Instant() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Instant instant = (Instant) o;
        return Double.compare(instant.percentOfAdditives, percentOfAdditives) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percentOfAdditives);
    }

    @Override
    public String toString() {
        return "Instant{" +
                "percentOfAdditives=" + percentOfAdditives +
                ", brand='" + brand + '\'' +
                ", quality='" + quality + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }

    public final void readInstantCoffeeFile(List<Instant> instantCoffeeList) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("instantCoffee.txt"), StandardCharsets.UTF_8)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isBlank()) {
                    Matcher matcher = Pattern.compile("\\w+").matcher(line);
                    List<String> listOfInstantCoffeeFileLine = new ArrayList<>();
                    while (matcher.find()) {
                        listOfInstantCoffeeFileLine.add(matcher.group());
                    }
                    instantCoffeeList.add(new Instant(
                            listOfInstantCoffeeFileLine.get(0),
                            listOfInstantCoffeeFileLine.get(1),
                            Double.parseDouble(listOfInstantCoffeeFileLine.get(2)),
                            Integer.parseInt(listOfInstantCoffeeFileLine.get(3)),
                            Double.parseDouble(listOfInstantCoffeeFileLine.get(4))
                    ));
                }
            }
        }
    }

    public final void sortByCorrelationOfWeightPrice(List<Instant> instantCoffeeList) {
        System.out.println("\nSorted instant coffee and correlation weight by price\n");
        Map<Instant, Double> sortedHashMap = new HashMap<>();
        for (Instant instant: instantCoffeeList) {
            sortedHashMap.put(instant, instant.weight / instant.price);
        }
        sortedHashMap = sortedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x1, x2) -> x1, LinkedHashMap::new));

        for (Instant instant: sortedHashMap.keySet()) {
            Double correlationValue = sortedHashMap.get(instant);
            System.out.println("Coffee: " + instant + "\nCorrelation: " + correlationValue);
        }
    }

    public final List<Instant> findQuality(List<Instant> instantCoffeeList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nQuality (low / medium / high) - ");
        String inputQuality = scanner.next();
        List<Instant> resultInstantCoffeeList = new ArrayList<>();

        switch (inputQuality.toLowerCase()) {
            case "high" -> {
                for (Instant instant: instantCoffeeList) {
                    if (instant.quality.equalsIgnoreCase("high")) {
                        resultInstantCoffeeList.add(instant);
                    }
                }
            }
            case "medium" -> {
                for (Instant instant: instantCoffeeList) {
                    if (instant.quality.equalsIgnoreCase("medium")) {
                        resultInstantCoffeeList.add(instant);
                    }
                }
            }
            case "low" -> {
                for (Instant instant: instantCoffeeList) {
                    if (instant.quality.equalsIgnoreCase("low")) {
                        resultInstantCoffeeList.add(instant);
                    }
                }
            }
            default -> throw new IllegalArgumentException("Incorrect input value");
        }

        if (resultInstantCoffeeList.isEmpty()) {
            System.out.println("List is empty");
        }

        return resultInstantCoffeeList;
    }
}
