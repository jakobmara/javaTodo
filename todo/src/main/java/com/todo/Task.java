package com.todo;

public class Task {
    public String taskName;
    public Boolean taskStatus;
    
    Task(String taskName, Boolean status){
        this.taskName = taskName;
        this.taskStatus = status;
    }
    public static void main(String args[]){
    
    }

    public String toString(){
        return String.format("Task name: %s, status: %s",this.taskName,this.taskStatus);
    }

}
