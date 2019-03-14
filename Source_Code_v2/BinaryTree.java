/**
 * 
 */

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;



/**
 * BinaryTree.java creates a binary tree of data.
 * Sourced from homework 9. 
 * @author Eric Botello
 *
 *@param <E> Generic type E.
 */
public class BinaryTree<E> {
   
  private Comparator<E> comp;
  private Node<E> root;
  boolean addReturn;
  E deleteReturn;
  List<E> myList;
  
  /*constructor sourced from book pg.270*/

  /**
   * Constructor for BinaryTree that initializes comparator and root.
   * @param c comparator for comparing data stored in nodes.
   */
  public BinaryTree(Comparator<E> c) {
    root = null;
    comp = c;
  }

  /*Add method sourced from book pg.287*/
  /**
   * Adds data to a new node in a binary tree if it doesn't already exist.
   * @param item Data to be added.
   * @return returns true if a new node was created.
   */
  public boolean add(E item) {
    root = add(root, item);
    return addReturn;
  }
  
  private Node<E>  add(Node<E> localRoot, E item) {
    if (localRoot == null) {
      addReturn = true;
      return new Node<E>(item);
    } else if (comp.compare(item, localRoot.data) == 0) {
      addReturn = false;
      return localRoot;
    } else if (comp.compare(item, localRoot.data) < 0) {
      localRoot.left = add(localRoot.left, item);
      return localRoot;
    } else  {
      localRoot.right = add(localRoot.right, item);
      return localRoot;
    }
  }

  /**
   * Search for an item in a tree.
   * @param item being searched for
   * @return returns true if the item is in the tree
   */
  public boolean contains(E item) {
    return contains(root, item);
  }

  private boolean contains(Node<E> localRoot, E target) {
    if (localRoot == null) {
      return false;
    } 
    
    int compResult = comp.compare(target, localRoot.data); 
    if (compResult == 0) {
      return true;
    } else if (compResult < 0) {
      return contains(localRoot.left, target);
    } else {
      return contains(localRoot.right, target);
    }
  }
  
  /*method sourced from book pg.285*/
  /**
   * Finds data in a binary tree.
   * @param target Data to be searched for in nodes of binary tree.
   * @return returns data if found, null if not found.
   */
  public E find(E target) {
    return find(root, target);
  }
  
  private E find(Node<E> localRoot, E target) {
    if (localRoot == null) {
      return null;
    } 
    
    int compResult = comp.compare(target, localRoot.data); 
    if (compResult == 0) {
      return localRoot.data;
    } else if (compResult < 0) {
      return find(localRoot.left, target);
    } else {
      return find(localRoot.right, target);
    }
  }
  
  /*method sourced from book pg.285*/
  /**
   * Finds data in a binary tree.
   * @param target Data to be searched for in nodes of binary tree.
   * @return returns data if found, null if not found.
   */
  public SortableList print(SortableList list) {
    print(root, list);
    return list;
  }
  
  private SortableList print(Node<E> localRoot, SortableList list) {
    if (localRoot == null) {
      return null;
    }
    list.add(localRoot.data);
    if (localRoot.left != null){
      print(localRoot.left, list);
    }
    if (localRoot.right != null){
      print(localRoot.right, list);
    }
    
    return list;
  }

  /*Delete method sourced from book pg.291*/
  /**
   * Deletes the target item from a tree.
   * @param target Item to be removed from tree.
   * @return returns value of data stored in removed node.
   */
  public E delete(E target) { 
    root = delete(root, target); 
    return deleteReturn; 
  } 

  private Node<E> delete(Node<E> localRoot, E item) { 
    if (localRoot == null) { 
      // item is not in the tree. 
      deleteReturn = null; 
      return localRoot; 
    } 

    // Search for item to delete. 
    int compResult = comp.compare(item, localRoot.data); 
    if (compResult < 0) { 
      // item is smaller than localRoot.data. 
      localRoot.left = delete(localRoot.left, item); 
      return localRoot; 
    } else if (compResult > 0) { 
      // item is larger than localRoot.data. 
      localRoot.right = delete(localRoot.right, item);
      return localRoot; 
    } else {  
      // item is at local root. 
      deleteReturn = localRoot.data; 
      if (localRoot.left == null) { 
        // If there is no left child, return right child 
        // which can also be null. 
        return localRoot.right; 
      } else if (localRoot.right == null) { 
        // If there is no right child, return left child. 
        return localRoot.left; 
      } else { 
        // Node being deleted has 2 children, replace the data 
        // with inorder predecessor.  
        if (localRoot.left.right == null) { 
          // The left child has no right child. 
          // Replace the data with the data in the 
          // left child. 
          localRoot.data = localRoot.left.data; 
          // Replace the left child with its left child. 
          localRoot.left = localRoot.left.left; 
          return localRoot; 
        } else { 
          // Search for the inorder predecessor (ip) and 
          // replace deleted node's data with ip. 
          localRoot.data = findLargestChild(localRoot.left); 
          return localRoot; 
        } 
      } 
    } 
  }

  /**
   * Removes the target from the tree.
   * @param target item to be removed from the tree
   * @return returns true if the item was successfully removed
   */
  public boolean remove(E target) { 
    root = delete(root, target); 
    if (deleteReturn == null) {
      return false;
    } else {
      return true;
    }
  }
  
  
  /* method sourced from book pg.293*/
  private E findLargestChild(Node<E> parent) { 
    // If the right child has no right child, it is 
    // the inorder predecessor. 
    if (parent.right.right == null) { 
      E returnValue = parent.right.data; 
      parent.right = parent.right.left; 
      return returnValue; 
    } else { 
      return findLargestChild(parent.right); 
    } 
  }
  
  
  /*Nested class from book pg.268*/
  /**
   * This class is the node template for a binary tree.
   * @param <E> Generic type E
   */
  class Node<E> implements Serializable {  
    protected E data; 
    protected Node<E> left; 
    protected Node<E> right;

    // Constructors 
    /** Construct a node with given data and no children.  
        @param data The data to store in this node 
     */ 
    public Node(E data) { 
      this.data = data; 
      left = null; 
      right = null; 
    }
    
    // Methods 
    /** Return a string representation of the node. 
        @return A string representation of the data fields */ 
    public String toString() { 
      return data.toString(); 
    } 
  } 
}



