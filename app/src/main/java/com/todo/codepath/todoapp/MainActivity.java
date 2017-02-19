package com.todo.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvAddedItems;
    private EditText etItemAdd;
    private File todoFile;

    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvAddedItems = (ListView) findViewById(R.id.lvAddedItems);
        this.etItemAdd = (EditText) findViewById(R.id.etItemAdd);

        this.todoFile = new File(getFilesDir(), "todo.txt");
        this.items = new ArrayList<>();
        readItems();

        this.itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        this.lvAddedItems.setAdapter(this.itemsAdapter);
        setupListViewLongClickListener();
        setupListViewClickListener();
    }

    private void setupListViewClickListener() {
        lvAddedItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra(EditItemActivity.TEXT, items.get(position));
                intent.putExtra(EditItemActivity.POSITION, position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String text = data.getExtras().getString(EditItemActivity.TEXT);
            int position = data.getExtras().getInt(EditItemActivity.POSITION);
            items.remove(position);
            items.add(position, text);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void setupListViewLongClickListener() {
        lvAddedItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }


    public void addItem(View view) {
        String item = etItemAdd.getText().toString();
        this.itemsAdapter.add(item);
        writeItems();
        etItemAdd.setText("");
    }

    private void readItems() {
        try {
            this.items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(todoFile, items);
            System.out.println(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
