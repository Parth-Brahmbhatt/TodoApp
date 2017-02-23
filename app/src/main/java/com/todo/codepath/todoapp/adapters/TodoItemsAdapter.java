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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

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
        TextView completeByTV = (TextView) convertView.findViewById(R.id.tvCompleteByDate);

        itemNameTV.setText(item.getItemName());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        completeByTV.setText(dateFormat.format(item.getCompleteByDate()));
        priorityTV.setText(item.getPriority());

        List<String> priorities = Arrays.asList(getContext().getResources().getStringArray(R.array.priorities));
        int priorityIndex = priorities.indexOf(item.getPriority());
        priorityTV.setTextColor(getContext().getResources().getIntArray(R.array.priorityColors)[priorityIndex]);
        return convertView;
    }
}
