package classes;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Diary {

    private UUID idDiary; //Первичный ключ, уникальное значение
    private UUID idUser;
    private LocalDateTime eatingTime;
    private Dish dish;

    public Diary(UUID idDiary, UUID idUser, LocalDateTime eatingTime, Dish dish) {
        this.idDiary = idDiary;
        this.idUser = idUser;
        this.eatingTime = eatingTime;
        this.dish = dish;
    }

    public Diary(){
        this.idDiary = VariableGenerator.getUid();
        this.eatingTime = LocalDateTime.now();
    }


    public String getDate() {
        if (eatingTime == null) {
            return "Дата не известна";
        }
        try {
            LocalDate date = eatingTime.toLocalDate();
            return date.getYear() + "." + getCorrectNumber(date.getMonthValue()) + "." + getCorrectNumber(date.getDayOfMonth());
        } catch (Exception e) {
            return "Дата не известна";
        }
    }

    public String getTime() {
        if (eatingTime == null) {
            return "Время не известно";
        }
        try {
            LocalTime time = eatingTime.toLocalTime();
            return time.getHour() + ":" + getCorrectNumber(time.getMinute());
        } catch (Exception e) {
            return "Время не известно";
        }
    }

    private String getCorrectNumber(int number){
        String strNumber = String.valueOf(number);
        if (strNumber.length() == 1){
            return "0" + strNumber;
        }else {
            return strNumber;
        }

    }

    public UUID getIdDiary() {
        return idDiary;
    }

    public void setIdDiary(UUID idDiary) {
        this.idDiary = idDiary;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getEatingTime() {
        return eatingTime;
    }

    public void setEatingTime(LocalDateTime eatingTime) {
        this.eatingTime = eatingTime;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "idDiary=" + idDiary +
                ", idUser=" + idUser +
                ", eatingTime=" + eatingTime +
                ", dish=(" + dish.toString() +
                ")}";
    }
}
