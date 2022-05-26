package com.artware.todolist.Controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.artware.todolist.Modele.Task;
import com.artware.todolist.R;
import com.artware.todolist.Services.TaskService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity {
    TextInputLayout title, category, description, priority;
    Button add, cancel, delete, update;
    TaskService taskService;
    Task presentTask, newTask;
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
            Objects.requireNonNull(title.getEditText()).setText(presentTask.getTitle());
            Objects.requireNonNull(category.getEditText()).setText(presentTask.getCategory());
            Objects.requireNonNull(description.getEditText()).setText(presentTask.getDescription());
            Objects.requireNonNull(priority.getEditText()).setText(String.valueOf(presentTask.getPriority()));

            editLinearLayout.setVisibility(View.VISIBLE);
            createLinearLayout.setVisibility(View.GONE);
        }

        newTask = new Task();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    newTask.setTitle(Objects.requireNonNull(title.getEditText()).getText().toString());
                    newTask.setCategory(Objects.requireNonNull(category.getEditText()).getText().toString());
                    newTask.setDescription(Objects.requireNonNull(description.getEditText()).getText().toString());
                    int prio = Integer.parseInt(Objects.requireNonNull(priority.getEditText()).getText().toString());
                    newTask.setPriority(prio);
                    newTask.setDueDate(new Date());
                    newTask.setFinished(false);
                    taskService.addTask(newTask);
                    finish();
                }
                catch (Exception e){
                    Log.i("IntError", e.getMessage());
                }
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
    public void finish() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null data.
        super.finish();
    }
}