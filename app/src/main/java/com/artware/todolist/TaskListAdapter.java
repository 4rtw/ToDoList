package com.artware.todolist;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artware.todolist.Modele.Task;
import com.artware.todolist.Services.TaskService;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

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

        holder.title.setText(task.getTitle());
        holder.title.setChecked(task.isFinished());
        holder.date.setText(task.getDueDate().toString());
        holder.desc.setText(task.getDescription());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("text", "text");
                Intent i = new Intent(context, AddTaskActivity.class);
                i.putExtra("id", task.getId());
                context.startActivity(i);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskService.deleteTask(task.getId());
                taskList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView desc, date;
        MaterialCheckBox title;
        MaterialCardView card;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.textViewDescriptionItem);
            date = itemView.findViewById(R.id.textViewDateItem);
            title = itemView.findViewById(R.id.checkBoxTitleItem);
            card = itemView.findViewById(R.id.cardItem);
            delete = itemView.findViewById(R.id.buttonDeleteOnItem);
        }
    }
}
