package com.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TodoList {
    
    String listName;
    private ArrayList<Task> tasks = new ArrayList<>();

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

    public ArrayList<String> getTasksToStrings(){
        ArrayList<String> desTasks = new ArrayList<>();

        for (Task task : this.tasks){
            desTasks.add(task.taskDispName);
        }
        return desTasks;

    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    //used to see if the button's text needs to be changed
    public Boolean getTaskStatus(String taskDispName){
        for (Task curTask : tasks){
            if (curTask.taskDispName == taskDispName){
                return curTask.taskStatus;
            }
        }
        return false;
    }

    public JSONObject getJSON(){
        JSONObject listJSON = new JSONObject();
        JSONArray taskListJSON = new JSONArray();
        for(Task curTask : tasks){
            JSONObject taskJSON = new JSONObject();
            taskJSON.put(curTask.taskName, Boolean.toString(curTask.taskStatus));
            taskListJSON.add(taskJSON);
        }
        listJSON.put("listName",listName);
        listJSON.put("tasks",taskListJSON);
        return listJSON;
    }

    public void add(Task newTask){
        tasks.add(newTask);
    }
    public void remove(String taskName){
        Iterator<Task> iter = tasks.iterator();
        while(iter.hasNext()){
            Task curTask = iter.next();
            if (curTask.taskDispName == taskName){
                iter.remove();
            }
        }
    }

    public Task updateTask(String taskName){
        for (Task curTask : tasks){
            if (curTask.taskDispName == taskName){
                curTask.updateTask();
                return curTask;
            }
        }
        return null;
    }

}
