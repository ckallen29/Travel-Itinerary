package com.example.d308.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d308.dao.ExcursionDAO;
import com.example.d308.dao.VacationDAO;
import com.example.d308.entities.Excursion;
import com.example.d308.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version=2, exportSchema = false) //change version number to update db
public abstract class CompanionDatabaseBuilder extends RoomDatabase {

    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile CompanionDatabaseBuilder INSTANCE;

    static CompanionDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (CompanionDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), CompanionDatabaseBuilder.class, "MyCompanionDatabase.db")
                            .fallbackToDestructiveMigration()
                            //.allowMainThreadQueries() //synchronous
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
