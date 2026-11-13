import java.util.*;

public class Digraph {
    private int n;
    private ArrayList<Integer>[] adj;

    Digraph(int n) {
        this.n = n;
        adj = (ArrayList<Integer>[]) new ArrayList[n];
        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<>();
    }

    void addEdge(int u, int v) {
        adj[u].add(v);
    }

    int V() {
        return n;
    }

    Iterable<Integer> adj(int v) {
        return adj[v];
    }

    int reachableCount(int s, int ru, int rv) {
        //bfs from s counting reachable vertices, as if the edge had been reversed
        boolean[] seen = new boolean[n];
        ArrayDeque<Integer> q = new ArrayDeque<>(); //????
        seen[s] = true;
        q.add(s);
        int count = 1;
        while (!q.isEmpty()) {
            int x = q.poll();
            for (int w : adj[x]) {
                if (x == ru && w == rv) continue;
                if (!seen[w]) {
                    seen[w] = true;
                    count++;
                    q.add(w);
                }
            }
            if (x == rv && !seen[ru]) {
                seen[ru] = true;
                count++;
                q.add(ru);
            }
        }
        return count;
    }
}
