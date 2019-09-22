import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Stack;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

//SunHacks 2019
//Author: Yasser Dbeis
//Project: Stackz, an interactive and educational visual simulator of stack data structure.

public class Stackz extends Application {
	final int xFrame = 750;
	final int yFrame = 750;
	private int sp = 700;
	private int destinationPop = 600;
	final private int xRect = 200;
	final private int yRect = 100;
	private EventHandler handler; 
	private String in;
	
	public static void main(String[] args) {
	    	launch(args);
	    }
  
	static Stack<Group> s = new Stack<Group>();
	
// Driver code 
	public static void pushIt(PathTransition pathTransition) {
        pathTransition.play();
	}
	
	public static void popIt(PathTransition pathTransition) {
	    pathTransition.play();    
	}
	
       
    public void start(Stage stage) throws Exception{
    	    	
    	Pane pane = new Pane();
    	Scene scene = new Scene(pane, xFrame, yFrame);
        pane.setStyle("-fx-background-color: lightblue;");
    	Line line1 = new Line(xFrame / 2 - 105, yFrame, xFrame / 2 - 105, yFrame - 294);
    	Line line2 = new Line(xFrame / 2 + 105, yFrame, xFrame / 2 + 105, yFrame - 294);
    	line1.setStrokeWidth(10);
    	line2.setStrokeWidth(10);
    	
    	
    	TextField input = new TextField("____");
    	input.setPrefSize(100, 100);
    	input.setFocusTraversable(true);
    	input.setStyle("-fx-background-color: #e87a74; -fx-font-size: 3em; -fx-border-color: #000000; -fx-border-width: 3px");
        Button popButton = new Button("Pop");
        Button pushButton = new Button("Push"); 
        popButton.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        pushButton.setFont(Font.font("Verdana", FontPosture.ITALIC, 30));
        popButton.setMinSize(200, 100);
        pushButton.setMinSize(100, 100);
        popButton.setTranslateX(550);
        pushButton.setTranslateX(100);
        pushButton.setStyle("-fx-background-color: #e87a74;");
        popButton.setStyle("-fx-background-color: #e87a74; -fx-text-fill: #48CF80");


        
        pane.getChildren().addAll(pushButton, input, popButton, line1, line2);
        
        stage.setTitle("Stackz");
        stage.setScene(scene);
        
        stage.show();
        
        Text errorMsg = new Text("Stack is empty! PUSH before you POP!");
        errorMsg.setFont(Font.font("Verdana", 35));
        errorMsg.setFill(Color.RED);
		errorMsg.setTranslateX(37);
		errorMsg.setTranslateY(150);
		
	    popButton.setOnAction(new EventHandler<ActionEvent>() {
	         public void handle(ActionEvent e) {
	        	 if (s.isEmpty()) {
 	    			 pane.getChildren().add(errorMsg);
 	    			 return;
	     	    }
	        	 Path path2 = new Path();
	        	 path2.getElements().add (new MoveTo(xFrame / 2, sp));  
	        	 path2.getElements().add (new CubicCurveTo (350, 0, 550, 180 , xFrame, yFrame - 600));
	     	      
	        	 PathTransition popTransition = new PathTransition();
	        	 popTransition.setDuration(Duration.millis(1500));
	     	     popTransition.setNode(s.peek());
	     	     popTransition.setPath(path2);
	     	     popTransition.setOrientation(OrientationType.NONE);
	     	     popTransition.setCycleCount(1);
	     	     popTransition.setAutoReverse(false);
	     	    
	     	     popTransition.setOnFinished(new EventHandler<ActionEvent>() {
	     	    	 public void handle(ActionEvent e) {
	     	    			 pane.getChildren().remove(s.pop());
		        	}
	     	    	

	     	    });
	     	   popIt(popTransition);
	     	   sp += 100;
	        }
	    });
	    
	    this.handler = new EventHandler<Event>() {
	         public void handle(Event e) {
	        	 pane.getChildren().remove(errorMsg);
	        	 
	        	 in = input.getText();
	        	 
	        	 if(input.getText().contentEquals("")) {
	        		 in = "null";
	        		 
	        	 }	        	 
	        	 s.push(createStack(in));

	        	 input.clear();
	     		 pane.getChildren().add(s.peek());
	        	 Path path = new Path();  
	             path.getElements().add (new MoveTo(0, yFrame - 600));  
	             path.getElements().add (new CubicCurveTo (400, 100, 375, 375, xFrame / 2, sp));
	             
	             PathTransition pushForwardTransition = new PathTransition();
	             pushForwardTransition.setDuration(Duration.millis(1500));
	             pushForwardTransition.setNode(s.peek());
	             pushForwardTransition.setPath(path);
	             pushForwardTransition.setOrientation(OrientationType.NONE);
	             pushForwardTransition.setCycleCount(1);
	             pushForwardTransition.setAutoReverse(false);
	             pushIt(pushForwardTransition);
	             sp -= 100;
	        }
	    };
	    
	    pushButton.setOnAction(handler);
	    input.setOnKeyPressed(e -> {
    		if(e.getCode() == KeyCode.ENTER) {
    			this.handler.handle(e);
    		}
    	});

    }
        
    
    public Group createStack(String n)
    {
    	Rectangle rect = new Rectangle(xRect, yRect,  Color.rgb((int)(Math.random() * 255) , (int)(Math.random() * 255), (int)(Math.random() * 255)));
    	Text text = new Text(n);
    	text.setFont(Font.font("Verdana", 80));
    	text.setX(100 - in.length() * 23);
    	text.setY(yRect / 2 + 28);
    	Group stack = new Group(rect, text);
    	return stack;
    	
    }

    
    
    
    



}