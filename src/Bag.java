//-----------------------------------------------------
// Title: Bag class
// Author: Kayra Arı, Elçin Karagül
// ID:
// Section: 4
// Assignment: 2
// Description: This class is a collection where items can be
// added and iterated, but not removed. It is implemented as a
// linked list of Nodes and is used for adjacency lists.
//-----------------------------------------------------

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

    private Node<Item> first;   // first node of the list
    private int n;              // number of items

    public Bag() {
        //--------------------------------------------------------
        // Summary: Creates an empty bag.
        // Precondition: None.
        // Postcondition: The bag has no items.
        //--------------------------------------------------------
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        //--------------------------------------------------------
        // Summary: Checks whether the bag is empty.
        // Precondition: None.
        // Postcondition: Returns true if there are no items.
        //--------------------------------------------------------
        return first == null;
    }

    public int size() {
        //--------------------------------------------------------
        // Summary: Returns the number of items in the bag.
        // Precondition: None.
        // Postcondition: n is returned.
        //--------------------------------------------------------
        return n;
    }

    public void add(Item item) {
        //--------------------------------------------------------
        // Summary: Adds an item to the front of the list in O(1).
        // Precondition: None.
        // Postcondition: The item is in the bag and n is increased.
        //--------------------------------------------------------
        first = new Node<Item>(item, first, null);
        n++;
    }

    public Iterator<Item> iterator() {
        //--------------------------------------------------------
        // Summary: Returns an iterator so the bag can be used in a
        // for-each loop.
        // Precondition: None.
        // Postcondition: An iterator starting at the first node is
        // returned.
        //--------------------------------------------------------
        return new LinkedIterator(first);
    }

    // Iterator that walks through the linked list from first to last
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;     // node that will be returned next

        public LinkedIterator(Node<Item> first) {
            //--------------------------------------------------------
            // Summary: Creates an iterator starting at first.
            // Precondition: None.
            // Postcondition: current is set to first.
            //--------------------------------------------------------
            current = first;
        }

        public boolean hasNext() {
            //--------------------------------------------------------
            // Summary: Checks whether there are more items.
            // Precondition: None.
            // Postcondition: Returns true if current is not null.
            //--------------------------------------------------------
            return current != null;
        }

        public Item next() {
            //--------------------------------------------------------
            // Summary: Returns the current item and moves forward.
            // Precondition: hasNext() is true.
            // Postcondition: current moves to the next node.
            //--------------------------------------------------------
            Item item = current.getData();
            current = current.getNext();
            return item;
        }
    }
}