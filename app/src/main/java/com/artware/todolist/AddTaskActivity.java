package com.artware.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.artware.todolist.Modele.Task;
import com.artware.todolist.Services.TaskService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    TextInputLayout title, category, description, priority;
    Button add, cancel, delete, update;
    TaskService taskService;
    Task presentTask;
    LinearLayout editLinearLayout, createLinearLayout;
    boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskService = new TaskService(getApplicationContext());

        title = findViewById(R.id.textFieldTaskTitle);
        category = findViewById(R.id.textFieldTaskCategory);
        description = findViewById(R.id.textFieldTaskDescription);
        priority = findViewById(R.id.textFieldTaskPriority);
        editLinearLayout = findViewById(R.id.editLinearLaoyut);
        createLinearLayout = findViewById(R.id.createLinearLaoyut);
        delete = findViewById(R.id.buttonDelete);
        update = findViewById(R.id.buttonEdit);

        add = findViewById(R.id.buttonAdd);
        cancel = findViewById(R.id.buttonCancel);

        String id = "";
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id = extras.getString("id");
            isUpdate = true;
        }

        if(isUpdate){
            presentTask = taskService.getTaskById(id);
            title.getEditText().setText(presentTask.getTitle());
            category.getEditText().setText(presentTask.getCategory());
            description.getEditText().setText(presentTask.getDescription());
            priority.getEditText().setText(String.valueOf(presentTask.getPriority()));

            editLinearLayout.setVisibility(View.VISIBLE);
            createLinearLayout.setVisibility(View.INVISIBLE);
        }

        Task task = new Task();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setTitle(title.getEditText().getText().toString());
                task.setCategory(category.getEditText().getText().toString());
                task.setDescription(description.getEditText().getText().toString());
                int prio = Integer.parseInt(priority.getEditText().getText().toString());
                task.setPriority(prio);
                task.setDueDate(new Date());
                task.setFinished(false);
                taskService.addTask(task);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskService.deleteTask(presentTask.getId());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}