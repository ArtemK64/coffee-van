package type;

import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

@Data
public class Grain extends Coffee {
    private String typeOfProcessingBeans;

    Grain(String brand, String quality, double weight, int price, String typeOfProcessingBeans) {
        super(brand, quality, weight, price);
        this.typeOfProcessingBeans = typeOfProcessingBeans;
    }

    public Grain() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Grain grain = (Grain) o;
        return Objects.equals(typeOfProcessingBeans, grain.typeOfProcessingBeans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeOfProcessingBeans);
    }

    @Override
    public String toString() {
        return "Grain{" +
                "typeOfProcessingBeans='" + typeOfProcessingBeans + '\'' +
                ", brand='" + brand + '\'' +
                ", quality='" + quality + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }

    public final void readGrainCoffeeFile(List<Grain> grainCoffeeList) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("groundCoffee.txt"), StandardCharsets.UTF_8)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isBlank()) {
                    Matcher matcher = Pattern.compile("\\w+").matcher(line);
                    List<String> listOfGrainCoffeeFileLine = new ArrayList<>();
                    while (matcher.find()) {
                        listOfGrainCoffeeFileLine.add(matcher.group());
                    }
                    grainCoffeeList.add(new Grain(
                            listOfGrainCoffeeFileLine.get(0),
                            listOfGrainCoffeeFileLine.get(1),
                            Double.parseDouble(listOfGrainCoffeeFileLine.get(2)),
                            Integer.parseInt(listOfGrainCoffeeFileLine.get(3)),
                            listOfGrainCoffeeFileLine.get(4)
                    ));
                }
            }
        }
    }

    public final void sortByCorrelationOfWeightPrice(List<Grain> grainCoffeeList) {
        System.out.println("\nSorted grain coffee and correlation weight by price\n");
        Map<Grain, Double> sortedHashMap = new HashMap<>();
        for (Grain grain: grainCoffeeList) {
            sortedHashMap.put(grain, grain.weight / grain.price);
        }
        sortedHashMap = sortedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x1, x2) -> x1, LinkedHashMap::new));

        for (Grain grain: sortedHashMap.keySet()) {
            Double correlationValue = sortedHashMap.get(grain);
            System.out.println("Coffee: " + grain + "\nCorrelation: " + correlationValue);
        }
    }

    public final List<Grain> findQuality(List<Grain> grainCoffeeList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nQuality (low / medium / high) - ");
        String inputQuality = scanner.next();
        List<Grain> resultGrainCoffeeList = new ArrayList<>();

        switch (inputQuality.toLowerCase()) {
            case "high" -> {
                for (Grain grain: grainCoffeeList) {
                    if (grain.quality.equalsIgnoreCase("high")) {
                        resultGrainCoffeeList.add(grain);
                    }
                }
            }
            case "medium" -> {
                for (Grain grain: grainCoffeeList) {
                    if (grain.quality.equalsIgnoreCase("medium")) {
                        resultGrainCoffeeList.add(grain);
                    }
                }
            }
            case "low" -> {
                for (Grain grain: grainCoffeeList) {
                    if (grain.quality.equalsIgnoreCase("low")) {
                        resultGrainCoffeeList.add(grain);
                    }
                }
            }
            default -> throw new IllegalArgumentException("Incorrect input value");
        }

        if (resultGrainCoffeeList.isEmpty()) {
            System.out.println("List is empty");
        }

        return resultGrainCoffeeList;
    }
}