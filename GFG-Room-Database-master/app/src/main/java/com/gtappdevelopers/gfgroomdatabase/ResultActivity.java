package com.gtappdevelopers.gfgroomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    ArrayList<String[]> arrayList;
    ArrayList<String[]> total_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Results");

        TableLayout tableLayout = (TableLayout) findViewById(R.id.result_table);
        tableLayout.setStretchAllColumns(true);

        Intent intent = getIntent();
        arrayList = (ArrayList<String[]>) intent.getSerializableExtra("marks");

        total_marks = getMaxMarks();
        ArrayList<String[]> sorted_marks = getSortedMarks();

        for (int i=0; i<3; i++){
            TableRow tableRow = new TableRow(ResultActivity.this);
            TextView name = new TextView(ResultActivity.this);
            TextView chestno = new TextView(ResultActivity.this);
            TextView marks = new TextView(ResultActivity.this);
            TextView place = new TextView(ResultActivity.this);

            chestno.setText(sorted_marks.get(i)[0]);
            name.setText(sorted_marks.get(i)[1]);
            marks.setText(sorted_marks.get(i)[2]);
            place.setText(Integer.toString(i+1));

            tableRow.addView(name);
            tableRow.addView(chestno);
            tableRow.addView(marks);
            tableRow.addView(place);

            tableLayout.addView(tableRow);
        }
    }

    public ArrayList<String[]> getMaxMarks(){
        ArrayList<String[]> max_marks = new ArrayList<>();
        
        for(int i=0; i<arrayList.size(); i++){
            String max="0";
            String[] temp = arrayList.get(i);
            for(int j=2; j<5; j++) {
                if(temp[j].equals("x") || temp[j].equals("X")){
                    continue;
                }
                if (Double.parseDouble(temp[j]) > Double.parseDouble(max)) {
                    max = temp[j];
                }
            }
            max_marks.add(new String[]{temp[0], temp[1], max});
        }
        return max_marks;
    }

    public ArrayList<String[]> getSortedMarks(){
        ArrayList<String[]> sorted_marks = new ArrayList<>();

        for(int i=0; i<total_marks.size(); i++){
            for(int j=0; j<total_marks.size()-1; j++){
                if(Double.parseDouble(total_marks.get(j)[2]) < Double.parseDouble(total_marks.get(j+1)[2])){
                    Collections.swap(total_marks, j, j+1);
                }
            }
        }

        for(int i=0; i<total_marks.size(); i++){
            sorted_marks.add(total_marks.get(i));
        }

        return sorted_marks;
    }
}
