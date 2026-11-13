//-----------------------------------------------------
// Title: Digraph class
// Author: Kayra Arı, Elçin Karagül
// ID:
// Section: 4
// Assignment: 2
// Description: This class represents the city as a directed
// graph. Intersections are identified by String names. The
// names are sorted alphabetically and each name gets an index
// 0..V-1, so index order is the same as alphabetical order.
// A directed edge u -> v is a one-way road from u to v.
// Adjacency lists are stored in an array of Bags.
//-----------------------------------------------------

import java.util.Arrays;

public class Digraph {
    private final int V;            // number of intersections
    private int E;                  // number of one-way roads
    private String[] names;         // names[i] = name of intersection i (sorted)
    private Bag<Integer>[] adj;     // adj[i] = intersections pointing from i

    public Digraph(String[] intersectionNames) {
        //--------------------------------------------------------
        // Summary: Creates a digraph with the given intersections
        // and no roads. Names are sorted alphabetically.
        // Precondition: intersectionNames contains distinct names.
        // Postcondition: An empty Bag is created for every
        // intersection.
        //--------------------------------------------------------
        V = intersectionNames.length;
        E = 0;
        names = intersectionNames.clone();
        Arrays.sort(names);         // uses String.compareTo
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<Integer>();
    }

    public int indexOf(String name) {
        //--------------------------------------------------------
        // Summary: Finds the index of an intersection by its name.
        // Precondition: name is a String.
        // Postcondition: Returns the index of the intersection, or
        // -1 if there is no intersection with that name.
        //--------------------------------------------------------
        for (int i = 0; i < V; i++)
            if (names[i].equals(name)) return i;
        return -1;
    }

    public String nameOf(int v) {
        //--------------------------------------------------------
        // Summary: Returns the name of the intersection with
        // index v.
        // Precondition: 0 <= v < V.
        // Postcondition: The intersection name is returned.
        //--------------------------------------------------------
        return names[v];
    }

    public void addEdge(int v, int w) {
        //--------------------------------------------------------
        // Summary: Adds a one-way road from v to w.
        // Precondition: 0 <= v, w < V.
        // Postcondition: w is added to the adjacency list of v
        // (only in this direction) and E is increased by one.
        //--------------------------------------------------------
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        //--------------------------------------------------------
        // Summary: Returns the intersections pointing from v.
        // Precondition: 0 <= v < V.
        // Postcondition: An iterable over the indices w with a
        // road v -> w is returned (in no particular order).
        //--------------------------------------------------------
        return adj[v];
    }

    public int V() {
        //--------------------------------------------------------
        // Summary: Returns the number of intersections.
        // Precondition: None.
        // Postcondition: V is returned.
        //--------------------------------------------------------
        return V;
    }

    public int E() {
        //--------------------------------------------------------
        // Summary: Returns the number of one-way roads.
        // Precondition: None.
        // Postcondition: E is returned.
        //--------------------------------------------------------
        return E;
    }
}