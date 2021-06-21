package com.todo;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class userDataHandler {
    private static Map<String, TodoList> todoLists = new HashMap<>();
    private static JSONArray todoListsJson = new JSONArray(); 
    public static void main(String[] args) 
    {
        
    }
    
    public void loadUserLists(){
    
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        
        
        try (FileReader reader = new FileReader("todo.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
    
            JSONArray todoList = (JSONArray) obj;
                       
            
            for(int i = 0; i < todoList.size(); i++){
                JSONObject list = (JSONObject) todoList.get(i);
                TodoList newList = new TodoList(list);
                addList(newList);
                todoLists.put(newList.listName,newList);
            }
            
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getListNames(){
        ArrayList<String> listNames = new ArrayList<>();

        for(String listName: todoLists.keySet()){
            listNames.add(listName);
        }

        return listNames;
    }

    public ArrayList<String> getTasks(String listName){
        ArrayList<String> tasks = new ArrayList<>();

        TodoList todoList = todoLists.get(listName);

        tasks = todoList.getTasksToStrings();

        return tasks;

    }
    
    public Boolean getTaskStatus(String listName, String taskName){
        TodoList curTodo = todoLists.get(listName);
        return curTodo.getTaskStatus(taskName);

    }

    //This function adds a list to the JSONarray object (which gets written to file)
    //modify this function so it uses getJSON
    //modify this function so it uses update
    public void addList(TodoList newList){

        JSONObject listDetails = new JSONObject();

        JSONArray listTasks = new JSONArray();

        for(Task tasks : newList.getTasks()){
            JSONObject taskJson = new JSONObject();
            taskJson.put(tasks.taskName,Boolean.toString(tasks.taskStatus));
            listTasks.add(taskJson);
        }
        listDetails.put("listName",newList.listName);
        listDetails.put("tasks",listTasks);
        todoListsJson.add(listDetails);


    }

    //this function creates a new list puts it in hashmap and jsonArray and updates list file
    public void createList(TodoList newList){
        todoLists.put(newList.listName,newList);

        addList(newList);
        try (FileWriter file = new FileWriter("todo.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(todoListsJson.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeList(String listName){
        todoLists.remove(listName);
        updateJson();

    }

    public void removeTask(String listName, String taskName){
        todoLists.get(listName).remove(taskName);
        updateJson();

    }

    public void addTask(String taskName, String listName){
        Task newTask = new Task(taskName, false);
        TodoList modifiedList = todoLists.get(listName);

        modifiedList.add(newTask);
        updateJson();
    }

    public Task updateList(String listName, String taskName){
        Task updatedTask = todoLists.get(listName).updateTask(taskName);
        updateJson();
        return updatedTask;

    }

    public void updateJson(){
        //this function goes through the map of lists and creates JSON objects to add to JSONarray
        //then writes JSON array to file
        todoListsJson.clear();

        for (TodoList curList : todoLists.values()){
            todoListsJson.add(curList.getJSON());
        }


        try (FileWriter file = new FileWriter("todo.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(todoListsJson.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
