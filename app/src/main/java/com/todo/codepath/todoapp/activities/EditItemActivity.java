package com.todo.codepath.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.todo.codepath.todoapp.R;

import java.util.Arrays;
import java.util.List;


public class EditItemActivity extends AppCompatActivity {

    public final static String TEXT = "text";
    public final static String PRIORITY = "priority";
    public final static String POSITION = "position";


    private EditText etEditItem;
    private Spinner spPriority;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        this.etEditItem = (EditText) findViewById(R.id.etEditItem);
        this.spPriority = (Spinner) findViewById(R.id.spPriority);

        final String text = getIntent().getStringExtra(TEXT);
        final String priority = getIntent().getStringExtra(PRIORITY);
        position = getIntent().getIntExtra(POSITION, -1);


        List<String> priorities = Arrays.asList(getResources().getStringArray(R.array.priorities));
        this.spPriority.setSelection(priorities.indexOf(priority));
        this.etEditItem.setText(text);
        this.etEditItem.setSelection(text == null? 0: text.length());
        this.etEditItem.requestFocus();
    }


    public void onSave(View view) {
        String text = this.etEditItem.getText().toString();
        String priority = this.spPriority.getSelectedItem().toString();
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra(TEXT,text);
        data.putExtra(POSITION, position);
        data.putExtra(PRIORITY, priority);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
