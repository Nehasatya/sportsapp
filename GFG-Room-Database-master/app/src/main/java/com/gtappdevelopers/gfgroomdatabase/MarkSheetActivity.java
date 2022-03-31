package com.gtappdevelopers.gfgroomdatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MarkSheetActivity extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> chestnos = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marksheet);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mark Sheet");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.mark_sheet_table);
        tableLayout.setStretchAllColumns(true);

        ViewModal viewModal = new ViewModelProvider(this).get(ViewModal.class);
        LiveData<List<CourseModal>> allCourses = viewModal.getAllCourses();

        allCourses.observe(this, courseModals -> {
            int i=0;
            for (CourseModal courseModal : courseModals) {
                i++;
                names.add(courseModal.getCourseName());
                chestnos.add(courseModal.getCourseDescription());
                TableRow tableRow = new TableRow(MarkSheetActivity.this);
                TextView chestno = new TextView(MarkSheetActivity.this);
                chestno.setText(courseModal.getCourseDescription());
                tableRow.addView(chestno);

                TextInputEditText m1 = new TextInputEditText(MarkSheetActivity.this);
                m1.setId(i*10+1);
                tableRow.addView(m1);

                TextInputEditText m2 = new TextInputEditText(MarkSheetActivity.this);
                tableRow.addView(m2);
                m2.setId(i*10+2);

                TextInputEditText m3 = new TextInputEditText(MarkSheetActivity.this);
                tableRow.addView(m3);
                m3.setId(i*10+3);

                tableLayout.addView(tableRow);
            }
        });

        Button button = (Button) findViewById(R.id.mark_submit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results();
            }
        });
        
    }

    public void results() {
        //get input from all textinputedittexts
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mark_sheet_table);
        ArrayList<String[]> marks = new ArrayList<>();
        for (int i=1; i<tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView chestno = (TextView) tableRow.getChildAt(0);
            TextInputEditText m1 = (TextInputEditText) tableRow.getChildAt(1);
            TextInputEditText m2 = (TextInputEditText) tableRow.getChildAt(2);
            TextInputEditText m3 = (TextInputEditText) tableRow.getChildAt(3);

            String[] mark = new String[5];

            mark[0] = chestnos.get(i-1);
            mark[1] = names.get(i-1);
            mark[2] = m1.getText().toString();
            mark[3] = m2.getText().toString();
            mark[4] = m3.getText().toString();
            marks.add(mark);
        }

        Intent intent = new Intent(MarkSheetActivity.this, ResultActivity.class);
        intent.putExtra("marks", marks);
        startActivity(intent);
    }

    //shared preferences to store marks input by user
    @Override
    protected void onResume() {
        super.onResume();
        //get marks input by user
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mark_sheet_table);
        for (int i=1; i<tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextInputEditText m1 = (TextInputEditText) tableRow.getChildAt(1);
            TextInputEditText m2 = (TextInputEditText) tableRow.getChildAt(2);
            TextInputEditText m3 = (TextInputEditText) tableRow.getChildAt(3);

            SharedPreferences sharedPreferences = getSharedPreferences("marks", MODE_PRIVATE);

            //set marks input by user
            m1.setText(sharedPreferences.getString(names.get(i-1)+"_m1", ""));
            m2.setText(sharedPreferences.getString(names.get(i-1)+"_m2", ""));
            m3.setText(sharedPreferences.getString(names.get(i-1)+"_m3", ""));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //get input from all textinputedittexts
        TableLayout tableLayout = (TableLayout) findViewById(R.id.mark_sheet_table);
        for (int i=1; i<tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextInputEditText m1 = (TextInputEditText) tableRow.getChildAt(1);
            TextInputEditText m2 = (TextInputEditText) tableRow.getChildAt(2);
            TextInputEditText m3 = (TextInputEditText) tableRow.getChildAt(3);

            SharedPreferences sharedPreferences = getSharedPreferences("marks", MODE_PRIVATE);

            //store marks input by user
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(names.get(i-1)+"_m1", m1.getText().toString());
            editor.putString(names.get(i-1)+"_m2", m2.getText().toString());
            editor.putString(names.get(i-1)+"_m3", m3.getText().toString());
            editor.apply();
        }
    }
}
