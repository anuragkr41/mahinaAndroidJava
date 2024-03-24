package com.esi.mahina.activities.doctorActivities;

import android.os.Bundle;

import com.esi.mahina.calculations.AppointmentsHelper;
import com.esi.mahina.calculations.DatesHelper;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.esi.mahina.R;
import com.esi.mahina.databinding.ActivityDoctorBinding;

import java.time.LocalDate;

public class DoctorActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private LocalDate lmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        this.datePicker = findViewById(R.id.datePicker);

        datePicker.init(datePicker.getYear(),
                datePicker.getMonth(), datePicker.getDayOfMonth(),
                (view, year, monthOfYear, dayOfMonth)->{
                    LocalDate selectedDate = DatesHelper.captureLocalDateFromDatePicker(datePicker);
                    setPogAndEDD(selectedDate);
                    if(this.lmp!=null){
                        String lmpString = lmp.format(DatesHelper.formatter);
                        TextView tvSaveLmpInfo = findViewById(R.id.LMPSelectedDate);
                        tvSaveLmpInfo.setText("LMP Selected: "+ lmpString);

                       tvSaveLmpInfo = findViewById(R.id.LMPSelectedDateAppointment);
                       tvSaveLmpInfo.setText("LMP Selected: "+ lmpString);
//                        It is here that you will set the USG and VISIT Data...
//                        Do this using private Functions
                        setUsgDates(this.lmp);
                        setVisitDates(this.lmp);


                    }
                });


    }

    private void setPogAndEDD(LocalDate date) {
        this.lmp=date;

        String pog= DatesHelper.getPeriodOfGestation.apply(date);
        TextView textViewPog = findViewById(R.id.twPog);
        textViewPog.setText(pog);

        String edd= DatesHelper.getExpectedDateOfDelivery.apply(date);
        TextView textViewEdd = findViewById(R.id.twEdd);
        textViewEdd.setText(edd);
    }

    private void setUsgDates(LocalDate date){
        date = this.lmp;
        String usg1DateRangeString = DatesHelper.getUSG1DateRange.apply(date);
        TextView tvUsg1DateRange = findViewById(R.id.usg1DatesFromLMP);
        tvUsg1DateRange.setText(usg1DateRangeString);

        String usg2DateRangeString = DatesHelper.getUSG2DateRange.apply(date);
        TextView tvUsg2DateRange = findViewById(R.id.usg2DatesFromLMP);
        tvUsg2DateRange.setText(usg2DateRangeString);

        String usg3DateRangeString = DatesHelper.getUSG3DateRange.apply(date);
        TextView tvUsg3DateRange = findViewById(R.id.usg3DatesFromLMP);
        tvUsg3DateRange.setText(usg3DateRangeString);

        String usg4DateRangeString = DatesHelper.getUSG4DateRange.apply(date);
        TextView tvUsg4DateRange = findViewById(R.id.usg4DatesFromLMP);
        tvUsg4DateRange.setText(usg4DateRangeString);
    }

    private void setVisitDates(LocalDate date){
        date = this.lmp;

        String visit1DateRangeString = AppointmentsHelper.getVisit1DateRange.apply(date);
        TextView tvVisit1DateRange = findViewById(R.id.visit1DatesFromLMP);
        tvVisit1DateRange.setText(visit1DateRangeString);

        String visit2DateRangeString = AppointmentsHelper.getVisit2To8DateRange.apply(date, 20);
        TextView tvVisit2DateRange = findViewById(R.id.visit2DatesFromLMP);
        tvVisit2DateRange.setText(visit2DateRangeString);



        String visit3DateRangeString = AppointmentsHelper.getVisit2To8DateRange.apply(date, 26);
        TextView tvVisit3DateRange = findViewById(R.id.visit3DatesFromLMP);
        tvVisit3DateRange.setText(visit3DateRangeString);

        String visit4DateRangeString = AppointmentsHelper.getVisit2To8DateRange.apply(date, 30);
        TextView tvVisit4DateRange = findViewById(R.id.visit4DatesFromLMP);
        tvVisit4DateRange.setText(visit4DateRangeString);



        String visit5DateRangeString = AppointmentsHelper.getVisit2To8DateRange.apply(date, 34);
        TextView tvVisit5DateRange = findViewById(R.id.visit5DatesFromLMP);
        tvVisit5DateRange.setText(visit5DateRangeString);

        String visit6DateRangeString = AppointmentsHelper.getVisit2To8DateRange.apply(date, 36);
        TextView tvVisit6DateRange = findViewById(R.id.visit6DatesFromLMP);
        tvVisit6DateRange.setText(visit6DateRangeString);



        String visit7DateRangeString = AppointmentsHelper.getVisit2To8DateRange.apply(date, 38);
        TextView tvVisit7DateRange = findViewById(R.id.visit7DatesFromLMP);
        tvVisit7DateRange.setText(visit7DateRangeString);


        String visit8DateRangeString = AppointmentsHelper.getVisit2To8DateRange.apply(date, 40);
        TextView tvVisit8DateRange = findViewById(R.id.visit8DatesFromLMP);
        tvVisit8DateRange.setText(visit8DateRangeString);

    }
}