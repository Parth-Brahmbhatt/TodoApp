package com.todo.codepath.todoapp.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = TodoDatabase.class)
public class TodoItems extends BaseModel {
    @Column
    @PrimaryKey
    int id;

    @Column
    String itemName;

    @Column
    String priority;

    @Column
    Date completeByDate;

    public TodoItems withId(int identity) {
        this.id = identity;
        return this;
    }

    public TodoItems withItemName(String name) {
        this.itemName = name;
        return this;
    }

    public TodoItems withPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public TodoItems withCompleteByDate(Date completeByDate) {
        this.completeByDate = completeByDate;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPriority() {
        return priority;
    }

    public Date getCompleteByDate() {
        return this.completeByDate;
    }

    @Override
    public String toString() {
        return "TodoItems{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", priority='" + priority + '\'' +
                ", completeByDate=" + completeByDate +
                '}';
    }
}
