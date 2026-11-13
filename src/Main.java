//-----------------------------------------------------
// Title: Main class
// Author: Kayra Arı, Elçin Karagül
// ID:
// Section: 4
// Assignment: 2
// Description: This class reads the road network from
// standard input and builds the digraph. Then it tries to
// reverse every road one by one, counts the intersections
// reachable from S each time and prints the largest count
// together with the original road that must be reversed. If
// several roads give the same count, the lexicographically
// smallest original road (u, v) is printed.
//-----------------------------------------------------

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //--------------------------------------------------------
        // Summary: Reads the input, solves the problem and prints
        // the result.
        // Precondition: The input follows the format given in the
        // assignment (N M, intersection names, M roads, S).
        // Postcondition: The answer is printed to standard output.
        //--------------------------------------------------------
        Scanner keyboard = new Scanner(System.in);

        // First line: number of intersections and one-way roads
        int N = keyboard.nextInt();
        int M = keyboard.nextInt();

        // Second line: all intersection names (isolated ones included)
        String[] names = new String[N];
        for (int i = 0; i < N; i++)
            names[i] = keyboard.next();

        // Build the digraph; the roads are also kept in two arrays so
        // that each of them can be tried as the reversed road
        Digraph graph = new Digraph(names);
        int[] from = new int[M];    // from[i] = u of road i (u -> v)
        int[] to = new int[M];      // to[i]   = v of road i (u -> v)
        for (int i = 0; i < M; i++) {
            from[i] = graph.indexOf(keyboard.next());
            to[i] = graph.indexOf(keyboard.next());
            graph.addEdge(from[i], to[i]);
        }

        // Last line: starting intersection S
        int s = graph.indexOf(keyboard.next());

        // Exactly one road must be reversed, so every road is tried
        int best = -1;      // largest number of reachable intersections
        int bestU = -1;     // u of the best original road
        int bestV = -1;     // v of the best original road
        for (int i = 0; i < M; i++) {
            DirectedDFS dfs = new DirectedDFS(graph, s, from[i], to[i]);
            int count = dfs.count();

            // Indices follow alphabetical order, so comparing indices
            // is the same as comparing the names with compareTo
            boolean smaller = from[i] < bestU
                    || (from[i] == bestU && to[i] < bestV);

            if (count > best || (count == best && smaller)) {
                best = count;
                bestU = from[i];
                bestV = to[i];
            }
        }

        // Print the count and the original road u -> v
        System.out.println(best);
        System.out.println(graph.nameOf(bestU) + " " + graph.nameOf(bestV));
    }
}