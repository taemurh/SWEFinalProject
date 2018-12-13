package se2018.SWEFinalProject.Client;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se2018.SWEFinalProject.Client.StoryController;
import se2018.SWEFinalProject.Server.Story;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class FXMLDocumentController implements Initializable {
    private ChatGateway gateway;
    
    @FXML private TextArea textArea;
    @FXML private TextField comment;
    
    @FXML TextField authorField;
	@FXML TextField titleField;
	@FXML TextField pointsField;
	@FXML TextField storyIDField;
	@FXML TextArea descriptionField;
	@FXML TextArea displayDescriptionField;
	@FXML VBox todoColumnVBox;
	@FXML VBox inprogressColumnVBox;
	@FXML VBox testingColumnVBox;
	@FXML VBox doneColumnVBox;
	@FXML Button submitButton;
	
	@FXML TextField displayAuthorField;
	@FXML ComboBox statusDropDown;
	@FXML Label IDField;
	@FXML TextField displayPointsField;
	@FXML TextField displayTitleField;
	
	@FXML NumberAxis xAxis;
	@FXML NumberAxis yAxis;
	@FXML LineChart<Number, Number> lineChart;
	ObservableList<Series<Number, Number>> lineChartData;
	Series<Number, Number> series;
	// @FXML TextArea displayDescriptionField;
    
    @FXML
    private void sendComment(ActionEvent event) {
        String text = comment.getText();
        gateway.sendComment(text);
    }
    
    @FXML 
    protected void handleAddStoryButtonAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("add_story_window.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Story!");
            stage.setScene(new Scene(root, 450, 500));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleBacklogButtonAction(ActionEvent event) {
 
        Parent root;
        try {
            //root = FXMLLoader.load(getClass().getResource("backlog_window.fxml"));
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("backlog_window.fxml"));
             
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Backlog");
            stage.setScene(new Scene(root, 320, 700));
            
          
            
            stage.setOnShowing(new EventHandler<WindowEvent>() {
            	public void handle(WindowEvent e) {
            		int storyCount = gateway.getStoryCount();
                	for (int i = 0; i < storyCount; i++) {
                		int j = i;
                		VBox storyPane = new VBox();
                		storyPane.setPrefHeight(80);
                		storyPane.setPrefWidth(300);
                	
                		
                		
                		// Set Text here for 
                		Story story = gateway.getStory(j);
                		
                		if(story.getStoryPoints() < 5) {
        	    			storyPane.setStyle("-fx-background-color: RGB(130,229,130);");
        	    		}
        	    		else if(story.getStoryPoints() > 4 && story.getStoryPoints()< 8 ) {
        	    			storyPane.setStyle("-fx-background-color: RGB(255,255,100);");
        	    		}
        	    		else {
        	    			storyPane.setStyle("-fx-background-color: RGB(250,150,130);");
        	    		}
                		
                		
                		
                		storyPane.getChildren().add(new Text("Author: " + story.getAuthor()));
                		storyPane.getChildren().add(new Text("Title: "+ story.getTitle()));
                		storyPane.getChildren().add(new Text("Points: " + Integer.toString(story.getStoryPoints())));
                		storyPane.getChildren().add(new Label(Integer.toString(story.getStoryID())));
                		BacklogController controller = loader.getController();
                		
                		
                		storyPane.setOnMouseClicked(ev -> {
            	            Parent root;
            	            try {
            	              
            	                FXMLLoader loader = new FXMLLoader(getClass().getResource("Story_Details.fxml"));
            	                
            	                root = loader.load();
            	                Stage stage = new Stage();
            	                stage.setTitle("Story Details");
            	                stage.setScene(new Scene(root, 475, 700));
            	                stage.show();
            	                
            	               
            	                StoryController storyController = loader.getController();
            	              
            	                storyController.IDField.setText(Integer.toString(story.getStoryID()));
            	                storyController.displayAuthorField.setText(story.getAuthor());
            	                storyController.displayTitleField.setText(story.getTitle());
            	                storyController.displayPointsField.setText(Integer.toString(story.getStoryPoints()));
            	                storyController.displayDescriptionField.setText(story.getDescription());
            	                // status
            	                if(story.getStatus().equals("todo")) 
            	                	storyController.statusDropDown.setValue("TODO");       
            	                else if(story.getStatus().equals("inprogress")) 
            	                	storyController.statusDropDown.setValue("In Progress");            
            	                else if (story.getStatus().equals("testing"))
            	                	storyController.statusDropDown.setValue("Testing");         
            	                else if (story.getStatus().equals("done"))
            	                	storyController.statusDropDown.setValue("Done");          
            	                else
            	                	storyController.statusDropDown.setValue("Backlog");
            	                // chat
            	                // getSize is transcript's size, not story size...confusing name, i'll probs change it
            	                for (int k = 0; k < story.getSize(); k++) {
            	                	VBox chatPane = new VBox();
            	            		chatPane.setPrefHeight(80);
            	            		chatPane.setPrefWidth(300);
            	            		chatPane.getChildren().add(new Text(story.getComment(k)));
            	                }
            	                
            	
            	            }
            	            catch (IOException d) {
            	                d.printStackTrace();
            	            }
            	    		});
                		
                		
	            		if (story.getStatus().equals("not started")) {
	            			
	                		controller.backlogWindow.getChildren().add(storyPane);
	            		}
	            		
	            		
                	}
            	}
            });
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    protected void handleBurndownButtonAction(ActionEvent event) {
        Parent root;
        //root = FXMLLoader.load(getClass().getResource("burndown_window.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Burndown Chart");
		
		lineChartData = FXCollections.observableArrayList();
		series = new LineChart.Series<Number, Number>();
         
		Hashtable<Integer, Integer> burndown = gateway.getBurndown();
		
		for (Integer key: burndown.keySet()) {
		  	series.getData().add(new XYChart.Data<Number, Number>((Number)key, (Number)burndown.get(key)));
		}
		
		System.out.println(series.getData());
		System.out.println(lineChartData);
		lineChartData.add(series);
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Time");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Points");
		lineChart = new LineChart<Number,Number>(xAxis,yAxis);
		lineChart.setData(lineChartData);
		lineChart.setVisible(true);
		
		stage.setScene(new Scene(lineChart, 800, 700));
		stage.show();
    }
    
    @FXML
    protected void handleAddStorySubmitButtonAction(ActionEvent event) {
    	//error checking for empty fields
    	if (authorField.getText().isEmpty() == false) {
    		if (titleField.getText().isEmpty() == false) {
    			if (pointsField.getText().isEmpty() == false) {
    				if (descriptionField.getText().isEmpty() == false) {
	    				System.out.println(authorField.getText());
	    		    	System.out.println(titleField.getText());
	    		    	System.out.println(pointsField.getText());
	    		    	
	    		    	Stage stage = (Stage) submitButton.getScene().getWindow();
	    		    	stage.close();
	    		    	System.out.println("window closed");
	    		    	// SEND INFO TO SERVER
	    		    	
	    		    	Story story = new Story(0, authorField.getText(), titleField.getText(), descriptionField.getText(), Integer.parseInt(pointsField.getText()));
	    		    	String storyJSON = "";

	    		    	try {
	    		    		storyJSON = story.toString_();
	    				} catch (Exception e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    		    	
	    				System.out.println(storyJSON);
	    		      	gateway.sendStory(storyJSON);
    				}
    				else {
    					System.out.println("Missing description text");
    				}
    			}
    			else {
    				System.out.println("Missing points text");
    			}
    		}
    		else {
    			System.out.println("Missing title text");
    		}
    	}
    	else {
    		System.out.println("Missing author text");
    	}

    }
    
    @FXML 
    public void handleRefreshButtonAction(ActionEvent event) {
    	refresh();
    }
    
    @FXML 
    protected void handleStoryClick(ActionEvent event) {
    	
    }
    
    @FXML
	protected void handleUpdateStoryButtonAction(ActionEvent event) {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("backlog_window.fxml"));
    	StoryController controller = loader.getController();
        
    	int id = Integer.parseInt(controller.IDField.getText());
    	Story story = gateway.getStory(id);
    	story.setAuthor(controller.displayAuthorField.getText());
    	story.setDesc(controller.displayDescriptionField.getText());
		story.setStoryPoints(Integer.parseInt(controller.displayPointsField.getText()));
		story.setTitle(controller.displayTitleField.getText());
		if(controller.statusDropDown.getValue().equals("TODO")) {
			story.setStatus("todo");
		} else if(controller.statusDropDown.getValue().equals("In Progress")) {
			story.setStatus("inprogress");
		} else if(controller.statusDropDown.getValue().equals("Testing")) {
			story.setStatus("testing");
		} else if(controller.statusDropDown.getValue().equals("Done")) {
			story.setStatus("done");
		} else {
			story.setStatus("not started");
		}
		
    	/*
    	int id = Integer.parseInt(IDField.getText());
    	Story story = gateway.getStory(id);
    	story.setAuthor(displayAuthorField.getText());
    	story.setDesc(displayDescriptionField.getText());
		story.setStoryPoints(Integer.parseInt(displayPointsField.getText()));
		story.setTitle(displayTitleField.getText());
		if(statusDropDown.getValue().equals("TODO")) {
			story.setStatus("todo");
		} else if(statusDropDown.getValue().equals("In Progress")) {
			story.setStatus("inprogress");
		} else if(statusDropDown.getValue().equals("Testing")) {
			story.setStatus("testing");
		} else if(statusDropDown.getValue().equals("Done")) {
			story.setStatus("done");
		} else {
			story.setStatus("not started");
		}
		*/
		// SEND INFO TO SERVER
    	// Story story = new Story(0, authorField.getText(), titleField.getText(), descriptionField.getText(), Integer.parseInt(pointsField.getText()));
    	/*
		String storyJSON = "";

    	try {
    		storyJSON = story.toString_();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		System.out.println(storyJSON);
      	gateway.sendStory(storyJSON);
      	*/
	}
    
    @FXML
    protected void handleDeleteStoryButtonAction(ActionEvent event) {
    	
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gateway = new ChatGateway(textArea);
        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,textArea, this)).start();
    }  
    
    public void refresh() {
    	//TODO Make for loop and for every story retrieved from server create a new VBOX and append
    	todoColumnVBox.getChildren().clear();
    	inprogressColumnVBox.getChildren().clear();
    	testingColumnVBox.getChildren().clear();
    	doneColumnVBox.getChildren().clear();
    	
    	int storyCount = gateway.getStoryCount();
    	System.out.println("refresh: " + storyCount);
    	for (int i = 0; i < storyCount; i++) {
    		int j = i;
    		VBox storyPane = new VBox();
    		storyPane.setPrefHeight(80);
    		storyPane.setPrefWidth(300);
    	
    		// Set Text here for 
    		Story story = gateway.getStory(j);
    		System.out.println(story);
    		if(story == null) {
    			continue;
    		} else {
    			if(!story.getStatus().equals("not started")) {
    	    		if(story.getStoryPoints() < 5) {
    	    			storyPane.setStyle("-fx-background-color: RGB(130,229,130);");
    	    		}
    	    		else if(story.getStoryPoints() > 4 && story.getStoryPoints()< 8 ) {
    	    			storyPane.setStyle("-fx-background-color: RGB(255,255,100);");
    	    		}
    	    		else {
    	    			storyPane.setStyle("-fx-background-color: RGB(250,150,130);");
    	    		}
    	    		
    	    		
    	    		storyPane.getChildren().add(new Text("Author: " + story.getAuthor()));
    	    		storyPane.getChildren().add(new Text("Title: "+ story.getTitle()));
    	    		storyPane.getChildren().add(new Text("Points: " + Integer.toString(story.getStoryPoints())));
    	    		storyPane.getChildren().add(new Label(Integer.toString(story.getStoryID())));
    	    		System.out.println("DEBUG");
    	    		
    	    		storyPane.setOnMouseClicked(e -> {
    	            Parent root;
    	            try {
    	              
    	                FXMLLoader loader = new FXMLLoader(getClass().getResource("Story_Details.fxml"));
    	                
    	                root = loader.load();
    	                Stage stage = new Stage();
    	                stage.setTitle("Story Details");
    	                stage.setScene(new Scene(root, 475, 700));
    	                stage.show();
    	                
    	               
    	                StoryController controller = loader.getController();
    	              
    	                controller.IDField.setText(Integer.toString(story.getStoryID()));
    	                controller.displayAuthorField.setText(story.getAuthor());
    	                controller.displayTitleField.setText(story.getTitle());
    	                controller.displayPointsField.setText(Integer.toString(story.getStoryPoints()));
    	                controller.displayDescriptionField.setText(story.getDescription());
    	                // status
    	                if(story.getStatus().equals("todo")) 
    	                	controller.statusDropDown.setValue("TODO");       
    	                else if(story.getStatus().equals("inprogress")) 
    	                	controller.statusDropDown.setValue("In Progress");            
    	                else if (story.getStatus().equals("testing"))
    	                	controller.statusDropDown.setValue("Testing");         
    	                else if (story.getStatus().equals("done"))
    	                	controller.statusDropDown.setValue("Done");          
    	                else
    	                	controller.statusDropDown.setValue("Backlog");
    	                // chat
    	                // getSize is transcript's size, not story size...confusing name, i'll probs change it
    	                for (int k = 0; k < story.getSize(); k++) {
    	                	VBox chatPane = new VBox();
    	            		chatPane.setPrefHeight(80);
    	            		chatPane.setPrefWidth(300);
    	            		chatPane.getChildren().add(new Text(story.getComment(k)));
    	                }
    	                
    	
    	            }
    	            catch (IOException d) {
    	                d.printStackTrace();
    	            }
    	    		});
    	    		
    	    		storyPane.setOnMouseReleased(e -> {
    	        		double endDragX = e.getSceneX();
    	        		double endDragY = e.getSceneY();
    	        		System.out.println(endDragX + ", " + endDragY);
    	        		
    	        		Bounds boundsInScene = storyPane.localToScene(storyPane.getBoundsInLocal());
    	        		System.out.println(boundsInScene.getMaxX());
    	        		if (endDragX <= 300) {
    	        			System.out.println("Ended in Todo");
    	        			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    	        				System.out.println("Already in Todo");
    	        			}
    	        			else {
    	        	    		System.out.println("DEBUG1");
    	        				todoColumnVBox.getChildren().add(storyPane);
    	        				String[] fields = storyPane.getChildren().get(3).toString().split("]");
    	        	    		gateway.changeStoryStatus(fields[1].replaceAll("'","") + "-todo");
    	        	    		System.out.println("DEBUG2");
    	
    	        			}
    	        		}
    	        		else if (endDragX <= 600) {
    	        			System.out.println("Ended in In Progress");
    	        			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    	        				System.out.println("Already in In Progress");
    	        			}
    	        			else {
    	        	    		System.out.println("DEBUG3");
    	
    	        				inprogressColumnVBox.getChildren().add(storyPane);
    	        				String[] fields = storyPane.getChildren().get(3).toString().split("]");
    	        				gateway.changeStoryStatus(fields[1].replaceAll("'","") + "-inprogress");
    	        			}
    	        		}
    	        		else if (endDragX <= 900) {
    	        			System.out.println("Ended in Testing");
    	        			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    	        				System.out.println("Already in Testing");
    	        			}
    	        			else {
    	
    	        				testingColumnVBox.getChildren().add(storyPane);
    	        				String[] fields = storyPane.getChildren().get(3).toString().split("]");
    	        				gateway.changeStoryStatus(fields[1].replaceAll("'","") + "-testing");
    	        			}
    	        		}
    	        		else if (endDragX <= 1200) {
    	        			System.out.println("Ended in Done");
    	        			if (endDragX <= boundsInScene.getMaxX() && endDragX >= boundsInScene.getMinX()) {
    	        				System.out.println("Already in Done");
    	        			}
    	        			else {
    	        	    		System.out.println("DEBUG5");
    	
    	        				doneColumnVBox.getChildren().add(storyPane);
    	        				String[] fields = storyPane.getChildren().get(3).toString().split("]");
    	        				gateway.changeStoryStatus(fields[1].replaceAll("'","") + "-done");
    	        			}
    	        		}
    	        	});
    	    		System.out.println("checking status...");
    	    		System.out.println(story.getStatus());
    	    		
    	    		if (story.getStatus().equals("todo" ) ) {
    	    			System.out.println("story added to todo");
    	        		todoColumnVBox.getChildren().add(storyPane);
    	    		} else if (story.getStatus().equals("inprogress")) {
    	    			inprogressColumnVBox.getChildren().add(storyPane);
    	    		} else if (story.getStatus().equals("testing")) {
    	    			testingColumnVBox.getChildren().add(storyPane);
    	    		} else if (story.getStatus().equals("done")) {
    	    			doneColumnVBox.getChildren().add(storyPane);
    	    		}
    	    		
    	        	System.out.println("after checking status");
    	        	System.out.println("story pane: " + storyPane);
    	        	System.out.println("parent: " + storyPane.getParent());
    	    		System.out.println(storyPane.getParent().idProperty().getValue());
        		}
    		}
    		
    	}
    }

class TranscriptCheck implements Runnable, se2018.SWEFinalProject.Chat.ChatConstants {
    private ChatGateway gateway; // Gateway to the server
    private TextArea textArea; // Where to display comments
    private int N; // How many comments we have read
    private FXMLDocumentController dc;
    /** Construct a thread */
    public TranscriptCheck(ChatGateway gateway,TextArea textArea, FXMLDocumentController dc) {
      this.gateway = gateway;
      this.textArea = textArea;
      this.N = 0;
      this.dc = dc;
    }

    /** Run a thread */
    public void run() {
     /* while(true) {
    	dc.refresh();
    	System.out.println("fxml clients...");
        try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
       */   
      }
      
    }
}
