package classes;

import java.time.LocalDateTime;
import java.util.UUID;

public class Diary {

    private UUID idDiary; //Первичный ключ, уникальное значение
    private UUID idUser;
    private UUID idDish;
    private LocalDateTime eatingTime;

    public Diary(UUID idDiary, UUID idUser, UUID idDish, LocalDateTime eatingTime) {
        this.idDiary = idDiary;
        this.idUser = idUser;
        this.idDish = idDish;
        this.eatingTime = eatingTime;
    }

    public Diary(){
        this.idDiary = VariableGenerator.getUid();
        this.idUser = VariableGenerator.getUid();
        this.idDish = VariableGenerator.getUid();
        this.eatingTime = LocalDateTime.now();
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

    public UUID getIdDish() {
        return idDish;
    }

    public void setIdDish(UUID idDish) {
        this.idDish = idDish;
    }

    public LocalDateTime getEatingTime() {
        return eatingTime;
    }

    public void setEatingTime(LocalDateTime eatingTime) {
        this.eatingTime = eatingTime;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "idDiary=" + idDiary +
                ", idUser=" + idUser +
                ", idDish=" + idDish +
                ", eatingTime=" + eatingTime +
                '}';
    }
}
