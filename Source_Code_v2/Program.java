/**********************************************************************************************************************************
* Program.java
* EPR/OPR Assist
* Author: Eric Botello
* Date: 20181223
*
* Variable List:
* 
* File  path
* File  pathToFinalMax
*
* Methods List:
* runProgramSetup()
* setUpRelatedWords()
* printRelatedWords()
* searchRelatedWords(String)
* setUpAbbrvs()
* printAbbrv()
* searchAbbrvs(String)
* condenseText(String)
* setSpaceSize(String)
* addSyn(String, String)
* addAbbrv(String, String)
* 
* 
* 
**********************************************************************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class Program {
  
  public static boolean hasImportData = false;
  
  SortableList[] relatedWordCollection;
  AbbrvComparator aCompare = new AbbrvComparator(); 
  BinaryTree abbrvTree = new BinaryTree(aCompare );
  private String trimmedPath = new String();
  private String spaceSize = " ";
  
  /********************************************************************************************
   * This method call
   * @param   n/a                                           *
   * @return  void                                                                            *
   ********************************************************************************************/
  public void runProgramSetup() {
    System.out.println("starting runProgram");
    
    String classpath = System.getProperty("java.class.path");
    System.out.println(classpath);
    trimmedPath = classpath.substring(0,classpath.length() - 1);
    
    while(trimmedPath.charAt(trimmedPath.length()-1) != File.separatorChar){
      trimmedPath = trimmedPath.substring(0,trimmedPath.length() - 1);
    }
    //System.out.println(trimmedPath);
    
    setUpRelatedWords();
    setUpAbbrvs();
  }
  
 
  /********************************************************************************************
   * This method creates an array of lists from imported csv
   * @param   n/a                                           
   * @return  void                                                                            *
   ********************************************************************************************/
  public void setUpRelatedWords() {
    //File csvFile = RelatedWordsFileProcessor.pickFile();
    File csvFile = new File(trimmedPath + "RelatedWordsCSV.csv"); //*******************************************swap for debugging
    
    if (csvFile == null){
      System.out.println("nothing imported");
    }
    else{
      relatedWordCollection = RelatedWordsFileProcessor.processFile(csvFile);
      System.out.println("imported related words file...");
      hasImportData = true;
      System.out.println("done setting up related words");
    }
  }
  
  
  /********************************************************************************************
   * This method calls the setup method in each of the Day classes                            *
   * @param   n/a                                                                             *
   * @return  void                                                                            *
   ********************************************************************************************/
  public void printrelatedwords() {

    try {
      
      File csvFile = new File(trimmedPath + "RelatedWordsCSV.csv");
      PrintWriter finalOutputCSV = new PrintWriter(csvFile);
      
      StringBuilder everything = new StringBuilder();  
        
        for (int i = 0; i < relatedWordCollection.length; i++) {
          if (relatedWordCollection[i] != null){
            for(int j = 0; j < relatedWordCollection[i].size(); j++){
              everything.append(relatedWordCollection[i].get(j)+",");
            }
          }
          everything.append("\n");
        }
      finalOutputCSV.write(everything.toString());
      finalOutputCSV.close();
    } catch (FileNotFoundException e) {
      //System.out.printf("error message %s\n", e );
      e.printStackTrace();
    }
  }
    
  /********************************************************************************************
   * This method searches through a list for related words
   * @param   n/a                                           
   * @return 
   * @return  void                                                                            *
   ********************************************************************************************/
  public SortableList searchRelatedWords(String key) {
    int found; //row number containing key
    int counter = 0;
    for (int i = 0; i < relatedWordCollection.length; i ++) {
        if (relatedWordCollection[i] != null) {
            counter ++;
        }
    }
    
    for (int i = 1; i < (counter); i++){
      SortableList relatedWords = new SortableList();
      relatedWords = relatedWordCollection[i];
      found = relatedWords.indexOf(key.toLowerCase());
      if (found >= 0) { //if key is in list
        return relatedWords;
      }
    }
    SortableList emptyList = new SortableList();
    return emptyList;
    
  }
  
  /********************************************************************************************
   * This method sets imports common abbreviations into a tree data structure
   * @param   n/a                                           *
   * @return  void                                                                            *
   ********************************************************************************************/
  public void setUpAbbrvs() {
    System.out.println("Setting up abbrv tree");
    
    //File csvFile = AbbrvsFileProcessor.pickFile();
    File csvFile = new File(trimmedPath + "AbbrvWords.csv"); //**************************************swap for debugging
    
    if (csvFile == null){
      System.out.println("nothing imported");
    }
    else{
      abbrvTree = AbbrvsFileProcessor.processFile(csvFile);
      System.out.println("imported abbrv file...");
      hasImportData = true;
      System.out.println("done setting up abbrvs");
    }
  }

  
  
  /********************************************************************************************
   * This method calls the setup method in each of the Day classes                            *
   * @param   n/a                                                                             *
   * @return  void                                                                            *
   ********************************************************************************************/
  public void printAbbrv() {
    
    try {

      File csvFile = new File(trimmedPath + "AbbrvWords.csv");
      PrintWriter finalOutputCSV = new PrintWriter(csvFile);
      
      StringBuilder everything = new StringBuilder();
      SortableList abbrvList = new SortableList();  
      abbrvList = abbrvTree.print(abbrvList);  
      for (int i = 0; i < abbrvList.size(); i++){
        everything.append(abbrvList.get(i).toString());
      }
      //System.out.println(abbrvList.get(0).toString());
      finalOutputCSV.write(everything.toString());
      finalOutputCSV.close();
    } catch (FileNotFoundException e) {
      //System.out.printf("error message %s\n", e );
      e.printStackTrace();
    }
  }
  
  
  
  /********************************************************************************************
   * This method searches the binary tree for a corresponding shorthand
   * @param   String key - Full word being searched                                           *
   * @return  Returns the abbreviation of key word
   *          Returns the key word if no abbreviation is found                                *
   ********************************************************************************************/
  public String searchAbbrvs(String key) {
    //System.out.println("searching for abbrv of " + key);
    Abbreviation abbrvObj = new Abbreviation(key, null);
    abbrvObj = (Abbreviation) abbrvTree.find(abbrvObj);
    
    if (abbrvObj == null) {
      return key;
    } else {
      return abbrvObj.getAbbrv();
    }
  }
  
  /********************************************************************************************
   * This method condenses input text using abbreviations
   * @param   n/a                                           
   * @return  void                                                                            
   ********************************************************************************************/
  public String condenseText(String input) {
    String output = new String(); 
    if(input.charAt(0) != '-'){
      output = "- ";
    }
 
    if(input.contains("\u2009")){
       input = input.replaceAll("\u2009", " ");
    }
    if(input.contains("\u2009")){
      input = input.replaceAll("\u2004", " ");
   }
    
    boolean hasDoubleDash = false;
    if (input.contains("--")){
      hasDoubleDash = true;
      input = input.replaceAll("--", " -- ");
    }
    
    String[] inputArray = input.split(" ");
    
    String temp = multiWordCheck(inputArray, 0);
    temp = temp.trim();
    String[] tempArray = temp.split(" ");
    System.out.println(temp);
    for (int i = 0; i < tempArray.length; i++){
      boolean onlyLetters = tempArray[i].chars().allMatch(Character::isLetter); //https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
      boolean isCleaned = false;      
      
      String preAbbrv = tempArray[i];
      
      if (onlyLetters) {
        tempArray[i] = searchAbbrvs(tempArray[i]);
        
      } else {
        String trimWord = tempArray[i].substring(0, tempArray[i].length() - 1);
        isCleaned = trimWord.chars().allMatch(Character::isLetter);
        if (isCleaned){ //if fixed by removing semicolon
            String lastChar = tempArray[i].substring(tempArray[i].length() - 1);
            tempArray[i] = searchAbbrvs(trimWord);
            tempArray[i] = tempArray[i].concat(lastChar);
        } else {//
          tempArray[i] = searchAbbrvs(tempArray[i]);
        }
      }
      
      //check proper K, M, B, T use
      if (tempArray[i].length() == 1){
        if (i == 0){
          tempArray[i] = preAbbrv;
        } else if ( !tempArray[i-1].chars().anyMatch(Character::isDigit)){
          tempArray[i] = preAbbrv;
        }
      }
      
      output = output.concat(tempArray[i]);
      output = output.concat(" ");
    }
    output.trim();
    
    
    //check for wrongly used abbreviations
    int tempIndex = 0;
    if (output.contains(" K ")){
      output = output.replace(" K ", "K ");
    }
    if (output.contains(" B ")){
      output = output.replace(" B ", "B ");
    }
    if (output.contains(" T ")){
      output = output.replace(" T ", "T ");
    }
    if (output.contains(" M ")){
      output = output.replace(" M ", "M ");
    }
    if (output.contains(" w/ ")){
      output = output.replace(" w/ ", " w/");
    }
    
    if (hasDoubleDash){
      output = output.replaceAll(" -- ", "--");
    }
    //adjust spacing
    if (!spaceSize.equals(" ")){
      output = output.replaceAll(" ", spaceSize);
    }
    
    return output;
  } 

  private String multiWordCheck(String[] inputArray, int i) {
    
    if (i >= inputArray.length) {
      //System.out.print("reached the end of the array");
      return "";
    }//https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
    else if ( ((!inputArray[i].chars().allMatch(Character::isLetter)) && (!inputArray[i].endsWith(","))) 
         || (i == inputArray.length-1)){ 
        
      
      return inputArray[i] + " " + multiWordCheck(inputArray, i+1);
    }
    else {
      int j = 1; //count of words to check for acronyms
      while (j < 6){
        //System.out.println("value at j"+j);
        if ( i+j  == inputArray.length-1) {
          break;
        }
        else if (!(inputArray[i+j].chars().allMatch(Character::isLetter)) && (!inputArray[i].endsWith(","))){
          break;
        }
        else {
          j = j+1;
        }
      }
      
      for (int k = j; k > 0 ; k--) {
        
        String bw = buildWord(inputArray, i, i+k);
        
        boolean onlyLetters = false;
        onlyLetters = inputArray[i+k].chars().allMatch(Character::isLetter); //https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
        boolean isCleaned = false;
        String lastChar = "";
        
        if (onlyLetters) {
          System.out.println("built word is "+bw+"!");//no change
          
        } else {
          String trimWord = bw.substring(0, bw.length() - 1);
          String[] builtwords = trimWord.split(" ");
          isCleaned = builtwords[builtwords.length - 1].chars().allMatch(Character::isLetter);
          if (isCleaned){
              lastChar = bw.substring(bw.length() - 1);
              bw = trimWord;
          } 
        }
        String searchResult = searchAbbrvs(bw);
        if (searchResult.equals(bw)){
          if(isCleaned){
            bw.concat(lastChar);
          }
        } else {
          i = i + k + 1;//index of next word after abbrv
          if(isCleaned){
            searchResult = searchResult + lastChar;
          }
          return searchResult + " " + multiWordCheck(inputArray, i);
        }
      }
        
      return inputArray[i] + " " + multiWordCheck(inputArray, i+ 1);
      }
    }
  

  private String buildWord(String[] inputArray, int start, int end) {
      String words = inputArray[start];
      for (int z = start+1; z < end+1; z++) {
        words = words.concat(" ");
        words = words.concat(inputArray[z]);
      }
      
    return words;
  }


  /********************************************************************************************
   * This method sets the width size of space character
   * @param   n/a                                           
   * @return  void                                                                            
   ********************************************************************************************/
  public void setSpaceSize(String input) {
    spaceSize = input;
  } 
  
  /********************************************************************************************
   * This method adds synonym
   * @param   n/a                                           
   * @return  void                                                                            
   ********************************************************************************************/
  public void addSyn(String refWord, String newWord) {
    int i = 0;
    while (i < relatedWordCollection.length) {
      if(relatedWordCollection[i] !=null){
        if ((relatedWordCollection[i].indexOf(newWord) >= 0)){
          return;
        }
        else if ((relatedWordCollection[i].indexOf(refWord) >= 0)){
          relatedWordCollection[i].add(newWord);
          return;
        } else {
          i++;
        }
      }else {
        System.out.println("related word not found, adding both");
        SortableList templist = new SortableList();
        relatedWordCollection[i] = templist;
        relatedWordCollection[i].add(refWord);
        relatedWordCollection[i].add(newWord);
        return;
      }
    }
    System.out.println("cannot add more reference words");
    
  } 
  
  /********************************************************************************************
   * This method adds abbreviation
   * @param   n/a                                           
   * @return  void                                                                            
   ********************************************************************************************/
  public void addAbbrv(String longWord, String shortWord) {
    Abbreviation abbrvObj = new Abbreviation(longWord, shortWord);
    abbrvTree.add(abbrvObj);
  } 
  
   
}
