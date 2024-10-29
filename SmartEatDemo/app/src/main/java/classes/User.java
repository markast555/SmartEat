package classes;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private UUID id;
    private String login;
    private String password;
    private String name;
    private Sex sex;
    private LocalDate dateOfBirth;
    private int height;
    private float weight;

    private String phoneNumber;

    private PhysicalActivityLevel physicalActivityLevel;

    private Goals goals;

    private String individualCharacteristics;

    public static UUID getUid() {
        return UUID.randomUUID();
    }

    public User(UUID id, String login, String password, String name, Sex sex, LocalDate dateOfBirth, int height, float weight, String phoneNumber, PhysicalActivityLevel physicalActivityLevel, Goals goals, String individualCharacteristics) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.phoneNumber = phoneNumber;
        this.physicalActivityLevel = physicalActivityLevel;
        this.goals = goals;
        this.individualCharacteristics = individualCharacteristics;
    }

    public User(String login, String password, String name, Sex sex, LocalDate dateOfBirth, int height, float weight, String phoneNumber, PhysicalActivityLevel physicalActivityLevel, Goals goals, String individualCharacteristics) {
        this.id = getUid();
        this.login = login;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.phoneNumber = phoneNumber;
        this.physicalActivityLevel = physicalActivityLevel;
        this.goals = goals;
        this.individualCharacteristics = individualCharacteristics;
    }

    public User() {
        this.id = getUid();
        this.login = "login";
        this.password = "password";
        this.name = "name";
        //this.sex = Sex.MALE;
        this.dateOfBirth = LocalDate.of(1990, 5, 15);;
        this.height = 180;
        this.weight = 70.5F;
        this.phoneNumber = "79208889988";
        this.physicalActivityLevel = PhysicalActivityLevel.ModerateLevelOfActivity;
        //this.goals = Goals.ImprovingHealth;
        this.individualCharacteristics = null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhysicalActivityLevel getPhysicalActivityLevel() {
        return physicalActivityLevel;
    }

    public void setPhysicalActivityLevel(PhysicalActivityLevel physicalActivityLevel) {
        this.physicalActivityLevel = physicalActivityLevel;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public String getIndividualCharacteristics() {
        return individualCharacteristics;
    }

    public void setIndividualCharacteristics(String individualCharacteristics) {
        this.individualCharacteristics = individualCharacteristics;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", dateOfBirth=" + dateOfBirth +
                ", height=" + height +
                ", weight=" + weight +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", physicalActivityLevel=" + physicalActivityLevel +
                ", goals=" + goals +
                ", individualCharacteristics='" + individualCharacteristics + '\'' +
                '}';
    }
}