/**
 * 
 */


import java.util.Comparator;

/**
 * WordComparator.Java
 * Compares the first character of two strings for sorting purposes
 * 
 * @author Eric Botello
 *
 */
public class WordComparator implements Comparator<String>{

  /**
   * Compares the key word to the word in the node 
   *
   *@param o1 First beer object to be compared.
   *@param o2 Second beer object to be compared.
   *@return returns 1 if o2 has a higher ABV than o1
   */
  public int compare(String key, String word) {
    key = key.toLowerCase();
    word = word.toLowerCase();
    System.out.println("comparing" + key + " key to word " + word);
    if((key.isEmpty() != true) && (word.isEmpty() != true)){
      if (key.equals(word)) {
        return 0;
      } else if (key.charAt(0) < word.charAt(0)){
        return 1;
      } else {
        return -1;
      }
    }else {
      return -1;
    }
  }
}