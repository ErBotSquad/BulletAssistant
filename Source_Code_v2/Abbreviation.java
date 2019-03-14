/**********************************************************************************************************************************
* Abbreviation.java
* Template for Abbreviation object
* Author: Eric Botello
* Date: 20181223
*
* Variable List:
* String fullword
* String abbrv
*
* Methods List:
* Abbreviation()
* Abbreviation(String, String)
* setFullWord(String)
* setAbbrv(String)
* getFullWord()
* getAbbrv()
* toString()
* 
* 
* 
**********************************************************************************************************************************/

public class Abbreviation {
  private String fullWord;
  private String abbrv;

  public Abbreviation(){
    fullWord = null;
    abbrv = null;
  }
  
  public Abbreviation(String fullWrd, String shortWrd) {
    fullWord = fullWrd;
    abbrv = shortWrd;
  }

  /**
   * @param fullWord - Set the full word of the abbreviation obj
   */
  public void setFullWord(String fullWord) {
    this.fullWord = fullWord;
  }

  /**
   * @param abbrv - Set the abbrv version of the full word
   */
  public void setAbbrv(String abbrv) {
    this.abbrv = abbrv;
  }

  /**
   * Getter for full word of abbrv obj
   * @return the fullWord
   */
  public String getFullWord() {
    return fullWord;
  }

  /**
   * Getter for abbreviated version of full word in obj
   * @return the abbrv
   */
  public String getAbbrv() {
    return abbrv;
  }
  
  /**
   * Converts abbrv obj to string in format "fullword,abbrv,"
   * @return the abbrv
   */
  public String toString() {
    return this.fullWord+","+this.abbrv+",\n";
  }
  
  
  
  
}
