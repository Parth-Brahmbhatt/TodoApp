package com.todo.codepath.todoapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.todo.codepath.todoapp.R;
import com.todo.codepath.todoapp.db.TodoItems;

import java.util.List;

/**
 * Created by parth on 2/21/17.
 */

public class TodoItemsAdapter extends ArrayAdapter<TodoItems> {

    public TodoItemsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItems item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_items, parent, false);
        }

        TextView itemNameTV = (TextView) convertView.findViewById(R.id.tvName);
        TextView priorityTV = (TextView) convertView.findViewById(R.id.tvPriority);

        itemNameTV.setText(item.getItemName());
        priorityTV.setText(item.getPriority());
        return convertView;
    }
}
