package com.example.d308.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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
    String startDate;
    String endDate;
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
        vacationID = getIntent().getIntExtra("vacationID", -1);
        //Log.d("Vacation ID", "Vacation ID is " + vacationID);
        //startDate = getIntent().getStringExtra("start"); //passes null, WHAT THE FUCK
        //endDate = getIntent().getStringExtra("end");
        excursionDate = getIntent().getStringExtra("date");
        excursionNote = getIntent().getStringExtra("note");

        editExcursionName.setText(excursionName);
        editExcursionPrice.setText(Double.toString(excursionPrice));
        editExcursionNote.setText(excursionNote);

        Vacation vacation = repository.getVacationById(vacationID);
        if (vacation != null) {
            startDate = vacation.getVacationStartDate();
            endDate = vacation.getVacationEndDate();
        }
        //Log.d("Start Date", "Start date is " + startDate);
        //Log.d("End Date", "End date is " + endDate);

        buttonExcursionDate = findViewById(R.id.buttonExcursionDate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        buttonExcursionDate.setText(excursionDate);

        if (excursionDate == null) {
            Date defaultDate;
            try {
                defaultDate = sdf.parse(startDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            eCalendar.setTime(defaultDate);
            excursionDate = sdf.format(eCalendar.getTime());
            buttonExcursionDate.setText(excursionDate);
        }

        buttonExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Date date;
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
                Date startDateConv;
                try {
                    startDateConv = sdf.parse(startDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Date endDateConv;
                try {
                    endDateConv = sdf.parse(endDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                //String myFormat = "MM/dd/yy";
                //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                //compare to vacation dates
                if (eCalendar.getTime().before(startDateConv) || eCalendar.getTime().after(endDateConv)) {
                    Toast.makeText(ExcursionDetails.this, "Date cannot be outside vacation dates", Toast.LENGTH_SHORT).show();
                } else {
                    updateLabel();
                }

                //updateLabel();
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
            sentIntent.putExtra(Intent.EXTRA_TEXT, editExcursionNote.getText().toString() + "EXTRA_TEXT"); //remove extra text
            sentIntent.putExtra(Intent.EXTRA_TITLE, editExcursionNote.getText().toString() + "EXTRA_TITLE"); //remove extra text
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId()==R.id.excursionnotify) {
            String dateFromScreen = excursionDate;
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;

            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger = myDate.getTime();

            //broadcast receiver sends the notification
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class); //intent to send
            intent.setAction("exc action");
            intent.putExtra("key", excursionName + " is today!"); //notification message
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE); //increments from 0 for the whole app every alert
            //alarm service wakes up the app to send the notification
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

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