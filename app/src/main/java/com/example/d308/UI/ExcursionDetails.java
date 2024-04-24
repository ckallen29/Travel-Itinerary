package com.example.d308.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    Repository repository;

    EditText editExcursionName;
    EditText editExcursionPrice;
    String excursionName;
    double excursionPrice;
    String excursionDate;
    String excursionNote;
    int excursionID;
    int vacationID;
    Button buttonExcursionDate;
    DatePickerDialog.OnDateSetListener eDate;
    final Calendar eCalendar = Calendar.getInstance();
    EditText editExcursionNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        repository=new Repository(getApplication());

        editExcursionName = findViewById(R.id.editExcursionName);
        editExcursionPrice = findViewById(R.id.editExcursionPrice);
        editExcursionNote = findViewById(R.id.editExcursionNote);

        excursionName = getIntent().getStringExtra("name");
        excursionPrice = getIntent().getDoubleExtra("price", 0.0);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1); //retrieve from VacationDetails
        Log.d("VacationID", "VacationID is: " + vacationID);
        excursionDate = getIntent().getStringExtra("date");
        excursionNote = getIntent().getStringExtra("note");

        editExcursionName.setText(excursionName);
        editExcursionPrice.setText(Double.toString(excursionPrice));
        editExcursionNote.setText(excursionNote);

        buttonExcursionDate = findViewById(R.id.buttonExcursionDate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        buttonExcursionDate.setText(excursionDate);

        if (excursionDate == null) {
            eCalendar.setTime(new Date());
            excursionDate = sdf.format(eCalendar.getTime());
            buttonExcursionDate.setText(excursionDate);
        }

        buttonExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = buttonExcursionDate.getText().toString();
                try {
                    eCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this,
                        eDate,
                        eCalendar.get(Calendar.YEAR),
                        eCalendar.get(Calendar.MONTH),
                        eCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        eDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                eCalendar.set(Calendar.YEAR, year);
                eCalendar.set(Calendar.MONTH, month);
                eCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabel();
                //Log.d("updateLabel()", "Value of excursionDate: " + excursionDate);
            }
        };

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
                        excursionDate,
                        editExcursionNote.getText().toString(),
                        vacationID
                );
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(
                        excursionID,
                        editExcursionName.getText().toString(),
                        Double.parseDouble(editExcursionPrice.getText().toString()),
                        excursionDate,
                        editExcursionNote.getText().toString(),
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
                        excursionDate,
                        editExcursionNote.getText().toString(),
                        vacationID
                );
                repository.delete(excursion);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.excursionshare) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, editExcursionNote.getText().toString()+ "EXTRA_TEXT"); //remove extra text
            sentIntent.putExtra(Intent.EXTRA_TITLE, editExcursionNote.getText().toString()+ "EXTRA_TITLE"); //remove extra text
            sentIntent.setType("text/plain");
            Intent shareIntent=Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId()==R.id.excursionnotify) {


            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish(); //go back to parent activity
            return true;
        }
        return true;
    }

    private void updateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        excursionDate = sdf.format(eCalendar.getTime()); //this updates the value stored in the repo object!!
        buttonExcursionDate.setText(sdf.format(eCalendar.getTime()));
    }
}