package com.artware.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.artware.todolist.Modele.Task;
import com.artware.todolist.Services.TaskService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView date;
    RecyclerView taskList;
    TaskService taskService;
    MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.dateNow);
        taskList = findViewById(R.id.taskList);
        topAppBar = findViewById(R.id.topAppBar);

        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("Menu", item.getTitle().toString());

                Intent i = null;
                if(item.getTitle().toString().contains("Categorie")){
                    i = new Intent(getApplicationContext(), AddTaskActivity.class);
                }
                else if(item.getTitle().toString().contains("Créer")){
                    i = new Intent(getApplicationContext(), AddTaskActivity.class);
                }
                startActivity(i);
                finish();
                return false;
            }
        });

        List<Task> tasks = new ArrayList<>();

        taskService = new TaskService(this);
        if(taskService.getAllTasks().size() < 1){
            this.initDatabase();
        }
        tasks = taskService.getAllTasks();

        taskList.setHasFixedSize(true);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new TaskListAdapter(tasks, this);
        taskList.setAdapter(adapter);
    }

    private void initDatabase(){
        Task task1 = new Task(true, "Test1", "Desc1", new Date(), "Cat1", 0);
        Task task2 = new Task(false, "Test2", "Desc2", new Date(), "Cat1", 0);
        Task task3 = new Task(true, "Test3", "Desc3", new Date(), "Cat1", 0);

        this.taskService.addTask(task1);
        this.taskService.addTask(task2);
        this.taskService.addTask(task3);
    }
}