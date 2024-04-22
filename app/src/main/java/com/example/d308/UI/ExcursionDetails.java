package com.example.d308.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308.R;
import com.example.d308.database.Repository;
import com.example.d308.entities.Excursion;
import com.example.d308.entities.Vacation;

public class ExcursionDetails extends AppCompatActivity {

    Repository repository;

    EditText editExcursionName;
    EditText editExcursionPrice;
    //EditText editExcursionNote;
    //TextView excursionDate;
    String excursionName;
    double excursionPrice;
    int excursionID;
    int vacationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        repository=new Repository(getApplication());

        editExcursionName = findViewById(R.id.editExcursionName);
        editExcursionPrice = findViewById(R.id.editExcursionPrice);
        excursionName = getIntent().getStringExtra("name");
        excursionPrice = getIntent().getDoubleExtra("price", 0.0);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1); //retrieve from VacationDetails
        editExcursionName.setText(excursionName);
        editExcursionPrice.setText(Double.toString(excursionPrice));
        //editExcursionNote=findViewById(R.id.excursionNote);
        //excursionDate=findViewById(R.id.excursionDate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.excursionsave) {
            Excursion excursion;
            if (excursionID == -1) {
                if (repository.getmAllExcursions().size() == 0) {
                    excursionID = 1;
                } else {
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                }
                excursion = new Excursion(
                        excursionID,
                        editExcursionName.getText().toString(),
                        Double.parseDouble(editExcursionPrice.getText().toString()),
                        vacationID
                );
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(
                        excursionID,
                        editExcursionName.getText().toString(),
                        Double.parseDouble(editExcursionPrice.getText().toString()),
                        vacationID
                );
                repository.update(excursion);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.excursiondelete) {
            Excursion excursion;
            if (excursionID == -1) {
                this.finish();
            } else {
                excursion = new Excursion(
                        excursionID,
                        editExcursionName.getText().toString(),
                        Double.parseDouble(editExcursionPrice.getText().toString()),
                        vacationID
                );
                repository.delete(excursion);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.excursionshare) {
            /*Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString()+ "EXTRA_TEXT");
            sentIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString()+ "EXTRA_TITLE");
            sentIntent.setType("text/plain");
            Intent shareIntent=Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);*/
            return true;
        }
        if (item.getItemId()==R.id.excursionnotify) {


            return true;
        }
        return true;
    }
}