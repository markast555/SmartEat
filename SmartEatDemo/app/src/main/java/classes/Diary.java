package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Diary {

    private UUID idDiary; //Первичный ключ, уникальное значение
    private LocalDateTime eatingTime;
    private Category category;
    private ArrayList<Dish> dishes = new ArrayList<Dish>();

    public Diary(UUID idDiary, LocalDateTime eatingTime, Category category, ArrayList<Dish> dishes) {
        this.idDiary = idDiary;
        this.eatingTime = eatingTime;
        this.category = category;
        this.dishes = dishes;
    }

    public Diary(LocalDateTime eatingTime, Category category, ArrayList<Dish> dishes) {
        this.idDiary = VariableGenerator.getUid();
        this.eatingTime = eatingTime;
        this.category = category;
        this.dishes = dishes;
    }

    public Diary(){
        this.idDiary = VariableGenerator.getUid();
        this.eatingTime = LocalDateTime.now();
        this.category = Category.Lunch;
        this.dishes.add(new Dish());
    }

    public UUID getIdDiary() {
        return idDiary;
    }

    public void setIdDiary(UUID idDiary) {
        this.idDiary = idDiary;
    }

    public LocalDateTime getEatingTime() {
        return eatingTime;
    }

    public void setEatingTime(LocalDateTime eatingTime) {
        this.eatingTime = eatingTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "idDiary=" + idDiary +
                ", eatingTime=" + eatingTime +
                ", category=" + category +
                ", dishes=" + dishes +
                '}';
    }
}
