package com.todo;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

        Map<String,Boolean> todoList = todoLists.get(listName);

        for (String key : todoList.keySet()){
            String task;
            System.out.println(key);
            //String isComplete = Boolean.parseBoolean(todoList.get(key));
            System.out.println(todoList.get(key));
            
            if(todoList.get(key)){
                task = String.format("<html><strike>%s</strike></html>",key);
            }else{
                task = key;
            }
            tasks.add(task);            
        }

        return tasks;

    }
    

}
