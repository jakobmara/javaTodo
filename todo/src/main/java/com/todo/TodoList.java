package com.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TodoList {
    
    String listName;
    public ArrayList<Task> tasks = new ArrayList<>();

    public TodoList(JSONObject list){
        //Get employee object within list
        this.listName = (String) list.get("listName");
        //Get employee first name
        JSONArray tasks = (JSONArray) list.get("tasks");    
        for(int i = 0; i < tasks.size(); i++){
            JSONObject task = (JSONObject) tasks.get(i);
            String taskName = (String) task.keySet().toArray()[0];
            Boolean taskStatus = Boolean.parseBoolean((String) task.values().toArray()[0]);
            Task listTask = new Task(taskName,taskStatus);
            this.tasks.add(listTask);
        }
        
    }
    
    public TodoList(String newListName, ArrayList<Task> newTasks) {
        this.listName = newListName;
        this.tasks = newTasks;
    }

    public String toString(){
        return String.format("ListName: %s tasks: %s",this.listName,this.tasks);
    }

    public ArrayList<String> getTasks(String listName){
        ArrayList<String> desTasks = new ArrayList<>();

        for (Task task : this.tasks){
            desTasks.add(task.taskName);
        }
        return desTasks;

    }

}
