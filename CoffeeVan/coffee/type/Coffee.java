package type;

import lombok.Data;

import java.util.List;

@Data
public class Coffee {
    protected String brand;
    protected String quality;
    protected double weight;
    protected int price;

    Coffee(String brand, String quality, double weight, int price) {
        this.brand = brand;
        this.quality = quality;
        this.weight = weight;
        this.price = price;
    }

    Coffee() {}

    public static <T> void printList(List<T> list) {
        for (T t: list) {
            System.out.println(t);
        }
    }
}