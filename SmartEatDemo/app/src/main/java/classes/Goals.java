package classes;

import java.util.Objects;

public enum Goals {
    WeightLoss("Снижение веса"),
    WeightGain("Набор веса"),
    MuscleMassGain("Набор мышечной массы"),
    MaintainingCurrentWeight("Поддержание текущего веса"),
    ImprovingHealth("Улучшение здоровья");

    private final String type;

    Goals(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Статический метод для поиска по типу
    public static Goals fromType(String type) {
        for (Goals goal : Goals.values()) {
            if (goal.getType().equalsIgnoreCase(type)) {
                return goal;
            }
        }
        throw new IllegalArgumentException("Нет такой цели: " + type);
    }

    public static String TranslateFromRusToEng(String type) {
        if (Objects.equals(type, "Снижение веса")){
            return "Weight loss";
        }
        if (Objects.equals(type, "Набор веса")){
            return "Weight gain";
        }
        if (Objects.equals(type, "Набор мышечной массы")){
            return "Muscle mass gain";
        }
        if (Objects.equals(type, "Поддержание текущего веса")){
            return "Maintaining current weight";
        }
        else {
            return "Improving health";
        }
    }

    public static String TranslateFromEngToRus(String type) {
        if (Objects.equals(type, "Weight loss")) {
            return "Снижение веса";
        }
        if (Objects.equals(type, "Weight gain")) {
            return "Набор веса";
        }
        if (Objects.equals(type, "Muscle mass gain")) {
            return "Набор мышечной массы";
        }
        if (Objects.equals(type, "Maintaining current weight")) {
            return "Поддержание текущего веса";
        } else {
            return "Улучшение здоровья";
        }
    }

}
