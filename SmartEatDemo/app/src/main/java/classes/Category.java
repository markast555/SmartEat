package classes;


public enum Category {

    Breakfast ("Завтрак"),
    Lunch("Обед"),
    Dinner("Ужин"),
    Snack("Перекус");

    private final String type;

    Category(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Статический метод для поиска по типу
    public static Category fromType(String type) {
        for (Category category : Category.values()) {
            if (category.getType().equalsIgnoreCase(type)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Нет такой категории: " + type);
    }
}
