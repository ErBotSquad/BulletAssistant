


import java.util.Arrays;
import java.util.Comparator;

/**
 * SortableList.java 
 * A template for a generic sortable doubly linked list.
 * @author Eric
 * 
 * @param <E> arbitrary object type E
 *
 */
public class SortableList<E> {
  private static final int INITIAL_CAPACITY = 10;
  private int capacity;
  private DLinkedNode head;
  private DLinkedNode tail;
  private int size = 0;
  private E[] data;
  private int numberOfSwaps;
  private double startTime;
  private double stopTime;
  private double totalTime;
  
  /**
   * Constructor for double linked sortable list.
   */
  public SortableList() {
    this.head = null;
    this.tail = null;
    this.size = 0;
    this.capacity = INITIAL_CAPACITY;
    this.data = (E[]) new Object[INITIAL_CAPACITY];
  }

  /**
   * Sorts using insertion sort algorithm.
   * Code sourced from text book pg. 385.
   * 
   * @param compare Comparator class.
   */
  public void insertionSort(Comparator<E> compare) {
    startTime = System.nanoTime();
    if (size == 1) {
      //do not sort
    } else {
      saveData();
      numberOfSwaps = 0;
      
      for (int index = 1; index < size; index++) {
        E insertValue = data[index];
        
        while (index > 0 && compare.compare(insertValue, data[index - 1]) < 0) {
          data[index] = data[index - 1];
          index = index - 1;
        }
        data[index] = insertValue;
        
      }
      sortNodes();
    }
    stopTime = System.nanoTime();
  }

  

  /**
   * Sorts using bubble sort algorithm.
   * Code sourced from:
   * https://www.youtube.com/watch?v=F13_wsHDIG4
   *
   * @param compare Pre-built Comparator
   */
  public void bubbleSort(Comparator<E> compare) {
    if (size <= 1) {
      //do not sort
    } else {
      saveData();
      startTime = System.nanoTime();
      numberOfSwaps = 0;
      //numOfCompares = 0;
      E tempValue;
      for (int index = 0; index < size - 1; index++) {
        for (int j = 0; j < (size - 1); j++) {
          //numOfCompares++;
          if (compare.compare(data[j], data[j + 1]) > 0) {
            numberOfSwaps++;
            tempValue = data[j];
            data[j] = data[j + 1];
            data[j + 1] = tempValue;
          }
        }

      }
      sortNodes();
    }
    stopTime = System.nanoTime();
  }


  /**
   * Sorts using selection sort algorithm.
   * Code sourced from textbook pg.381
   * 
   * @param compare Object of type Comparator
   */
  public void selectionSort(Comparator<E> compare) {
    saveData();
    startTime = System.nanoTime(); 
    numberOfSwaps = 0;
    E tempValue;
    for (int i = 0; i < size - 1; i++) {
      int indexOfSmallest = i;
      for (int nextIndex = i + 1; nextIndex < size; nextIndex++) {
        if (compare.compare(data[indexOfSmallest], data[nextIndex]) > 0) {
          indexOfSmallest = nextIndex;
          numberOfSwaps++;
        }
      }
      tempValue = data[i];
      data[i] = data[indexOfSmallest];
      data[indexOfSmallest] = tempValue;
    }
    sortNodes();
    stopTime = System.nanoTime();
  }

  
  public E get(int index) {
    E returnData = null;
    DLinkedNode temp = head;
    
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    } else if (index == 0) {
      returnData = temp.item;
    } else {
      for (int i = 0; i < index; i++) {
        temp = temp.next;
      }
      returnData = temp.item;
    }
    return returnData;
  }

  
  public E set(int index, E element) {
    E oldData = null;
    DLinkedNode temp = head;
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    } else if (index == 0) {
      oldData = temp.item;
      temp.item = element;
    } else {
      for (int i = 0; i < index; i++) {
        temp = temp.next;
      }
      oldData = temp.item; //save old data
      temp.item = element; //replace old data  
    }    
    return oldData;
  }

  public int indexOf(Object obj) {
    int position = -1;
    DLinkedNode temp = head;
    for (int i = 0; i < size; i++) {
      if (temp.item.equals(obj)) {
        position = i;
        break;
      }
      temp = temp.next;
      //System.out.println("position is " + position);
    }
    return position;
  }
  
  
  public int size() {
    // TODO Auto-generated method stub
    return size;
  }

  
  public boolean add(E e) {
    //System.out.println("adding to end");
    if (size == 0) { //add first node
      DLinkedNode newNode = new DLinkedNode(e, null, null);//add data
      head = newNode;//change Head
      tail = newNode;//change Tail
      
    } else {
      DLinkedNode newNode = new DLinkedNode(e, null, tail);//create new node
      tail.next = newNode;
      tail = newNode;
    }
    size++;
    return true;
  }

  
  public void add(int index, E element) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    if (size == 0) {
      DLinkedNode newNode = new DLinkedNode(element, null, null);//add first
      head = newNode;
      tail = newNode;
    } else if (index == 0) { //add to beginning
      DLinkedNode newNode = new DLinkedNode(element, head, null);
      head.prev = newNode;
      head = newNode;
    } else if (index == size) { //add to end
      DLinkedNode newNode = new DLinkedNode(element, null, tail);
      tail.next = newNode;
      tail = newNode;
    } else {
      DLinkedNode temp = head;
      for (int i = 0; i < index - 1; i++) {
        temp = temp.next;
      }
      DLinkedNode newNode = new DLinkedNode(element, temp.next, temp);
      temp.next = newNode;
      newNode.next.prev = newNode;
    }
    size++;
    
  }

  
  public E remove(int index) {
    //System.out.println("removing from index " + index);
    E dataRemoved;
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    DLinkedNode temp = head;
    if (index == 0) { //if removing first item
      dataRemoved = temp.item;
      head = temp.next;
      head.prev = null;
    } else if (index == size - 1) { //remove last item
      temp = tail;
      dataRemoved = tail.item;
      tail = temp.prev;
      tail.next = null;
    } else {
      for (int i = 0; i < index; i++) { //stop at target-1
        temp = temp.next;
      }
      dataRemoved = temp.item;

      temp.prev.next = temp.next;
      temp.next.prev = temp.prev;
      
    }
   
    size = size - 1;
    return dataRemoved;
  }
  
  /**
   * Save items in list to an array for sorting.
   */
  public void saveData() {
    DLinkedNode temp = head;
    for (int i = 0; i < size; i++) {
      buildArray(i, temp.item);
      temp = temp.next;
    }
  }
  
  /**
   * Add a new object to the array at a specified index.
   * Code sourced from textbook pg. 73
   * 
   * @param index Index of the location for new entry.
   * @param newEntry New element to be added to array.
   */
  public void buildArray(int index, E newEntry) {
    if (index < 0 || index > size) {
      throw new ArrayIndexOutOfBoundsException();
    }
    if (size >= capacity) {
      reallocate();
    }
    for (int i = size; i > index; i--) {
      data[i] = data[i - 1];
    }
    data[index] = newEntry;
  }
  
  /**
   * Doubles the capacity of the array.
   * Code sourced from textbook pg. 74
   */
  public void reallocate() {
    capacity = 2 * capacity;
    data = Arrays.copyOf(data, capacity);
  }
  
  /**
   * Sorts nodes based on pre-sorted data.
   * Removes smallest node and adds to the end.
   */
  public void sortNodes() {
    DLinkedNode sortingNode = head;
    
    for (int i = 0; i < size; i++) {
      add(remove(indexOf(data[i])));
    }
  }
  
  
  /**
   * Node class given in homework instructions.
   *
   */
  private class DLinkedNode { 
    E item;
    DLinkedNode next;
    DLinkedNode prev;
    
    public DLinkedNode(E item, DLinkedNode next, DLinkedNode prev) {
      this.item = item;
      this.next = next;
      this.prev = prev;
    }
  }

}
