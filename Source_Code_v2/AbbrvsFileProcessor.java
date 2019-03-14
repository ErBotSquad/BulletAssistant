
import java.io.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AbbrvsFileProcessor {
  public static AbbrvComparator abbrvCompare = new AbbrvComparator();
  public static BinaryTree abbrvTree = new BinaryTree(abbrvCompare);
  
  public static File pickFile(){
    FileChooser chooser = new FileChooser();//folder picking dialog box
    chooser.setTitle("Select Abbreviations File");
    //File extension filter
    chooser.getExtensionFilters().add(new ExtensionFilter("CSV File", "*.csv"));
    return chooser.showOpenDialog(null);
  }
  
  public static BinaryTree processFile(File csvFile){
    if(csvFile != null){
      Scanner scanIn = null;
      int rowCounter = 0;
      String inputLine = "";
      
      try {
        FileReader fReader = new FileReader(csvFile);
        BufferedReader bReader = new BufferedReader(fReader);
        scanIn = new Scanner(bReader);
          
        while (scanIn.hasNextLine()){
          //scan row into a string
          inputLine = scanIn.nextLine();
          //split string at commas
          
          inputLine = inputLine.replaceAll("\"", "");
          String[] tempArray = inputLine.split(",");
          //check for blank column 1
          
          //join comma seperated values
          if ((tempArray.length >= 2)) {
            for (int i = 1; i < tempArray.length-1; i++) {
              tempArray[0] = tempArray[0]+","+ tempArray[i];
            }
            tempArray[1] = tempArray[tempArray.length-1];
          //save temporary array contents to tree
            
            Abbreviation temp = new Abbreviation (tempArray[0], tempArray[1]);
            abbrvTree.add(temp);
          }
          
          
          
        }
          
        
        scanIn.close();
        bReader.close();
       
       
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    else{
    System.out.println(csvFile);
    }
    return abbrvTree;
  }//End of processFile Method
}
