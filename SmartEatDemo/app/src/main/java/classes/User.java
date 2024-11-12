package classes;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class User implements Parcelable, Cloneable {

    private UUID idUser; //Первичный ключ, уникальное значение
    private String login; //Уникальное значение
    private String password;
    private Sex sex;
    private LocalDate dateOfBirth;
    private int height;
    private float weight;
    private PhysicalActivityLevel levelOfPhysicalActivity;
    private Goals goals;
    private String gmail; //Уникальное значение
    private int calorieNorm;


    public User(UUID idUser, String login, String password, Sex sex, LocalDate dateOfBirth, int height, float weight, PhysicalActivityLevel levelOfPhysicalActivity, Goals goals, String gmail, int calorieNorm) {
        this.idUser = idUser;
        this.login = login;
        this.password = VariableGenerator.hashPassword(password);
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.levelOfPhysicalActivity = levelOfPhysicalActivity;
        this.goals = goals;
        this.gmail = gmail;
        this.calorieNorm = calorieNorm;
    }


    public User(){
        this.idUser = VariableGenerator.getUid();
        this.login = VariableGenerator.generateRandomLogin();
        this.password = VariableGenerator.hashPassword("password");
        this.sex = Sex.MALE;
        this.dateOfBirth = LocalDate.of(1990, 5, 15);;
        this.height = 180;
        this.weight = 70.5F;
        this.gmail = VariableGenerator.generateRandomGmail();
        this.levelOfPhysicalActivity = PhysicalActivityLevel.ModerateActivity;
        this.goals = Goals.ImprovingHealth;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public PhysicalActivityLevel getLevelOfPhysicalActivity() {
        return levelOfPhysicalActivity;
    }

    public void setLevelOfPhysicalActivity(PhysicalActivityLevel levelOfPhysicalActivity) {
        this.levelOfPhysicalActivity = levelOfPhysicalActivity;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public int getCalorieNorm() {
        return calorieNorm;
    }

    public void setCalorieNorm(int calorieNorm) {
        this.calorieNorm = calorieNorm;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", dateOfBirth=" + dateOfBirth +
                ", height=" + height +
                ", weight=" + weight +
                ", levelOfPhysicalActivity=" + levelOfPhysicalActivity +
                ", goals=" + goals +
                ", gmail='" + gmail + '\'' +
                ", calorieNorm=" + calorieNorm +
                '}';
    }

    // Parcelable реализация
    protected User(Parcel in) {
        idUser = (UUID) in.readSerializable();
        login = in.readString();
        password = in.readString();
        sex = (Sex) in.readSerializable(); // Предполагается, что Sex реализует Serializable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateOfBirth = (LocalDate) in.readSerializable(); // Либо используйте другой способ
        }
        height = in.readInt();
        weight = in.readFloat();
        levelOfPhysicalActivity = (PhysicalActivityLevel) in.readSerializable(); // Предполагается, что реализует Serializable
        goals = (Goals) in.readSerializable(); // Предполагается, что Goals реализует Serializable
        gmail = in.readString();
        calorieNorm = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(idUser);
        dest.writeString(login);
        dest.writeString(password);
        dest.writeSerializable(sex); // Предполагается, что Sex реализует Serializable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dest.writeSerializable(dateOfBirth); // Либо используйте другой способ
        }
        dest.writeInt(height);
        dest.writeFloat(weight);
        dest.writeSerializable(levelOfPhysicalActivity); // Предполагается, что реализует Serializable
        dest.writeSerializable(goals); // Предполагается, что Goals реализует Serializable
        dest.writeString(gmail);
        dest.writeInt(calorieNorm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return height == user.height && Float.compare(weight, user.weight) == 0 && calorieNorm == user.calorieNorm && Objects.equals(idUser, user.idUser) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && sex == user.sex && Objects.equals(dateOfBirth, user.dateOfBirth) && levelOfPhysicalActivity == user.levelOfPhysicalActivity && goals == user.goals && Objects.equals(gmail, user.gmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, login, password, sex, dateOfBirth, height, weight, levelOfPhysicalActivity, goals, gmail, calorieNorm);
    }
}