
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

public class RelatedWordsFileProcessor {
  public static SortableList[] inputMemory = new SortableList[50];
  
  public static File pickFile(){
    FileChooser chooser = new FileChooser();//folder picking dialog box
    chooser.setTitle("Select Related Words File");
    //File extension filter
    chooser.getExtensionFilters().add(new ExtensionFilter("CSV File", "*.csv"));
    chooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
    return chooser.showOpenDialog(null);  
  }
  
  public static SortableList[] processFile(File csvFile){
    if(csvFile != null){
      
      Scanner scanIn = null;
      int rowCounter = 0;
      String inputLine = "";
      
      try {
        FileReader fReader = new FileReader(csvFile);
        BufferedReader bReader = new BufferedReader(fReader);
        scanIn = new Scanner(bReader);
          
        while (scanIn.hasNextLine()){
          //scan numbers into a string
          inputLine = scanIn.nextLine();
          //split string at commas
          String[] tempArray = inputLine.split(",");
          //save temporary array contents to list
            SortableList randoList = new SortableList();
            for (int i = 0; i < tempArray.length; i++){
              if (true){
                //System.out.print(tempArray[i]);
                randoList.add(tempArray[i]);
              }
              
            }
            inputMemory[rowCounter] = randoList;
            rowCounter = rowCounter+1;
          }
          
        
        scanIn.close();
        bReader.close();
//        
       
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    else{
    System.out.println(csvFile);
    }
    return inputMemory;
  }//End of processFile Method
}




























