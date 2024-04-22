package com.example.d308.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    public double vacationPrice;
    public String vacationHotel;
    public String vacationStartDate;
    public String vacationEndDate;

    public Vacation(
            int vacationID,
            String vacationName,
            double vacationPrice,
            String vacationHotel,
            String vacationStartDate,
            String vacationEndDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.vacationPrice = vacationPrice;
        this.vacationHotel = vacationHotel;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public double getVacationPrice() {
        return vacationPrice;
    }

    public void setVacationPrice(double vacationPrice) {
        this.vacationPrice = vacationPrice;
    }

    public String getVacationHotel() {
        return vacationHotel;
    }

    public void setVacationHotel(String vacationHotel) {
        this.vacationHotel = vacationHotel;
    }

    public String getVacationStartDate() {
        return vacationStartDate;
    }

    public void setVacationStartDate(String startDate) {
        this.vacationStartDate = startDate;
    }

    public String getVacationEndDate() {
        return vacationEndDate;
    }

    public void setVacationEndDate(String endDate) {
        this.vacationEndDate = endDate;
    }
}
