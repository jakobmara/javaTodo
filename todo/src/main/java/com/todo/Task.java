package com.todo;

public class Task {
    public String taskName;
    public String taskDispName;
    public Boolean taskStatus;
    
    Task(String taskName, Boolean status){
        if (status){
            this.taskDispName = String.format("<html><strike>%s</strike></html>",taskName);
        }
        else{
            this.taskDispName = taskName;
        }
        this.taskName = taskName;
        this.taskStatus = status;
    }
    public static void main(String args[]){
    
    }

    public String toString(){
        return String.format("Task name: %s, status: %s",this.taskDispName,this.taskStatus);
    }

}
