package com.todo.codepath.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.todo.codepath.todoapp.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditItemActivity extends AppCompatActivity {

    public final static String TEXT = "text";
    public final static String PRIORITY = "priority";
    public final static String POSITION = "position";
    public final static String COMPLETE_BY_DATE = "completeByDate";


    private EditText etEditItem;
    private Spinner spPriority;
    private DatePicker dpItemDate;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        this.etEditItem = (EditText) findViewById(R.id.etEditItem);
        this.spPriority = (Spinner) findViewById(R.id.spPriority);
        this.dpItemDate = (DatePicker) findViewById(R.id.dpItemDate);

        final String text = getIntent().getStringExtra(TEXT);
        final String priority = getIntent().getStringExtra(PRIORITY);
        final Date completeByDate = new Date(getIntent().getLongExtra(COMPLETE_BY_DATE, new Date().getTime()));
        position = getIntent().getIntExtra(POSITION, -1);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(completeByDate);
        List<String> priorities = Arrays.asList(getResources().getStringArray(R.array.priorities));
        this.spPriority.setSelection(priorities.indexOf(priority));
        this.dpItemDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        this.etEditItem.setText(text);
        this.etEditItem.setSelection(text == null? 0: text.length());
        this.etEditItem.requestFocus();
    }


    public void onSave(View view) {
        String text = this.etEditItem.getText().toString();
        String priority = this.spPriority.getSelectedItem().toString();
        int year = this.dpItemDate.getYear();
        int month = this.dpItemDate.getMonth();
        int dayOfMonth = this.dpItemDate.getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra(TEXT,text);
        data.putExtra(POSITION, position);
        data.putExtra(PRIORITY, priority);
        data.putExtra(COMPLETE_BY_DATE, calendar.getTime().getTime());
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
