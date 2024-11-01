package classes;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Dish {

    private Set<Category> categories;
    private int calorieContent;
    private float proteins;
    private float fats;
    private float сarbohydrates;
    private String name; //Уникальное значение
    private String description;
    private MealType mealType;
    private UUID idDish; //Первичный ключ, уникальное значение

    public Dish(Set<Category> categories, int calorieContent, float proteins, float fats, float сarbohydrates, String name, String description, MealType mealType, UUID idDish) {
        this.categories = categories;
        this.calorieContent = calorieContent;
        this.proteins = proteins;
        this.fats = fats;
        this.сarbohydrates = сarbohydrates;
        this.name = name;
        this.description = description;
        this.mealType = mealType;
        this.idDish = idDish;
    }

    public Dish(Set<Category> categories, int calorieContent, float proteins, float fats, float сarbohydrates, String name, MealType mealType, String description) {
        this.categories = categories;
        this.calorieContent = calorieContent;
        this.proteins = proteins;
        this.fats = fats;
        this.сarbohydrates = сarbohydrates;
        this.name = name;
        this.description = description;
        this.mealType = mealType;
        this.idDish = VariableGenerator.getUid();
    }

    public Dish() {
        this.categories.add(Category.Lunch);
        this.categories.add(Category.Dinner);

        this.calorieContent = 350;
        this.proteins = 25.0f;
        this.fats = 20.0f;
        this.сarbohydrates = 15.0f;
        this.name = "Цезарь";
        this.description = "Салат с курицей и пармезаном";
        this.mealType = MealType.Salad;
        this.idDish = VariableGenerator.getUid();
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public int getCalorieContent() {
        return calorieContent;
    }

    public void setCalorieContent(int calorieContent) {
        this.calorieContent = calorieContent;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getСarbohydrates() {
        return сarbohydrates;
    }

    public void setСarbohydrates(float сarbohydrates) {
        this.сarbohydrates = сarbohydrates;
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

    public UUID getIdDish() {
        return idDish;
    }

    public void setIdDish(UUID idDish) {
        this.idDish = idDish;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "categories=" + categories +
                ", calorieContent=" + calorieContent +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", сarbohydrates=" + сarbohydrates +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idDish=" + idDish +
                '}';
    }
}