package classes;

public enum Sex {
    MALE("м"),
    FEMALE("ж");

    private final String type;

    Sex(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Статический метод для поиска по типу
    public static Sex fromType(String type) {
        for (Sex sex : Sex.values()) {
            if (sex.getType().equalsIgnoreCase(type)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Нет такого пола: " + type);
    }
}
