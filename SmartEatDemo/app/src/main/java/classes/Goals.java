package classes;

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
}
