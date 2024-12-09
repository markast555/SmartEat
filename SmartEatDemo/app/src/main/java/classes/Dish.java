package classes;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.UUID;

public class Dish implements Parcelable {

    private UUID idDish; //Первичный ключ, уникальное значение
    private String name; //Уникальное значение
    private int calorieContent;
    private MealType mealType;
    private String description;

    public Dish( UUID idDish, String name, int calorieContent, MealType mealType,  String description) {
        this.idDish = idDish;
        this.name = name;
        this.calorieContent = calorieContent;
        this.mealType = mealType;
        this.description = description;
    }

    public Dish() {
        this.idDish = VariableGenerator.getUid();
        this.name = "Цезарь";
        this.calorieContent = 350;
        this.mealType = MealType.Salad;
        this.description = "Салат с курицей и пармезаном";
    }


    public int getCalorieContent() {
        return calorieContent;
    }

    public void setCalorieContent(int calorieContent) {
        this.calorieContent = calorieContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public UUID getIdDish() {
        return idDish;
    }

    public void setIdDish(UUID idDish) {
        this.idDish = idDish;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "idDish=" + idDish +
                ", name='" + name + '\'' +
                ", calorieContent=" + calorieContent +
                ", mealType=" + mealType +
                ", description='" + description + '\'' +
                '}';
    }

    // Parcelable реализация
    protected Dish(Parcel in) {
        idDish = (UUID) in.readSerializable();
        name = in.readString();
        calorieContent = in.readInt();
        mealType = (MealType) in.readSerializable();
        description = in.readString();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(idDish);
        dest.writeString(name);
        dest.writeInt(calorieContent);
        dest.writeSerializable(mealType);
        dest.writeString(description);
    }
}
