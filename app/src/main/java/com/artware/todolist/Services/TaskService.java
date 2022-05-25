package com.artware.todolist.Services;

import android.content.Context;

import com.artware.todolist.Modele.Task;
import com.artware.todolist.Utils.SQLiteHelper;

import java.util.List;

public class TaskService {
    SQLiteHelper db;

    public TaskService(Context context) {
        this.db = new SQLiteHelper(context);
    }

    public List<Task> getAllTasks(){
        return db.getAllPointages();
    }

    public Task getTaskById(String id){
        return db.getTaskById(id);
    }

    public void addTask(Task task){
        db.addTask(task);
    }

    public void deleteTask(String id){
        db.deleteTask(id);
    }
}
