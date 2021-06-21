package com.todo;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
/**
 * Hello world!
 *
 */
public class App 
{
   final static userDataHandler udh = new userDataHandler();
   final static DefaultListModel<String> l1 = new DefaultListModel<>();
   final static JList<String> todoLists = new JList<>(l1);


    public static void main( String[] args )
    {
      JFrame f= new JFrame();
      f.setTitle("Todo List");
      udh.loadUserLists();
      f.setSize(900,900);
      final JLabel listLabel = new JLabel();
      final JLabel tasksLabel = new JLabel();
      final DefaultListModel<String> tasks = new DefaultListModel<>(); 
      final JList<String> todoTasks = new JList<>(tasks);
      final JButton taskButton = new JButton("Mark as complete");
      
      //idk if these need to be final
      final JButton removeListButton = new JButton("Remove List");
      final JButton removeTaskButton = new JButton("Delete task");
      
      JButton addList = new JButton("New list");
      final JButton addTask = new JButton("New task");

      listLabel.setSize(50,100);  
      listLabel.setText("My Lists");

      displayLists();

      removeListButton.setBounds(130,200,120,35);
      removeTaskButton.setBounds(550,325,120,35);


      todoLists.setBounds(0,60,250,125);
      tasksLabel.setBounds(400, 0, 200,100);  
      tasksLabel.setText("Tasks");
      f.add(tasksLabel);
      todoTasks.setBounds(400,60,250,250);
      addList.setBounds(0,200,120,35);

      addTask.setBounds(400,325,120,35);
      addTask.setEnabled(false);
      taskButton.setEnabled(false);
      removeTaskButton.setEnabled(false);
      taskButton.setBounds(660,165,150,35);


      addList.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            createList();
         }

      });

      todoLists.addListSelectionListener( new ListSelectionListener(){

         @Override
         public void valueChanged(ListSelectionEvent e) {
            addTask.setEnabled(true);
            if (todoLists.getSelectedValue() != null){
               String listKey = todoLists.getSelectedValue().toString();
            
               ArrayList<String> listTasks = udh.getTasks(listKey);
   
               tasks.clear();
               for(String task : listTasks){
                  tasks.addElement(task);
               }
               tasksLabel.setText(listKey + " Tasks");
            }
            
            
         }
            
         });
      todoTasks.addListSelectionListener( new ListSelectionListener(){

         @Override
         public void valueChanged(ListSelectionEvent e) {
            taskButton.setEnabled(true);
            removeTaskButton.setEnabled(true);
            String listName = todoLists.getSelectedValue().toString();
            try {
               String taskName = todoTasks.getSelectedValue().toString();
               Boolean isComp = udh.getTaskStatus(listName, taskName);
               if (isComp){
                  taskButton.setText("Mark as incomplete");
               }else{
                  taskButton.setText("Mark as complete");
               }
            } catch (Exception newE) {
               //TODO: handle exception
            }
            
            
            
         }
            
         });
      taskButton.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            //when the button is pressed the todoList is modified
            if (todoTasks.getSelectedValue() != null){
               String listName = todoLists.getSelectedValue().toString();
               String taskName = todoTasks.getSelectedValue().toString();
               int taskInd = todoTasks.getSelectedIndex();

               Task updatedTask = udh.updateList(listName, taskName);

               tasks.set(taskInd,updatedTask.taskDispName);
               if (updatedTask.taskStatus){
                  taskButton.setText("Mark as incomplete");
               }else{
                  taskButton.setText("Mark as complete");
               }


            }
         }


      });   

      removeTaskButton.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
             //this is supposed to be index of what task is currently selectred
            udh.removeTask(todoLists.getSelectedValue(), todoTasks.getSelectedValue());
            tasks.remove(todoTasks.getSelectedIndex());
         }

      });

      addTask.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            createTask(todoLists.getSelectedValue(), tasks);
         }

      });

      removeListButton.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String removedListName = todoLists.getSelectedValue().toString();
            int curIndex = todoLists.getSelectedIndex();
            DefaultListModel<String> newMod = (DefaultListModel<String>) todoLists.getModel();
            newMod.removeElementAt(curIndex);//this is supposed to be index of what task is currently selectred
            udh.removeList(removedListName);
            tasks.clear();

         }

      });

      

      
      f.add(taskButton);
      f.add(listLabel);
      f.add(todoTasks);
      f.add(todoLists);
      f.add(addList);
      f.add(addTask);
      f.add(removeListButton);
      f.add(removeTaskButton);
      f.setLayout(null);

      f.setVisible(true);
   }

   public static void displayLists(){
      ArrayList<String> listNames = udh.getListNames();
      for(String list : listNames){
         l1.addElement(list);
      }

   }

   public static void createTask(final String listName, final DefaultListModel<String> taskList){
      final JFrame f = new JFrame();

      f.setSize(250,250);
      f.setTitle("Add task(s)");

      f.setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.fill = GridBagConstraints.BOTH;

      final JTextArea taskjArea = new JTextArea();
      JButton addTaskButton = new JButton("Add task");

      addTaskButton.setBounds(50, 210, 50, 50);
      taskjArea.setBounds(50, 50, 150, 150);

      addTaskButton.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String newTask = taskjArea.getText();
            udh.addTask(newTask, listName);
            taskList.addElement(newTask);
         }

      });



      f.add(taskjArea,gbc);
      f.add(addTaskButton,gbc);
      f.setVisible(true);

   }



   public static void createList(){
      final JFrame f = new JFrame();
      f.setSize(450,450);
      f.setLayout(new GridBagLayout());
      f.setTitle("Add list");
      JPanel[] row = new JPanel[3];
      row[0] = new JPanel();
      row[1] = new JPanel();
      row[2] = new JPanel();
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.fill = GridBagConstraints.BOTH;

      JLabel listlJLabel = new JLabel("List Name:");
      final JTextField listNameField = new JTextField(24);
      JLabel taskJLabel = new JLabel("Tasks:");
      final JTextArea taskjArea = new JTextArea();
      JButton createListButton = new JButton("Create List");


      listNameField.setBackground(Color.white);
      taskjArea.setSize(new Dimension(100,100));
      taskjArea.setBackground(Color.white);
      taskjArea.setColumns(24);
      taskjArea.setRows(12);


      row[0].add(listlJLabel);
      row[0].add(listNameField);
      row[1].add(taskJLabel);
      row[1].add(taskjArea);
      row[2].add(createListButton);

      f.add(row[0], gbc);
      f.add(row[1], gbc);
      f.add(row[2], gbc);
      f.setVisible(true);
       
      createListButton.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            // breaks up user input into array of tasks 
            String allTasks[] = taskjArea.getText().split("\\r?\\n");
            ArrayList<Task> newTasks = new ArrayList<>();
            for (String task : allTasks){
               Task newTask = new Task(task,false);
               newTasks.add(newTask);
            }
            String newListName = listNameField.getText();
            TodoList newTodo = new TodoList(newListName,newTasks);
            udh.createList(newTodo);
            l1.addElement(newListName);
            f.dispose();
         }

      });
      
    }
}

