import java.util.Scanner;
import java.util.HashMap;

public class Main{
    public static void main(String[]args){
        Scanner keyboard = new Scanner(System.in);
        int n = keyboard.nextInt();
        int m= keyboard.nextInt();

        String[] names = new String[n];
        HashMap<String, Integer> id = new HashMap<>();

        for(int i =0; i<n; i++){
            names[i] = keyboard.next();
            id.put(names[i],i);
        }

        Digraph g = new Digraph(n);
        int[] eu = new int[m];
        int[] ev = new int[m];
        for(int i =0; i<m; i++){
            eu[i]= id.get(keyboard.next());
            ev[i] = id.get(keyboard.next());
            g.addEdge(eu[i], ev[i]);
        }

        int s = id.get(keyboard.next());

        int best = -1, bu=-1, bv=-1;
        for(int i =0; i<m; i++){
            int c=g.reachableCount(s,eu[i],ev[i]);
            boolean better = c>best;
            if(c==best){
                int cmp = names[eu[i]].compareTo(names[bv]);
                if(cmp==0) cmp=names[ev[i]].compareTo(names[bv]);
                better = cmp <0;
            }
            if (better) { best = c; bu = eu[i]; bv = ev[i]; }
        }
        System.out.println(best);
        System.out.println(names[bu] + " " + names[bv]);
    }
}
