package models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataIngredient {
    private String _id;
    private String name;
    private String type;
    private int proteins;
    private int fat;
    private int carbohydrates;
    private int calories;
    private int price;
    private String image;
    private String image_mobile;
    private String image_large;
    private int __v;

    // Дополнительные методы для удобства
    public boolean isBun() {
        return "bun".equals(type);
    }

    public boolean isMain() {
        return "main".equals(type);
    }

    public boolean isSauce() {
        return "sauce".equals(type);
    }
}