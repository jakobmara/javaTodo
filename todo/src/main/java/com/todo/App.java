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
    public static void main( String[] args )
    {
      JFrame f= new JFrame();
      udh.loadUserLists();
      f.setSize(900,900);
      final JLabel listLabel = new JLabel();
      final DefaultListModel<String> l1 = new DefaultListModel<>();
      final JList<String> todoLists = new JList<>(l1);
      final JLabel tasksLabel = new JLabel();
      final DefaultListModel<String> tasks = new DefaultListModel<>(); 
      JList<String> todoTasks = new JList<>(tasks);
      JButton addList = new JButton();
      JButton addTask = new JButton();

      listLabel.setSize(50,100);  
      listLabel.setText("My Lists");

      ArrayList<String> listNames = udh.getListNames();
      
      for(String list : listNames){
         l1.addElement(list);
         System.out.println("list" + list);
      }

      todoLists.setBounds(0,60,250,125);
      tasksLabel.setBounds(400, 0, 200,100);  
      tasksLabel.setText("Tasks");
      f.add(tasksLabel);
      todoTasks.setBounds(400,60,250,125);
      addList.setText("New List");
      addList.setBounds(0,500,100,100);

      addTask.setText("New Task");
      addTask.setBounds(400,500,100,100);
      
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
            // TODO Auto-generated method stub
            System.out.println("something");
            String allTasks[] = taskjArea.getText().split("\\r?\\n");
            ArrayList<Task> newTasks = new ArrayList<>();
            for (String task : allTasks){
               Task newTask = new Task(task,false);
               newTasks.add(newTask);
            }
            String newListName = listNameField.getText();
            TodoList newTodo = new TodoList(newListName,newTasks);
            udh.createList(newTodo);
            f.dispose();
         }

      });
      
    }
}

