import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class IDAStar {

    int[][] searchGraph;
    ArrayList<String> endNodes;
    int[][] heuristics;
    int preiskanaVozlisca = 0;
    boolean[][] marked;

    LinkedList<String> path;
    boolean found;

    public static void main(String[] args) throws Exception
    {
        File file = new File("src/labyrinth_12.txt");
        Scanner sc1 = new Scanner(file);
        Scanner sc2 = new Scanner(file);
        int rowNum = 0;
        while (sc1.hasNextLine()) {
            rowNum++;
            sc1.nextLine();
        }
        sc1.close();
        int[][] graph = new int[rowNum][rowNum];
        for (int i = 0; i < rowNum; i++) {
            String[] splitLine = sc2.nextLine().split(",");
            for (int j = 0; j < splitLine.length; j++) {
                graph[i][j] = Integer.parseInt(splitLine[j]);
            }
        }
        sc2.close();

        IDAStar idas = new IDAStar();
        idas.find(graph);
    }

    public static int[][] makeHeuristic(int[][] graph) {
        String endNodeS = BFS.search(graph, true);
        int[][] heuristics = new int[graph.length][graph[0].length];
        int[] endNode = toInt(endNodeS);
        int finalI = endNode[0];
        int finalJ = endNode[1];
        int dx, dy;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                dx = Math.abs(j - finalJ);
                dy = Math.abs(i - finalI);
                heuristics[i][j] = dx + dy;
            }
        }
        return heuristics;
    }

    public static String toString(int i, int j) {
        return "(" + i + "," + j + ")";
    }

    public static int[] toInt(String string) {
        string = string.replaceAll("[()]", "");
        String[] split = string.split(",");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

    private int search(int gScore, int bound)
    {
        String curNode = path.get(0);
        int[] curNodeArray = toInt(curNode);
        int curNodeI = curNodeArray[0], curNodeJ = curNodeArray[1];

        int fScore = gScore + heuristics[curNodeI][curNodeJ];

        if (fScore > bound)
        {
            System.out.println("Vozlisce " + curNode + " je zunaj trenutne meje (razdalja " + fScore + ")");
            return fScore;
        }
        marked[curNodeI][curNodeJ] = true;
        System.out.println("Vozlisce " + curNode + " je znotraj trenutne meje");

        if (endNodes.contains(curNode))
        {
            found = true;
            return fScore;
        }

        int min = Integer.MAX_VALUE;
        int res = 0;

        if (searchGraph[curNodeI][curNodeJ - 1] != -1) {
            if (!path.contains(toString(curNodeI, curNodeJ - 1))) {
                path.add(0, toString(curNodeI, curNodeJ - 1));
                if (searchGraph[curNodeI][curNodeJ - 1] == -3)
                    res = search(gScore, bound);
                else
                    res = search(gScore + searchGraph[curNodeI][curNodeJ - 1], bound);
                if (found) return res;
                if (res < min) min = res;
                path.remove(0);
            }
        }
        if (searchGraph[curNodeI + 1][curNodeJ] != -1) {
            if (!path.contains(toString(curNodeI + 1, curNodeJ))) {
                path.add(0, toString(curNodeI + 1, curNodeJ));
                if (searchGraph[curNodeI + 1][curNodeJ] == -3)
                    res = search(gScore, bound);
                else
                    res = search(gScore + searchGraph[curNodeI + 1][curNodeJ], bound);
                if (found) return res;
                if (res < min) min = res;
                path.remove(0);
            }
        }
        if (searchGraph[curNodeI][curNodeJ + 1] != -1) {
            if (!path.contains(toString(curNodeI, curNodeJ + 1))) {
                path.add(0, toString(curNodeI, curNodeJ + 1));
                if (searchGraph[curNodeI][curNodeJ + 1] == -3)
                    res = search(gScore, bound);
                else
                    res = search(gScore + searchGraph[curNodeI][curNodeJ + 1], bound);
                if (found) return res;
                if (res < min) min = res;
                path.remove(0);
            }
        }
        if (searchGraph[curNodeI - 1][curNodeJ] != -1) {
            if (!path.contains(toString(curNodeI - 1, curNodeJ))) {
                path.add(0, toString(curNodeI - 1, curNodeJ));
                if (searchGraph[curNodeI - 1][curNodeJ] == -3)
                    res = search(gScore, bound);
                else
                    res = search(gScore + searchGraph[curNodeI - 1][curNodeJ], bound);
                if (found) return res;
                if (res < min) min = res;
                path.remove(0);
            }
        }

        return min;
    }

    public void find(int[][] graph)
    {
        searchGraph = graph;
        heuristics = makeHeuristic(graph);
        endNodes = new ArrayList<String>();

        path = new LinkedList<String>();

        int startNodeI = 0, startNodeJ = 0;

        for (int i = 0; i < searchGraph.length; i++) {
            for (int j = 0; j < searchGraph[i].length; j++) {
                if (searchGraph[i][j] == -2) {
                    startNodeI = i;
                    startNodeJ = j;
                }
                else if (searchGraph[i][j] == -3) {
                    endNodes.add(toString(i, j));
                }
            }
        }

        path.add(toString(startNodeI, startNodeJ));
        int bound = heuristics[startNodeI][startNodeJ];
        found = false;

        while (true)
        {
            marked = new boolean[searchGraph.length][searchGraph[0].length];
            System.out.println("Meja iskanja je nastavljena na " + bound);

            int res = search(0, bound);

            if (found)
            {
                System.out.println("Resitev IDA* je v vozliscu: " + path.get(0));
                System.out.print("Najdena pot: ");
                for (int i = 0; i < path.size(); i++)
                {
                    if (i > 0)
                        System.out.print(" <-- ");
                    System.out.print(path.get(i));
                }
                for (int i = 0; i < marked.length; i++) {
                    for (int j = 0; j < marked[i].length; j++) {
                        if (marked[i][j]) preiskanaVozlisca++;
                    }
                }
                System.out.println("\nCena poti: " + res);
                System.out.println("Število premikov: " + (path.size() - 1));
                System.out.println("Število preiskanih vozlišč: " + preiskanaVozlisca);
                System.out.println("Meja iskanja je nastavljena na: " + bound);
                break;
            }

            if (res == Integer.MAX_VALUE)
            {
                System.out.println("Iz zacetnega vozlisca ni mozno priti do nobenega ciljnega vozlisca!");
                break;
            }

            //postavi novo mejo iskanja
            bound = res;
        }
    }
}
