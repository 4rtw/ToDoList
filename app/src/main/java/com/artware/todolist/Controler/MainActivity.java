package com.artware.todolist.Controler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.artware.todolist.Adapter.TaskListAdapter;
import com.artware.todolist.Modele.Task;
import com.artware.todolist.R;
import com.artware.todolist.Services.TaskService;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView date;
    RecyclerView taskList;
    TaskService taskService;
    MaterialToolbar topAppBar;
    TaskListAdapter adapter;
    List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.dateNow);
        taskList = findViewById(R.id.taskList);
        topAppBar = findViewById(R.id.topAppBar);

        taskService = new TaskService(getApplicationContext());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.FRANCE);
        String pageTitle = "Tâches du " + dateFormat.format(new Date());
        date.setText(pageTitle);

        topAppBar.setOnMenuItemClickListener(item -> {
            Log.i("Menu", item.getTitle().toString());

            Intent i = null;
            if(item.getTitle().toString().contains("Categorie")){
                i = new Intent(getApplicationContext(), CategoryActivity.class);
            }
            else if(item.getTitle().toString().contains("Créer")){
                i = new Intent(getApplicationContext(), AddTaskActivity.class);
            }
            startActivityIntent.launch(i);
            return false;
        });

        getData();
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> getData());

    private void getData(){
        tasks = taskService.getAllTasks();
        taskList.setHasFixedSize(true);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskListAdapter(tasks, this);
        setUpItemTouchHelper(ItemTouchHelper.LEFT);
        setUpItemTouchHelper(ItemTouchHelper.RIGHT);
        taskList.setAdapter(adapter);
    }

    private void setUpItemTouchHelper(int direction) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, direction) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                adapter.removeAt(swipedPosition);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(taskList);
    }
}