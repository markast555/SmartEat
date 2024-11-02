package classes;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private UUID idUser; //Первичный ключ, уникальное значение
    private String login; //Уникальное значение
    private String password;
    private Sex sex;
    private LocalDate dateOfBirth;
    private int height;
    private float weight;
    private PhysicalActivityLevel levelOfPhysicalActivity;
    private Goals goals;
    private String phoneNumber; //Уникальное значение
    private int calorieNorm;

    public User(UUID idUser, String login, String password, Sex sex, LocalDate dateOfBirth, int height, float weight, PhysicalActivityLevel levelOfPhysicalActivity, Goals goals, String phoneNumber, int calorieNorm) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.levelOfPhysicalActivity = levelOfPhysicalActivity;
        this.goals = goals;
        this.phoneNumber = phoneNumber;
        this.calorieNorm = calorieNorm;
    }

//    public User(String login, String password, Sex sex, LocalDate dateOfBirth, int height, float weight, PhysicalActivityLevel levelOfPhysicalActivity, Goals goals, String phoneNumber) {
//        this.idUser = VariableGenerator.getUid();
//        this.login = login;
//        this.password = password;
//        this.sex = sex;
//        this.dateOfBirth = dateOfBirth;
//        this.height = height;
//        this.weight = weight;
//        this.levelOfPhysicalActivity = levelOfPhysicalActivity;
//        this.goals = goals;
//        this.phoneNumber = phoneNumber;
//    }

    public User(){
        this.idUser = VariableGenerator.getUid();
        this.login = VariableGenerator.generateRandomLogin();
        this.password = "password";
        this.sex = Sex.MALE;
        this.dateOfBirth = LocalDate.of(1990, 5, 15);;
        this.height = 180;
        this.weight = 70.5F;
        this.phoneNumber = VariableGenerator.generateRandomPhoneNumber();
        this.levelOfPhysicalActivity = PhysicalActivityLevel.ModerateLevelOfActivity;
        this.goals = Goals.ImprovingHealth;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}