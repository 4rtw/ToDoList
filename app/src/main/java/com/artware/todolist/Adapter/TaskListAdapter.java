package com.artware.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artware.todolist.Controler.AddTaskActivity;
import com.artware.todolist.Modele.Task;
import com.artware.todolist.R;
import com.artware.todolist.Services.TaskService;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> taskList;
    Context context;
    TaskService taskService;

    public TaskListAdapter(List<Task> tasks, Context context){
        this.taskList = tasks;
        this.context = context;
        taskService = new TaskService(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);

        String title = task.getTitle();
        String titleToShow = title.substring(0, Math.min(title.length(), 15));
        if(title.length()>15){
            titleToShow += "...";
        }

        holder.title.setText(titleToShow);
        holder.title.setChecked(task.isFinished());

        SimpleDateFormat dateFormat = new SimpleDateFormat("'Créée le' EEEE, dd MMMM yyyy 'à' hh:mm", Locale.FRANCE);
        holder.date.setText(dateFormat.format(task.getDueDate()));

        String description = task.getDescription();
        String descriptionToShow = description.substring(0, Math.min(description.length(), 60));
        if(description.length()>60){
            descriptionToShow += "...";
        }
        holder.desc.setText(descriptionToShow);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("text", "text");
                Intent i = new Intent(context, AddTaskActivity.class);
                i.putExtra("id", task.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public void removeAt(int position){
        taskService.deleteTask(taskList.get(position).getId());
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView desc, date;
        MaterialCheckBox title;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.textViewDescriptionItem);
            date = itemView.findViewById(R.id.textViewDateItem);
            title = itemView.findViewById(R.id.checkBoxTitleItem);
            card = itemView.findViewById(R.id.cardItem);
        }
    }
}
