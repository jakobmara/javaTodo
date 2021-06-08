package com.todo;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class userDataHandler {
    private static Map<String, TodoList> todoLists = new HashMap<>();
    private static JSONArray todoListsJson = new JSONArray(); 
    @SuppressWarnings("unchecked")
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
            System.out.println(todoList);
                       
            
            for(int i = 0; i < todoList.size(); i++){
                JSONObject list = (JSONObject) todoList.get(i);
                TodoList newList = new TodoList(list);
                System.out.println(newList);
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
            System.out.println("keys");
            System.out.println(listName);
            listNames.add(listName);
        }

        return listNames;
    }

    public ArrayList<String> getTasks(String listName){
        ArrayList<String> tasks = new ArrayList<>();

        TodoList todoList = todoLists.get(listName);

        tasks = todoList.getTasks(listName);

        return tasks;

    }

    //This function adds a list to the JSONarray object (which gets written to file)
    public void addList(TodoList newList){

        JSONObject listDetails = new JSONObject();

        JSONArray listTasks = new JSONArray();

        for(Task tasks : newList.tasks){
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
    

}
