package com.todo.codepath.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.todo.codepath.todoapp.R;
import com.todo.codepath.todoapp.adapters.TodoItemsAdapter;
import com.todo.codepath.todoapp.db.TodoItems;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TodoItems> items;
    private TodoItemsAdapter itemsAdapter;
    private ListView lvAddedItems;

    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvAddedItems = (ListView) findViewById(R.id.lvAddedItems);

        //FlowManager.getContext().deleteDatabase(TodoDatabase.NAME+".db");
        this.items = readAllItems();

        this.itemsAdapter = new TodoItemsAdapter(this, 0, items);
        this.lvAddedItems.setAdapter(this.itemsAdapter);
        setupListViewLongClickListener();
        setupListViewClickListener();
    }

    /**
     * TODO move listener to adapter
     */
    private void setupListViewClickListener() {
        lvAddedItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                TodoItems todoItems = items.get(position);
                // TODO, figure out a way to pass complex objects in intent.
                intent.putExtra(EditItemActivity.TEXT, todoItems.getItemName());
                intent.putExtra(EditItemActivity.PRIORITY, todoItems.getPriority());
                intent.putExtra(EditItemActivity.COMPLETE_BY_DATE, todoItems.getCompleteByDate().getTime());
                intent.putExtra(EditItemActivity.POSITION, position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getStringExtra(EditItemActivity.TEXT);
            String priority = data.getStringExtra(EditItemActivity.PRIORITY);
            Date completeByDate = new Date(data.getLongExtra(EditItemActivity.COMPLETE_BY_DATE, new Date().getTime()));
            int position = data.getExtras().getInt(EditItemActivity.POSITION, -1);

            TodoItems item = null;
            if(position == -1) {
                item = new TodoItems().withId(itemsAdapter.getCount());
                itemsAdapter.add(item);
            } else {
                item = items.get(position);
            }
            item = item.withItemName(text).withPriority(priority).withCompleteByDate(completeByDate);
            saveOrUpdate(item);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Long click will delete the todo item.
     *
     * TODO: Move listeners to adapter it self
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
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private List<TodoItems> readAllItems() {
        return SQLite.select().from(TodoItems.class).queryList();
    }

    private void saveOrUpdate(TodoItems item) {
        if(item.exists()) {
            item.update();
        } else {
            item.save();
        }
    }

    private void delete(TodoItems item) {
        item.delete();
    }
}
