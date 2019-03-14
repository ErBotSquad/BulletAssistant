
/**
 * SizeComparator.java 
 * Compares the length of two strings
 * 
 * @author Eric Botello
 *
 */


import java.util.Comparator;

public class SizeComparator {

  /**
   * Compares the key word to the word in the node 
   *
   *@param first First String object to be compared.
   *@param second Second String object to be compared.
   *@return 
   */
  public int compareSize(String first, String second) {
    if (first.length() == second.length()) {
      return 0;
    } else if(first.length() < second.length()) {
      return -1;
    } else {
      return 1;
    }
  }
}