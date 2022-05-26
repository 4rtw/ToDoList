package com.artware.todolist.Modele;

import java.util.Date;
import java.util.UUID;

public class Task {

    private String id;
    private boolean isFinished;
    private String title;
    private String description;
    private Date created;
    private Date dueDate;
    private String category;
    private int priority;

    public Task() {
        this.id = UUID.randomUUID().toString();
        this.created = new Date();
    }

    public Task(boolean isFinished, String title, String description, Date dueDate, String category, int priority) {
        this.id = UUID.randomUUID().toString();
        this.isFinished = isFinished;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.created = new Date();
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
