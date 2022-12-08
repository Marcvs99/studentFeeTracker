package javaFX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;

public class KingMarcusStudentFeeTracker extends Application 
{
	
	private ArrayList<Student> studentList = new ArrayList<>();
	

	@Override
	public void start(Stage primaryStage) 
	{
		initializeArrayList(studentList);
		alphabetize(studentList);
		
		FlowPane flowPane = new FlowPane();
		flowPane.setHgap(5);
		flowPane.setVgap(5);
		
		
		TableView<Student> table = new TableView<Student>();
		table.setEditable(false);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table.setPrefWidth(600);
		table.setPrefHeight(500);
		
		
		TextField totalPaidTxt = new TextField();
		totalPaidTxt.setEditable(false);
		
		
		TextField totalDueTxt = new TextField();
		totalDueTxt.setEditable(false);
		
		
		
		
		TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
			@Override
			public void handle(CellEditEvent<Student, String> event) {
				Student student = event.getRowValue();
				student.setName(event.getNewValue());
				autoResizeColumns(table);
				updateArrayList(table, studentList);
				
				double totalP = 0;
    			double totalD = 0;
    			
    			for(int i = 0; i < studentList.size(); i++)
    			{
    				totalP += studentList.get(i).getAmountPaid();
    				totalD += studentList.get(i).getAmountDue();
    			}
    			
    			totalPaidTxt.setText(Double.toString(totalP));
    			totalDueTxt.setText(Double.toString(totalD));
			}
		});
			
		TableColumn<Student, String> addressColumn = new TableColumn<Student, String>("Address");
		addressColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("address"));
		addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		addressColumn.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
			@Override
			public void handle(CellEditEvent<Student, String> event) {
				Student student = event.getRowValue();
				student.setAddress(event.getNewValue());
				autoResizeColumns(table);
				updateArrayList(table, studentList);
				
				double totalP = 0;
    			double totalD = 0;
    			
    			for(int i = 0; i < studentList.size(); i++)
    			{
    				totalP += studentList.get(i).getAmountPaid();
    				totalD += studentList.get(i).getAmountDue();
    			}
    			
    			totalPaidTxt.setText(Double.toString(totalP));
    			totalDueTxt.setText(Double.toString(totalD));
			}
		});
		
		TableColumn<Student, Double> paidColumn = new TableColumn<Student, Double>("Amount Paid");
		paidColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("amountPaid"));
		paidColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		try
		{
			paidColumn.setOnEditCommit(new EventHandler<CellEditEvent<Student, Double>>() {
				@Override
				public void handle(CellEditEvent<Student, Double> event) {
					Student student = event.getRowValue();
					student.setAmountPaid(event.getNewValue());
					autoResizeColumns(table);
					updateArrayList(table, studentList);
					
					double totalP = 0;
	    			double totalD = 0;
	    			
	    			for(int i = 0; i < studentList.size(); i++)
	    			{
	    				totalP += studentList.get(i).getAmountPaid();
	    				totalD += studentList.get(i).getAmountDue();
	    			}
	    			
	    			totalPaidTxt.setText(Double.toString(totalP));
	    			totalDueTxt.setText(Double.toString(totalD));
				}
			});
		}
		catch(Exception e)
		{
		}
		
		
		TableColumn<Student, Double> dueColumn = new TableColumn<Student, Double>("Amount Due");
		dueColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("amountDue"));
		dueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		try
		{
			dueColumn.setOnEditCommit(new EventHandler<CellEditEvent<Student, Double>>() {
				@Override
				public void handle(CellEditEvent<Student, Double> event) {
					Student student = event.getRowValue();
					student.setAmountDue(event.getNewValue());
					autoResizeColumns(table);
					updateArrayList(table, studentList);
					
					double totalP = 0;
	    			double totalD = 0;
	    			
	    			for(int i = 0; i < studentList.size(); i++)
	    			{
	    				totalP += studentList.get(i).getAmountPaid();
	    				totalD += studentList.get(i).getAmountDue();
	    			}
	    			
	    			totalPaidTxt.setText(Double.toString(totalP));
	    			totalDueTxt.setText(Double.toString(totalD));
				}
			});
		}
		catch(Exception e)
		{
		}
		
		table.getColumns().add(nameColumn);
		table.getColumns().add(addressColumn);
		table.getColumns().add(paidColumn);
		table.getColumns().add(dueColumn);
		
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		populateTable(table, studentList);
		
		autoResizeColumns(table);
		
		double totalP = 0;
		double totalD = 0;
		
		for(int i = 0; i < table.getItems().size(); i++)
		{
			totalP += table.getItems().get(i).getAmountPaid();
			totalD += table.getItems().get(i).getAmountDue();
		}
		
		totalPaidTxt.setText(Double.toString(totalP));
		totalDueTxt.setText(Double.toString(totalD));
		
		table.getItems().addListener(new ListChangeListener<>()
				{
					@Override
					public void onChanged(Change<? extends Student> arg0) {
						double totalP = 0;
						double totalD = 0;
						
						for(int i = 0; i < table.getItems().size(); i++)
						{
							totalP += table.getItems().get(i).getAmountPaid();
							totalD += table.getItems().get(i).getAmountDue();
						}
						
						totalPaidTxt.setText(Double.toString(totalP));
						totalDueTxt.setText(Double.toString(totalD));
						
					}
			
				});
		
		
		
		TextField searchTxt = new TextField();
		searchTxt.setPromptText("Search");
		
		searchTxt.setOnKeyTyped(e ->
		{
			if(searchTxt.getText().equals(""))
			{
				alphabetize(studentList);
				populateTable(table, studentList);
			}
			else
			{
				filter(table, studentList, searchTxt.getText());
			}
			
		});
		
		Button editButton = new Button("Edit/Delete");
		
		editButton.setOnAction(e ->
		{
			if(editButton.getText().equals("Edit/Delete"))
			{
				editButton.setText("Stop Editing");
				table.setEditable(true);
			}
			else
			{
				editButton.setText("Edit/Delete");
				table.setEditable(false);
				populateTable(table, studentList);
				
			}
			
			
		});
		
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(e -> 
		{
			ArrayList<Student> deleteList = new ArrayList<>();
			deleteList.addAll(table.getSelectionModel().getSelectedItems());
			
			if(deleteList.size() > 0 && editButton.getText().equals("Stop Editing"))
			{
				deleteItems(studentList, deleteList);
				populateTable(table, studentList);
				updateArrayList(table, studentList);	
			}
			else if(editButton.getText().equals("Edit/Delete"))
			{
				Stage errorStage = new Stage(); 
			    errorStage.setTitle("Error");
			    Label error = new Label("Click the edit button to delete entries");
			    error.setFont(Font.font(15));
			    FlowPane ePane = new FlowPane();
			    ePane.getChildren().add(error);
			    Scene errorScene = new Scene(ePane, 270, 140);
			    errorStage.setScene(errorScene);
			    errorStage.show();
			}
			
		});
		
		Button newButton = new Button("New");
		newButton.setOnAction(e -> 
		{
			 Stage stage = new Stage(); 
			    stage.setTitle("New Record");
			    TextField newNameTxt = new TextField();
			    newNameTxt.setPromptText("Enter Name");
			    
			    TextField newAddressTxt = new TextField();
			    newAddressTxt.setPromptText("Enter Address");
			    
			    TextField newPaidTxt = new TextField();
			    newPaidTxt.setPromptText("Enter Amount Paid");
			    
			    TextField newDueTxt = new TextField();
			    newDueTxt.setPromptText("Enter Amount Due");
			    
			    Button addButton = new Button("Add Student");
			    addButton.setOnAction(f -> 
			    {
			    	if(!newNameTxt.getText().isBlank())
			    	{
			    		double paid;
			    		double due;
			    		
			    		try
			    		{
			    			if(newPaidTxt.getText().equals(""))
				    		{
				    			paid = 0.0;
				    		}
				    		else
				    		{
				    			paid = Double.parseDouble(newPaidTxt.getText());
				    		}
			    			
			    			if(newDueTxt.getText().equals(""))
				    		{
				    			due = 0.0;
				    		}
				    		else
				    		{
				    			due = Double.parseDouble(newDueTxt.getText());
				    		}
			    			
			    			Student newEntry = new Student(newNameTxt.getText(), newAddressTxt.getText(), paid, due);
			    			
			    			studentList.add(newEntry);
			    			alphabetize(studentList);
			    			populateTable(table, studentList);
			    			writeToFile(studentList);
			    			
			    			newNameTxt.clear();
			    			newAddressTxt.clear();
			    			newPaidTxt.clear();
			    			newDueTxt.clear();
			    			
			    			
			    			
			    		}
			    		catch(Exception e1)
			    		{
			    			
			    		}
			    		
			    		
			    		
			    	}
			    });
			    
			    FlowPane newPane = new FlowPane();
			    newPane.orientationProperty().set(Orientation.VERTICAL);
			    newPane.getChildren().add(newNameTxt);
			    newPane.getChildren().add(newAddressTxt);
			    newPane.getChildren().add(newPaidTxt);
			    newPane.getChildren().add(newDueTxt);
			    newPane.getChildren().add(addButton);
			    
			    Scene newScene = new Scene(newPane, 250, 150);
			    stage.setScene(newScene);        
			    stage.show(); 
		});
		
		Label totalPaid = new Label("Total Paid");
		
		Label totalDue = new Label("Total Due");
		
		
		
		GridPane totalPane = new GridPane();
		totalPane.setAlignment(Pos.TOP_LEFT);
		totalPane.setVgap(5);
		totalPane.setHgap(5);
		totalPane.add(totalPaid, 0, 0);
		totalPane.add(totalPaidTxt, 1, 0);
		totalPane.add(editButton, 2, 0);
		totalPane.add(deleteButton, 3, 0);
		totalPane.add(totalDue, 0, 1);
		totalPane.add(totalDueTxt, 1, 1);
		totalPane.add(newButton, 2, 1);
		
		
		flowPane.orientationProperty().set(Orientation.VERTICAL);
		flowPane.setAlignment(Pos.TOP_LEFT);
		
		flowPane.getChildren().add(searchTxt);
		flowPane.getChildren().add(table);
		flowPane.getChildren().add(totalPane);
//		
		
		Scene scene = new Scene(flowPane, 700, 600);
		primaryStage.setTitle("Student Fees");
		primaryStage.setScene(scene); 
		primaryStage.show();
		
		updateArrayList(table, studentList);
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
	
	
	public static void autoResizeColumns( TableView<?> table )
	{
	  
	    table.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY);
	    table.getColumns().stream().forEach( (column) ->
	    {
	        
	        Text t = new Text( column.getText() );
	        double max = t.getLayoutBounds().getWidth();
	        for ( int i = 0; i < table.getItems().size(); i++ )
	        {
	            
	            if ( column.getCellData( i ) != null )
	            {
	                t = new Text( column.getCellData( i ).toString() );
	                double calcwidth = t.getLayoutBounds().getWidth();
	                
	                if ( calcwidth > max )
	                {
	                    max = calcwidth;
	                }
	            }
	        }
	        
	        column.setPrefWidth( max + 10.0d );
	    } );
	}
	
	public static void deleteItems(ArrayList<Student> studentList, ArrayList<Student> deleteList)
	{
		for(int i = 0; i < deleteList.size(); i++)
		{
			for(int j = 0; j < studentList.size(); j++)
			{
				if(deleteList.get(i).getName().equals(studentList.get(j).getName()))
				{
					studentList.remove(j);
				}
			}
		}
		
		
	}
	
	public static void alphabetize(ArrayList<Student> studentList)
	{
		for(int i = 0; i < studentList.size() - 1; i++)
		{
			int min = i;
			
			for(int j = i + 1; j < studentList.size(); j++)
			{
				if(studentList.get(j).getName().compareToIgnoreCase(studentList.get(min).getName()) < 0)
				{
					min = j;	
				}
			}
			
			Student temp = studentList.get(i);
			studentList.set(i, studentList.get(min));
			studentList.set(min, temp);
		}
	}
	
	public static void filter(TableView<Student> table, ArrayList<Student> studentList, String f)
	{
		
		for(int i = 0; i < studentList.size(); i++)
		{
			String s = (studentList.get(i)).getName();
			if(s.toLowerCase().contains(f.toLowerCase()))
			{
				studentList.add(0, studentList.get(i));
				studentList.remove(i + 1);
			}
		}
		
		populateTable(table, studentList);
	}
	
	public static void initializeArrayList(ArrayList<Student> studentList)
	{
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
			String name;
			while((name = reader.readLine()) != null)
			{
				
				String address = reader.readLine();
				double amountPaid = Double.parseDouble(reader.readLine());
				double amountDue = Double.parseDouble(reader.readLine());
				studentList.add(new Student(name, address, amountPaid, amountDue));
			}
			
			reader.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void populateTable(TableView<Student> table, ArrayList<Student> studentList)
	{
		table.getItems().clear();
		for(int i = 0; i < studentList.size(); i++)
		{
			table.getItems().add(studentList.get(i));
		}
		
		
	}
	
	public static void updateArrayList(TableView<Student> table, ArrayList<Student> studentList)
	{
		studentList.clear();
		for(int i = 0; i < table.getItems().size(); i++)
		{
			studentList.add(table.getItems().get(i));
		}
		
		writeToFile(studentList);
		
	}
	
	public static void writeToFile(ArrayList<Student> students)
	{
		try 
		{
		      BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"));
		      for(int i = 0; i < students.size(); i++)
		      {
		    	  writer.write(((Student) students.get(i)).getName());
		    	  writer.write('\n');
		    	  writer.write(((Student) students.get(i)).getAddress());
		    	  writer.write('\n');
		    	  writer.write(Double.toString(((Student) students.get(i)).getAmountPaid()));
		    	  writer.write('\n');
		    	  writer.write(Double.toString(((Student) students.get(i)).getAmountDue()));
		    	  writer.write('\n');
		    	  
		      }
		      
		      writer.close();
		      System.out.println("Successfully wrote to the file.");
		} 
		catch (IOException e) 
		{
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
}
