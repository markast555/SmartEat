package classes;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Diary {

    private LocalDateTime dateTime;

    private ArrayList<Dish> dishes = new ArrayList<Dish>();

    public Diary(LocalDateTime dateTime, ArrayList<Dish> dishes) {
        this.dateTime = dateTime;
        this.dishes = dishes;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }
}
