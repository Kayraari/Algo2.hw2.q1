//-----------------------------------------------------
// Title: Node class
// Author: Kayra Arı, Elçin Karagül
// ID:
// Section: 4
// Assignment: 2
// Description: This class is a node of a linked list. It
// stores one item and references to the next and previous
// nodes. It is used by Bag.
//-----------------------------------------------------

public class Node<T> {
    protected T data;           // item stored in this node
    protected Node<T> next;     // next node in the list
    protected Node<T> prev;     // previous node in the list

    public Node(T data, Node<T> next, Node<T> prev) {
        //--------------------------------------------------------
        // Summary: Creates a node with the given item and links.
        // Precondition: None.
        // Postcondition: A new node is created.
        //--------------------------------------------------------
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public T getData() {
        //--------------------------------------------------------
        // Summary: Returns the item stored in the node.
        // Precondition: None.
        // Postcondition: data is returned.
        //--------------------------------------------------------
        return data;
    }

    public void setData(T data) {
        //--------------------------------------------------------
        // Summary: Changes the item stored in the node.
        // Precondition: None.
        // Postcondition: data is set to the given item.
        //--------------------------------------------------------
        this.data = data;
    }

    public Node<T> getNext() {
        //--------------------------------------------------------
        // Summary: Returns the next node.
        // Precondition: None.
        // Postcondition: next is returned (null if last).
        //--------------------------------------------------------
        return next;
    }

    public void setNext(Node<T> next) {
        //--------------------------------------------------------
        // Summary: Changes the next node.
        // Precondition: None.
        // Postcondition: next is set to the given node.
        //--------------------------------------------------------
        this.next = next;
    }

    public Node<T> getPrev() {
        //--------------------------------------------------------
        // Summary: Returns the previous node.
        // Precondition: None.
        // Postcondition: prev is returned (null if first).
        //--------------------------------------------------------
        return prev;
    }

    public void setPrev(Node<T> prev) {
        //--------------------------------------------------------
        // Summary: Changes the previous node.
        // Precondition: None.
        // Postcondition: prev is set to the given node.
        //--------------------------------------------------------
        this.prev = prev;
    }
}