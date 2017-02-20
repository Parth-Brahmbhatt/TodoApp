package com.todo.codepath.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.todo.codepath.todoapp.R;


public class EditItemActivity extends AppCompatActivity {

    public final static String TEXT = "text";
    public final static String POSITION = "position";

    private EditText etEditItem;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        this.etEditItem = (EditText) findViewById(R.id.etEditItem);

        final String text = getIntent().getStringExtra(TEXT);
        position = getIntent().getIntExtra(POSITION, -1);

        this.etEditItem.setText(text);
        this.etEditItem.setSelection(text.length());
        this.etEditItem.requestFocus();
    }

    public void onSave(View view) {
        String text = this.etEditItem.getText().toString();
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra(TEXT,text);
        data.putExtra(POSITION, position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}