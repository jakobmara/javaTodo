package com.todo;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        JFrame f= new JFrame();
        final userDataHandler udh = new userDataHandler();
        udh.loadUserLists();
        f.setSize(900,900);
        final JLabel listLabel = new JLabel();
        listLabel.setSize(50,100);  
        listLabel.setText("My Lists");
  
        final DefaultListModel<String> l1 = new DefaultListModel<>();
        ArrayList<String> listNames = udh.getListNames();
        
        for(String list : listNames){
           l1.addElement(list);
           System.out.println("list" + list);
        }
        l1.addElement("<html><strike>this text will be struck through</strike></html>");
  
        final JList<String> todoLists = new JList<>(l1);
        todoLists.setBounds(0,60,250,125);
        
        final JLabel tasksLabel = new JLabel();
        tasksLabel.setBounds(400, 0, 200,100);  
        tasksLabel.setText("Tasks");
        f.add(tasksLabel);
        final DefaultListModel<String> tasks = new DefaultListModel<>(); 
        JList<String> todoTasks = new JList<>(tasks);
        todoTasks.setBounds(400,60,250,125);
        
        JButton addList = new JButton();
  
        addList.setText("New List");
        addList.setBounds(0,500,100,100);
  
        JButton addTask = new JButton();
  
        addTask.setText("New Task");
        addTask.setBounds(400,500,100,100);
  
  
        todoLists.addListSelectionListener( new ListSelectionListener(){
  
           @Override
           public void valueChanged(ListSelectionEvent e) {
  
              String listKey = todoLists.getSelectedValue().toString();
              
              ArrayList<String> listTasks = udh.getTasks(listKey);
              
              System.out.println(listKey);
              tasks.clear();
              for(String task : listTasks){
                 System.out.println(task);
                 tasks.addElement(task);
              }
              tasksLabel.setText(listKey + " Tasks");
              
           }
              
           });
  
        f.add(listLabel);
        f.add(todoTasks);
        f.add(todoLists);
        f.add(addList);
        f.add(addTask);
        f.setLayout(null);
  
        f.setVisible(true);
    }    
}

