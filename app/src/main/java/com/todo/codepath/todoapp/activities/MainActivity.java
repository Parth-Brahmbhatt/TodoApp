package com.todo.codepath.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.todo.codepath.todoapp.R;
import com.todo.codepath.todoapp.db.TodoItems;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TodoItems> items;
    private ArrayAdapter<TodoItems> itemsAdapter;
    private ListView lvAddedItems;
    private EditText etItemAdd;

    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvAddedItems = (ListView) findViewById(R.id.lvAddedItems);
        this.etItemAdd = (EditText) findViewById(R.id.etItemAdd);

        this.items = readAllItems();

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
                TodoItems todoItems = items.get(position);
                intent.putExtra(EditItemActivity.TEXT, todoItems.getItemName());
                intent.putExtra(EditItemActivity.POSITION, position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getExtras().getString(EditItemActivity.TEXT);
            int position = data.getExtras().getInt(EditItemActivity.POSITION);
            TodoItems item = items.get(position);
            item.withItemName(text);
            itemsAdapter.notifyDataSetChanged();
            update(item);
        }
    }

    /**
     * Long click will delete the todo item.
     */
    private void setupListViewLongClickListener() {
        lvAddedItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delete(items.get(position));
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    public void addItem(View view) {
        String itemText = etItemAdd.getText().toString();
        TodoItems item = new TodoItems().withId(itemsAdapter.getCount()).withItemName(itemText).withCreationDate(new Date());
        saveOrUpdate(item);
        this.itemsAdapter.add(item);
        etItemAdd.setText("");
    }

    private List<TodoItems> readAllItems() {
        System.out.println(SQLite.select().from(TodoItems.class).queryList());
        return SQLite.select().from(TodoItems.class).queryList();
    }

    private void update(TodoItems item) {
        item.update();
        readAllItems();
    }
    private void saveOrUpdate(TodoItems item) {
        item.save();
        readAllItems();
    }

    private void delete(TodoItems item) {
        item.delete();
        readAllItems();
    }
}
