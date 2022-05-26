package com.artware.todolist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.artware.todolist.Modele.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "TODO_List.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "task";
    private static final String COLUMN_ID = "task_id";
    private static final String COLUMN_IS_FINISHED = "task_finished";
    private static final String COLUMN_TITLE = "task_title";
    private static final String COLUMN_DESCRIPTION = "task_description";
    private static final String COLUMN_CREATED = "task_created";
    private static final String COLUMN_DUE_DATE = "task_dueDate";
    private static final String COLUMN_CATEGORY = "task_category";
    private static final String COLUMN_PRIORITY = "task_priority";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " TEXT PRIMARY KEY," +
                COLUMN_IS_FINISHED + " TEXT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_CREATED + " TEXT," +
                COLUMN_DUE_DATE + " TEXT," +
                COLUMN_CATEGORY + " TEXT," +
                COLUMN_PRIORITY + " NUMERIC);";

        Log.i("Query", query);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, task.getId());
        String finished = "";
        if(task.isFinished()){
            finished = "true";
        }
        else {
            finished = "false";
        }
        cv.put(COLUMN_IS_FINISHED, finished);
        cv.put(COLUMN_TITLE, task.getTitle());
        cv.put(COLUMN_DESCRIPTION, task.getDescription());
        cv.put(COLUMN_CREATED, task.getCreated().toString());
        cv.put(COLUMN_DUE_DATE, task.getDueDate().toString());
        cv.put(COLUMN_CATEGORY, task.getCategory());
        cv.put(COLUMN_PRIORITY, task.getPriority());

        long result = db.insert(TABLE_NAME,null, cv);

        if(result == -1){
            Toast.makeText(context, "Une erreur est survenue. Ajout impossible", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Tâche ajouté", Toast.LENGTH_LONG).show();
        }
        this.close();
    }

    public void deleteTask(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=\"" + id + "\"", null);
        if(result == -1){
            Toast.makeText(context, "Une erreur est survenue. Suppression impossible", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "Tâche effacée avec succès", Toast.LENGTH_LONG).show();
        }
    }

    public List<Task> getAllPointages(){
        String query = "SELECT * FROM " + TABLE_NAME + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        List<Task> tasksList = new ArrayList<>();
        while (true){
            if (!cursor.moveToNext())
                break;
            Task task = new Task();
            task.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));

            String isFinished = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IS_FINISHED));
            if(isFinished.contains("true")){
                task.setFinished(true);
            }
            else{
                task.setFinished(false);
            }
            task.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));

            String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED));

            Date date = new Date(dueDate);
            task.setDueDate(date);
            Log.i("TestID", task.getId());
            tasksList.add(task);
        }
        return tasksList;
    }

    public Task getTaskById(String id){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = \"" + id +"\";";
        Log.i("Request", query);
        SQLiteDatabase db = this.getReadableDatabase();

        Log.i("TestID", id);
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        Task task = new Task();

        while (true){
            if (!cursor.moveToNext())
                break;
            task.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));

            String isFinished = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IS_FINISHED));
            if(isFinished.contains("true")){
                task.setFinished(true);
            }
            else{
                task.setFinished(false);
            }
            task.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
            task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            task.setPriority(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY)));

            String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED));

            try {
                task.setDueDate(new SimpleDateFormat().parse(dueDate));
            } catch (ParseException e) {
                task.setDueDate(new Date());
                e.printStackTrace();
            }
        }

        return task;


    }
}
