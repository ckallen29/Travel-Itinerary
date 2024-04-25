package com.example.d308.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308.R;
import com.example.d308.database.Repository;
import com.example.d308.entities.Excursion;
import com.example.d308.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class); //send info to VacationDetails

                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.vacationRecyclerView);

        //query database
        repository = new Repository(getApplication());
        //get list of vacations
        List<Vacation> allVacations = repository.getmAllVacations();
        //add adapter to recycler view
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        //layout manager
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //put list of vacations on the recycler view using method defined in adapter
        vacationAdapter.setVacations(allVacations);

        //System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() == R.id.sample) {
            repository = new Repository(getApplication());
            //Toast.makeText(VacationList.this, "Add sample data", Toast.LENGTH_LONG).show();
            Vacation vacation = new Vacation(
                    0,
                    "Italy",
                    1000.0,
                    "Holiday Inn",
                    "12/01/24",
                    "12/07/24");
            repository.insert(vacation);

            vacation = new Vacation(
                    0,
                    "Germany",
                    1000.0,
                    "Marriot Suites",
                    "02/14/24",
                    "02/20/24");
            repository.insert(vacation);

            Excursion excursion = new Excursion(
                    0,
                    "Tour",
                    50.0,
                    "12/03/24",
                    "Enter note here",
                    1);
            repository.insert(excursion);

            onResume();
            return true;
        }*/

        //optional if statement if excursion list is implemented
        //if implemented, uncomment code in menu_vacation_list
        /*if (item.getItemId() == R.id.seeExcursions) {
            //create intent to navigate to the ExcursionList activity
            Intent intent = new Intent(this, ExcursionList.class);

            //start new activity
            startActivity(intent);

            //indicate item selection has been handled
            return true;
        }*/

        if (item.getItemId() == android.R.id.home) {
            this.finish(); //go back to parent activity
            return true;
        }
        return true;
    }

    @Override
    protected void onResume() { //refresh vacation list view
        super.onResume();

        List<Vacation> allVacations = repository.getmAllVacations();

        RecyclerView recyclerView = findViewById(R.id.vacationRecyclerView);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vacationAdapter.setVacations(allVacations);
        vacationAdapter.notifyDataSetChanged();
        //Toast.makeText(VacationDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

}