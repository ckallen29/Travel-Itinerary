package com.example.d308.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    private double excursionPrice;
    public String excursionDate;
    public String excursionNote;
    private int vacationID;

    public Excursion(int excursionID,
                     String excursionName,
                     double excursionPrice,
                     String excursionDate,
                     String excursionNote,
                     int vacationID) { //Code -> Generate: Constructor
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.excursionPrice = excursionPrice;
        this.excursionDate = excursionDate;
        this.excursionNote = excursionNote;
        this.vacationID = vacationID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public double getExcursionPrice() {
        return excursionPrice;
    }

    public void setExcursionPrice(double excursionPrice) {
        this.excursionPrice = excursionPrice;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String eDate) {
        this.excursionDate = eDate;
    }

    public String getExcursionNote() {
        return excursionNote;
    }

    public void setExcursionNote(String note) {
        this.excursionNote = note;
    }
}
