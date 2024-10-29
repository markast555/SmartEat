package classes;

public class Dish {

    private String name;

    private float calorieContent;

    public Dish(String name, float calorieContent) {
        this.name = name;
        this.calorieContent = calorieContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalorieContent() {
        return calorieContent;
    }

    public void setCalorieContent(float calorieContent) {
        this.calorieContent = calorieContent;
    }
}