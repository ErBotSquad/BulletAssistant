/**********************************************************************************************************************************
* Main.java
* Final Project: Self-Training Fitness Software
* Author: Eric Botello
* Date: 04152017
*
* Variable List:
* Scene scene1
* 
* 
* Methods List:
* start()
* validateInput()
* main()
* 
**********************************************************************************************************************************/


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.InputMismatchException;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
  
  Scene scene1;//Bullet formatter
  Scene scene2;//Word Loader
  Scene scene3;//Abbrv Loader
  Scene scene4;//test scene
  String searchWord;
  
  @Override
  public void start(Stage primaryStage) throws Exception{
    primaryStage.setHeight(300);
    primaryStage.setWidth(800);
    
      //Initialize managing software
      Program Program = new Program();
      Program.runProgramSetup();
      AbbrvComparator aCompare = new AbbrvComparator();
      
    try { 
      
      /********************************************************************************************
       * SCENE 1 LAYOUT - User inputs Goals and/or imports existing csv file                      *
       * @param                                                             *
       * @return                                                                                  *
       ********************************************************************************************/    

      
      //set up layout
      GridPane gridPane = new GridPane();
      gridPane.setHgap(5);
      gridPane.setVgap(15);
      gridPane.setPadding(new Insets(10));
      
      //Add buttons and boxes
      RadioButton rbSpace1 = new RadioButton("Smaller |\u2009|");
      RadioButton rbSpace2 = new RadioButton("Default |\u0020|") ;
      RadioButton rbSpace3 = new RadioButton("Bigger |\u2004|");
      ToggleGroup group = new ToggleGroup();
      rbSpace1.setToggleGroup(group);
      rbSpace2.setToggleGroup(group);
      rbSpace3.setToggleGroup(group);
      gridPane.add(new Label("Space Size:"), 0, 1);
      gridPane.add(rbSpace1, 1, 1);
      gridPane.add(rbSpace2, 2, 1);
      gridPane.add(rbSpace3, 3, 1);
      
      rbSpace1.setOnAction(c -> Program.setSpaceSize("\u2009"));//set space to smaller
      rbSpace2.setOnAction(c -> Program.setSpaceSize("\u0020"));//set space to default
      rbSpace3.setOnAction(c -> Program.setSpaceSize("\u2004"));//set space to larger
      
      TextField textArea = new TextField();
      textArea.setFont(new Font("Times New Roman", 14));
      textArea.setPromptText("Write Bullet Here");
      textArea.setPrefColumnCount(30);
      textArea.getText();
      gridPane.add(textArea, 0, 3, 5, 1);
      
      TextArea wordPrompt = new TextArea();
      wordPrompt.setEditable(false);
      wordPrompt.setStyle("-fx-background-color: transparent");
      wordPrompt.setFont(new Font("Times New Roman", 14));

      gridPane.add(wordPrompt, 0, 6, 5, 6);
      
      TextArea scratch = new TextArea();
      scratch.setEditable(false);
      scratch.setStyle("-fx-background-color: transparent");
      scratch.setFont(new Font("Times New Roman", 14));
      //gridPane.add(scratch, 0, 6);
      
      Label warning = new Label("");
      warning.setFont(new Font("Times New Roman", 14));
      warning.setTextFill(Color.RED);
      gridPane.add(warning, 1, 4, 5, 4);
      
      ProgressBar bar = new ProgressBar(0);
      gridPane.add(bar, 0, 5);
      
      
      //next block sourced from 
      //https://stackoverflow.com/questions/32403650/javafx-get-current-word-being-typed-in-textarea
      textArea.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
          String text = textArea.getText().substring(0, newPosition.intValue());
          int index ;
          for (index = text.length() - 1; index >= 0 && ! Character.isWhitespace(text.charAt(index)); index--);
          final String lastWord = text.substring(index+1, text.length());
          searchWord = lastWord;
          //System.out.println(searchWord);
      });
      
 
      
      textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
          //next block sourced from 
            //https://stackoverflow.com/questions/32403650/javafx-get-current-word-being-typed-in-textarea
//            textArea.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
//                String text = textArea.getText().substring(0, newPosition.intValue());
//                int index ;
//                for (index = text.length() - 1; index >= 0 && ! Character.isWhitespace(text.charAt(index)); index--);
//                final String lastWord = text.substring(index+1, text.length());
//                searchWord = lastWord;
//                //System.out.println(searchWord);
//            });
            
            
            String allthetext = textArea.getText();
            if (allthetext.length() >0){
                String condensedText = Program.condenseText(allthetext);
                scratch.setText(condensedText);
                
                //sourced from https://stackoverflow.com/questions/13015698/how-to-calculate-the-pixel-width-of-a-string-in-javafx
                Text theText = new Text(condensedText);
                theText.setFont(textArea.getFont());
                double testWidth = theText.getBoundsInLocal().getWidth();
                //
                if (testWidth <= 667.8){
                  bar.setProgress(testWidth/667.8);
                  warning.setText("");
                  
                } else {
                  //warning.setText("Does Not Fit");
                  bar.setProgress(1);
                }
            }
            
            
            switch(event.getCode()) {
            case SPACE: //System.out.println("searching for "+searchWord);
                        SortableList foundWordList = new SortableList();
                        foundWordList = Program.searchRelatedWords(searchWord);
                        StringJoiner joiner = new StringJoiner("\n");
                        for (int i = 0; i < foundWordList.size(); i++ ){
                            String word = (String) foundWordList.get(i);
                            joiner.add(word);
                        }   
                        wordPrompt.setText("Related word: \n" + joiner.toString());
                        
                        break;
            }
          }
          
      } );
      
    //create condense button 
      Button btCondense = new Button("Condense");
      btCondense.setDefaultButton(true);
      btCondense.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            String allthetext = textArea.getText();
            String displayText;
            if (!allthetext.isEmpty()){
              displayText = Program.condenseText(allthetext);
            } else {
              displayText = "";
            }
            wordPrompt.setText(displayText);
            
            //sourced from https://stackoverflow.com/questions/13015698/how-to-calculate-the-pixel-width-of-a-string-in-javafx
            Text theText = new Text(displayText);
            theText.setFont(textArea.getFont());
            double testWidth = theText.getBoundsInLocal().getWidth();
            //
            
            if (testWidth <= 667.8){
              bar.setProgress(testWidth/667.8);
              warning.setText("");
              
            } else {
              warning.setText("Does Not Fit");
              bar.setProgress(1);
            }
            
            wordPrompt.cursorProperty();
            
            //Sourced From https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
              StringSelection stringSelection = new StringSelection(displayText);
              Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
              clipboard.setContents(stringSelection, null);
            //
        }
      });
      gridPane.add(btCondense, 6, 3);
      
      
      
//    //create menu button 
//      MenuButton menu = new MenuButton();
//        menu.setText("Tools");
//      MenuItem item1 = new MenuItem("Add Synonym"); 
//      MenuItem item2 = new MenuItem("Add Abbreviation"); 
//      menu.getItems().add(item1); 
//      menu.getItems().add(item2); 
//      
//      item1.setOnAction(e -> {primaryStage.setScene(scene2);
//        });//go to next page
//      item2.setOnAction(e -> {primaryStage.setScene(scene3);
//      });//go to next page
//      
//      
//      gridPane.add(menu, 6, 0);
 

      
    //adjust alignmnet
      gridPane.setAlignment(Pos.CENTER);
      GridPane.setHalignment(btCondense, HPos.RIGHT);
      //GridPane.setHalignment(menu, HPos.RIGHT);
     // GridPane.setHalignment(btImport, HPos.RIGHT);
      
      scene1 = new Scene(gridPane,400,250);//create scene1
      
//      /********************************************************************************************
//       * SCENE 2 LAYOUT - User inputs related word                                     *
//       * @param                                                             *
//       * @return                                                                                  *
//       ********************************************************************************************/
//      //Set up layout
//      GridPane gridPane2 = new GridPane();
//      gridPane2.setHgap(5);
//      gridPane2.setVgap(5);
//      
//      
//      //create text boxes
//      Label title = new Label("Add Synonym to Database");
//      TextField tfNewAdd = new TextField();
//      tfNewAdd.setPromptText("Synonym");
//      Label arrow = new Label("-->");
//      TextField tfOldReference = new TextField() ;
//      tfOldReference.setPromptText("Reference Word");
//      Button btAdd = new Button("ADD");
//      //add items to scene
//      gridPane2.add(tfOldReference, 1, 1);
//      gridPane2.add(arrow, 2, 1);
//      gridPane2.add(tfNewAdd, 3, 1);
//      gridPane2.add(btAdd, 3, 6);
//      gridPane2.add(title , 0, 0, 5, 1);
//      //adjust alignmnet
//      //gridPane2.setAlignment(Pos.CENTER);
//      tfNewAdd.setAlignment(Pos.CENTER);
//      tfOldReference.setAlignment(Pos.CENTER);
//      arrow.setAlignment(Pos.CENTER);
//      GridPane.setHalignment(btAdd, HPos.RIGHT);
//      
//      //Events
//      btAdd.setDefaultButton(true);
//      btAdd.setOnAction(e -> {
//        Program.addSyn(tfOldReference.getText(), tfNewAdd.getText());
//        Program.printrelatedwords();
//        primaryStage.setScene(scene1);
//
//        
//      });//go to next page
//      
//      
//      scene2 = new Scene(gridPane2,400,250);//create scene2     
//
//      /********************************************************************************************
//       * SCENE 3 LAYOUT - User inputs Abbreviation                                     *
//       * @param                                                             *
//       * @return                                                                                  *
//       ********************************************************************************************/
//      //Set up layout
//      GridPane gridPane3 = new GridPane();
//      gridPane3.setHgap(5);
//      gridPane3.setVgap(5);
//      
//      
//      //create text boxes
//      Label title2 = new Label("Add Abbreviation to Database");
//      TextField tfNewAdd2 = new TextField();
//      tfNewAdd2.setPromptText("Abbreviation");
//      Label arrow2 = new Label("-->");
//      TextField tfOldReference2 = new TextField() ;
//      tfOldReference2.setPromptText("Reference Word");
//      Button btAdd2 = new Button("ADD");
//      
//      //add items to scene
//      gridPane3.add(btAdd2, 3, 6);
//      gridPane3.add(title2 , 0, 0, 5, 1);
//      gridPane3.add(tfOldReference2, 1, 1);
//      gridPane3.add(arrow2, 2, 1);
//      gridPane3.add(tfNewAdd2, 3, 1);
//      
//      //adjust alignmnet
//      //gridPane3.setAlignment(Pos.CENTER);
//      tfNewAdd2.setAlignment(Pos.CENTER);
//      tfOldReference2.setAlignment(Pos.CENTER);
//      arrow2.setAlignment(Pos.CENTER);
//      GridPane.setHalignment(btAdd2, HPos.RIGHT);
//      
//      //Events
//      btAdd2.setDefaultButton(true);
//      btAdd2.setOnAction(e -> {
//        Program.addAbbrv(tfOldReference2.getText(), tfNewAdd2.getText());
//        Program.printAbbrv();
//        primaryStage.setScene(scene1);
//        
//      });//go to next page
//      
//      
//      scene3 = new Scene(gridPane3,400,250);//create scene3  
//      
//      /*************************
//       * end of scene3
//       ************************/
//      
      
      primaryStage.setTitle("Bullet Assistant");
      primaryStage.setScene(scene1);
      scene1.getStylesheets().add("mystyle.css");
      primaryStage.show();
    
    
    } catch(Exception e) {
      e.printStackTrace();
    }
  }//End Scenes

  public static void main(String[] args) {
    launch(args);
  }
  
}//End Main Class
