package classes;

import java.util.UUID;

public class Dish {

    private UUID idDish; //Первичный ключ, уникальное значение
    private String name; //Уникальное значение
    private int calorieContent;
    private MealType mealType;
    private String description;

    public Dish( UUID idDish, String name, MealType mealType, int calorieContent,  String description) {
        this.idDish = idDish;
        this.name = name;
        this.mealType = mealType;
        this.calorieContent = calorieContent;
        this.description = description;
    }

    public Dish() {
        this.idDish = VariableGenerator.getUid();
        this.name = "Цезарь";
        this.mealType = MealType.Salad;
        this.calorieContent = 350;
        this.description = "Салат с курицей и пармезаном";
    }


    public int getCalorieContent() {
        return calorieContent;
    }

    public void setCalorieContent(int calorieContent) {
        this.calorieContent = calorieContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public UUID getIdDish() {
        return idDish;
    }

    public void setIdDish(UUID idDish) {
        this.idDish = idDish;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "idDish=" + idDish +
                ", name='" + name + '\'' +
                ", calorieContent=" + calorieContent +
                ", mealType=" + mealType +
                ", description='" + description + '\'' +
                '}';
    }
}
