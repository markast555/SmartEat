package classes;

public enum PhysicalActivityLevel {
    SedentaryLifestyle("Очень низкий"),
    LightActivity("Низкий"),
    ModerateActivity("Умеренный"),
    HighActivity("Высокий"),
    VeryHighActivity("Очень высокий");

    private final String type;

    PhysicalActivityLevel(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Статический метод для поиска по типу
    public static PhysicalActivityLevel fromType(String type) {
        for (PhysicalActivityLevel physicalActivityLevel : PhysicalActivityLevel.values()) {
            if (physicalActivityLevel.getType().equalsIgnoreCase(type)) {
                return physicalActivityLevel;
            }
        }
        throw new IllegalArgumentException("Нет такого типа активности: " + type);
    }
}
