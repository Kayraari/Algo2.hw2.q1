//-----------------------------------------------------
// Title: Tester class
// Author: Kayra Arı, Elçin Karagül
// ID:
// Section: 4
// Assignment: 2
// Description: This class tests the solution of Question 1.
// It runs Main with fixed key test cases (sample inputs, a
// reversal that must be done although it reduces the count,
// lexicographic ties on u and on v, multi-letter names given
// out of order, a road whose opposite road already exists, an
// isolated start, a cycle) and a large network for running
// time. It also generates random networks and compares the
// output with an independent brute-force method that really
// rebuilds the network for every reversal.
//-----------------------------------------------------

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class Tester {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        //--------------------------------------------------------
        // Summary: Runs all fixed tests, the large test and the
        // random tests, then prints a summary.
        // Precondition: Main, Digraph and DirectedDFS classes are
        // compiled in the same folder.
        // Postcondition: Each test is printed as PASS or FAIL and
        // the totals are printed.
        //--------------------------------------------------------
        System.out.println("=== Key test points ===");

        // 1. First sample input given in the assignment
        check("Sample input 1",
                "7 6\nA B C D E F G\nA B\nB C\nC D\nE C\nE F\nF G\nA\n",
                "7\nE C\n");

        // 2. Second sample input: no reversal helps, tie between C D and D E
        check("Sample input 2",
                "5 3\nA B C D E\nA B\nC D\nD E\nA\n",
                "2\nC D\n");

        // 3. Only one road: it must be reversed even though the count drops
        check("Forced reversal reduces the count",
                "2 1\nA B\nA B\nA\n",
                "1\nA B\n");

        // 4. Tie decided by u: A C is smaller than B A although A < C
        check("Lexicographic tie decided by u",
                "4 2\nA B C S\nB A\nA C\nS\n",
                "1\nA C\n");

        // 5. Tie decided by v: same u, roads given in descending order
        check("Lexicographic tie decided by v",
                "4 3\nA B C D\nA C\nA B\nB C\nD\n",
                "1\nA B\n");

        // 6. Multi-letter names given out of order: AB < AC < B
        check("Multi-letter names, unsorted",
                "4 3\nB AC AB S\nB AB\nAC B\nAB AC\nS\n",
                "1\nAB AC\n");

        // 7. Opposite road already exists: reversing B A gives two
        //    roads A B, the existing road A B must not be removed
        check("Opposite road already exists",
                "2 2\nA B\nA B\nB A\nA\n",
                "2\nB A\n");

        // 8. Reversing a road that enters S makes everything reachable
        check("Road into S is reversed",
                "3 2\nA B C\nB A\nB C\nA\n",
                "3\nB A\n");

        // 9. Directed cycle: only reversing C A keeps all three reachable
        check("Directed cycle",
                "3 3\nA B C\nA B\nB C\nC A\nA\n",
                "3\nC A\n");

        // 10. Largest allowed network (300 intersections, 2000 roads)
        largeTest(300, 2000);

        System.out.println();
        System.out.println("=== Randomly generated tests ===");
        randomTests(1000, 2026);

        System.out.println();
        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }

    private static void check(String name, String input, String expected) {
        //--------------------------------------------------------
        // Summary: Runs Main with the input and compares the output
        // with the expected output.
        // Precondition: input is valid for Question 1.
        // Postcondition: PASS or FAIL is printed and counters are
        // updated.
        //--------------------------------------------------------
        String actual = runMain(input);
        if (actual.equals(expected)) {
            passed++;
            System.out.println("PASS  " + name);
        } else {
            failed++;
            System.out.println("FAIL  " + name);
            System.out.println("  Input:\n" + input);
            System.out.println("  Expected:\n" + expected);
            System.out.println("  Actual:\n" + actual);
        }
    }

    private static String runMain(String input) {
        //--------------------------------------------------------
        // Summary: Runs Main.main with the given text as standard
        // input and returns what Main prints.
        // Precondition: input is a valid input string.
        // Postcondition: System.in and System.out are restored.
        //--------------------------------------------------------
        java.io.InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            System.setOut(new PrintStream(buffer));
            Main.main(new String[0]);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        return buffer.toString().replace("\r\n", "\n");
    }

    private static String randomName(Random random, int maxLength) {
        //--------------------------------------------------------
        // Summary: Creates a random name of uppercase letters.
        // Precondition: maxLength >= 1.
        // Postcondition: A name with 1..maxLength letters chosen
        // from A, B and C is returned (few letters give many ties).
        //--------------------------------------------------------
        int length = 1 + random.nextInt(maxLength);
        String name = "";
        for (int i = 0; i < length; i++)
            name += (char) ('A' + random.nextInt(3));
        return name;
    }

    private static void largeTest(int n, int m) {
        //--------------------------------------------------------
        // Summary: Builds a random network with n intersections and
        // m roads, compares Main with the brute-force answer and
        // checks that Main finishes within 2 seconds.
        // Precondition: 2 <= n and 1 <= m <= n(n-1).
        // Postcondition: PASS or FAIL is printed.
        //--------------------------------------------------------
        Random random = new Random(224);

        // Names N0, N1, ... are not allowed (digits), so letters are used
        String[] names = new String[n];
        for (int i = 0; i < n; i++)
            names[i] = "" + (char) ('A' + i / 26) + (char) ('A' + i % 26);

        int[][] road = new int[n][n];
        int[] from = new int[m];
        int[] to = new int[m];
        int count = 0;
        while (count < m) {
            int u = random.nextInt(n);
            int v = random.nextInt(n);
            if (u != v && road[u][v] == 0) {
                road[u][v] = 1;
                from[count] = u;
                to[count] = v;
                count++;
            }
        }
        String input = buildInput(names, from, to, 0);
        String expected = bruteForce(names, road, from, to, 0);

        long start = System.currentTimeMillis();
        String actual = runMain(input);
        long time = System.currentTimeMillis() - start;

        String name = "Large network " + n + " / " + m + " (" + time + " ms)";
        if (actual.equals(expected) && time < 2000) {
            passed++;
            System.out.println("PASS  " + name);
        } else {
            failed++;
            System.out.println("FAIL  " + name);
        }
    }

    private static void randomTests(int count, long seed) {
        //--------------------------------------------------------
        // Summary: Generates random networks and compares Main with
        // the brute-force expected output.
        // Precondition: count > 0.
        // Postcondition: Number of passed random tests is printed;
        // every failing case is printed.
        //--------------------------------------------------------
        Random random = new Random(seed);
        int ok = 0;

        for (int test = 0; test < count; test++) {
            // Random number of intersections with distinct names
            int n = 2 + random.nextInt(7);
            String[] names = new String[n];
            for (int i = 0; i < n; i++) {
                boolean distinct;
                do {
                    names[i] = randomName(random, 3);
                    distinct = true;
                    for (int j = 0; j < i; j++)
                        if (names[j].equals(names[i])) distinct = false;
                } while (!distinct);
            }

            // Random roads without self-loops or duplicates
            int maxM = Math.min(12, n * (n - 1));
            int m = 1 + random.nextInt(maxM);
            int[][] road = new int[n][n];
            int[] from = new int[m];
            int[] to = new int[m];
            int added = 0;
            while (added < m) {
                int u = random.nextInt(n);
                int v = random.nextInt(n);
                if (u != v && road[u][v] == 0) {
                    road[u][v] = 1;
                    from[added] = u;
                    to[added] = v;
                    added++;
                }
            }
            int s = random.nextInt(n);

            String input = buildInput(names, from, to, s);
            String expected = bruteForce(names, road, from, to, s);
            String actual = runMain(input);

            if (actual.equals(expected)) {
                ok++;
                passed++;
            } else {
                failed++;
                System.out.println("FAIL  random test " + (test + 1));
                System.out.println("  Input:\n" + input);
                System.out.println("  Expected:\n" + expected);
                System.out.println("  Actual:\n" + actual);
            }
        }
        System.out.println("Random tests passed: " + ok + " / " + count);
    }

    private static String buildInput(String[] names, int[] from, int[] to, int s) {
        //--------------------------------------------------------
        // Summary: Writes a network in the input format of the
        // assignment.
        // Precondition: from[i] -> to[i] are the roads (indices of
        // names) and s is the index of the start.
        // Postcondition: The input text is returned.
        //--------------------------------------------------------
        StringBuilder input = new StringBuilder();
        input.append(names.length).append(" ").append(from.length).append("\n");
        for (int i = 0; i < names.length; i++) {
            if (i > 0) input.append(" ");
            input.append(names[i]);
        }
        input.append("\n");
        for (int i = 0; i < from.length; i++)
            input.append(names[from[i]]).append(" ").append(names[to[i]]).append("\n");
        input.append(names[s]).append("\n");
        return input.toString();
    }

    private static String bruteForce(String[] names, int[][] road,
                                     int[] from, int[] to, int s) {
        //--------------------------------------------------------
        // Summary: Computes the expected output independently. For
        // every road the network is really changed (one road u -> v
        // removed, one road v -> u added), reachability is computed
        // by repeated passes over the matrix and the network is
        // restored. Roads are compared by name with compareTo.
        // Precondition: road[u][v] = number of roads from u to v.
        // Postcondition: Returns the expected output text.
        //--------------------------------------------------------
        int n = names.length;
        int best = -1;
        int bestRoad = -1;

        for (int i = 0; i < from.length; i++) {
            int u = from[i];
            int v = to[i];
            road[u][v]--;               // remove u -> v
            road[v][u]++;               // add v -> u

            // Repeat until no new intersection becomes reachable
            boolean[] reach = new boolean[n];
            reach[s] = true;
            boolean changed = true;
            while (changed) {
                changed = false;
                for (int a = 0; a < n; a++)
                    for (int b = 0; b < n; b++)
                        if (reach[a] && !reach[b] && road[a][b] > 0) {
                            reach[b] = true;
                            changed = true;
                        }
            }
            int count = 0;
            for (int a = 0; a < n; a++)
                if (reach[a]) count++;

            road[u][v]++;               // restore the network
            road[v][u]--;

            boolean better = count > best;
            if (count == best) {
                int cmp = names[u].compareTo(names[from[bestRoad]]);
                if (cmp == 0) cmp = names[v].compareTo(names[to[bestRoad]]);
                better = cmp < 0;
            }
            if (better) {
                best = count;
                bestRoad = i;
            }
        }
        return best + "\n" + names[from[bestRoad]] + " " + names[to[bestRoad]] + "\n";
    }
}