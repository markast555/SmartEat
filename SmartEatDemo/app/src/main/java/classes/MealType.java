package classes;

public enum MealType {
    Complex("Составной"),
    Soup("Суп"),
    Drink("Напиток"),
    SideDish("Гарнир"),
    MainCourse("Основное блюдо"),
    Salad("Салат"),
    Dessert("Десерт");

    private final String type;

    MealType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Статический метод для поиска по типу
    public static MealType fromType(String type) {
        for (MealType mealType : MealType.values()) {
            if (mealType.getType().equalsIgnoreCase(type)) {
                return mealType;
            }
        }
        throw new IllegalArgumentException("Нет такого типа блюда: " + type);
    }
}
