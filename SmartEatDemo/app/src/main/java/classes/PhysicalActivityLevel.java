package classes;

import java.util.Objects;

public enum PhysicalActivityLevel {
    SedentaryLifeStyle("Очень низкий"),
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

    public static String TranslateFromRusToEng(String type) {
        if (Objects.equals(type, "Очень низкий")){
            return "Sedentary life style";
        }
        if (Objects.equals(type, "Низкий")){
            return "Light activity";
        }
        if (Objects.equals(type, "Умеренный")){
            return "Moderate activity";
        }
        if (Objects.equals(type, "Высокий")){
            return "High activity";
        }
        else {
            return "Very high activity";
        }
    }

    public static String TranslateFromEngToRus(String type) {
        if (Objects.equals(type, "Sedentary life style")) {
            return "Очень низкий";
        }
        if (Objects.equals(type, "Light activity")) {
            return "Низкий";
        }
        if (Objects.equals(type, "Moderate activity")) {
            return "Умеренный";
        }
        if (Objects.equals(type, "High activity")) {
            return "Высокий";
        } else {
            return "Очень высокий";
        }
    }

}
