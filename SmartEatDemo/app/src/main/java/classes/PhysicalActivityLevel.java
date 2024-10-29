package classes;

public enum PhysicalActivityLevel {
    SedentaryLifestyle("Сидячий образ жизни"),
    LowLevelOfActivity("Низкий уровень активности"),
    ModerateLevelOfActivity("Умеренный уровень активности"),

    HighLevelOfActivity("Высокий уровень активности");

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
        throw new IllegalArgumentException("Нет дня с типом: " + type);
    }
}
