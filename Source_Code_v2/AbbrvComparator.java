import java.util.Comparator;

public class AbbrvComparator implements Comparator<Abbreviation>{

  @Override
  public int compare(Abbreviation o1, Abbreviation o2) {
    WordComparator wcompare = new WordComparator();
    
    return wcompare.compare(o1.getFullWord(),o2.getFullWord());
  }

}
