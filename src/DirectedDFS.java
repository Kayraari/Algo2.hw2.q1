//-----------------------------------------------------
// Title: Directed depth-first search class
// Author: Kayra Arı, Elçin Karagül
// ID:
// Section: 4
// Assignment: 2
// Description: This class runs DFS from a start intersection
// and counts the intersections that can be reached when one
// given road u -> v is reversed. The digraph itself is not
// changed: during the search the road u -> v is skipped and
// the road v -> u is followed instead. So every other road,
// including an already existing road v -> u, stays as it is.
//-----------------------------------------------------

public class DirectedDFS {
    private boolean[] marked;   // marked[v] = is v reachable from s?
    private int count;          // number of reachable intersections
    private final int ru;       // tail of the reversed road (ru -> rv)
    private final int rv;       // head of the reversed road (ru -> rv)

    public DirectedDFS(Digraph G, int s, int ru, int rv) {
        //--------------------------------------------------------
        // Summary: Creates the marked array and runs DFS from s as
        // if the road ru -> rv had been replaced by rv -> ru.
        // Precondition: 0 <= s < G.V() and ru -> rv is a road of G
        // (G has no duplicate roads).
        // Postcondition: marked[v] is true for every intersection
        // reachable from s and count is the number of them.
        //--------------------------------------------------------
        this.ru = ru;
        this.rv = rv;
        marked = new boolean[G.V()];
        count = 0;
        dfs(G, s);
    }

    private void dfs(Digraph G, int v) {
        //--------------------------------------------------------
        // Summary: Recursive DFS. Marks v and visits all unmarked
        // intersections pointing from v in the modified network.
        // Precondition: v is not marked.
        // Postcondition: v and every intersection reachable from v
        // are marked and counted.
        //--------------------------------------------------------
        marked[v] = true;
        count++;

        for (int w : G.adj(v)) {
            // The reversed road no longer goes from ru to rv
            if (v == ru && w == rv) continue;
            if (!marked[w]) dfs(G, w);
        }

        // The reversed road now goes from rv to ru
        if (v == rv && !marked[ru]) dfs(G, ru);
    }

    public boolean visited(int v) {
        //--------------------------------------------------------
        // Summary: Checks whether v is reachable from s.
        // Precondition: 0 <= v < V.
        // Postcondition: Returns true if v is reachable.
        //--------------------------------------------------------
        return marked[v];
    }

    public int count() {
        //--------------------------------------------------------
        // Summary: Returns the number of intersections reachable
        // from s (s itself is included).
        // Precondition: None.
        // Postcondition: count is returned.
        //--------------------------------------------------------
        return count;
    }
}