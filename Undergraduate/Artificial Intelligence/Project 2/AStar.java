import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class AStar {

    public static void main(String[] args) throws Exception
    {
        File file = new File("src/labyrinth_5.txt");
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

        AStar.search(graph);
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

    public static void search(int[][] graph)
    {
        boolean[][] closed = new boolean[graph.length][graph.length];
        String[][] parent = new String[graph.length][graph.length];
        ArrayList<String> endNodes = new ArrayList<String>();
        int startNodeI = 0, startNodeJ = 0;
        int cenaPoti = 0, premiki = 0, preiskanaVozlisca = 0;
        int[][] heuristics = makeHeuristic(graph);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] == -2) {
                    startNodeI = i;
                    startNodeJ = j;
                }
                else if (graph[i][j] == -3) {
                    endNodes.add(toString(i, j));
                }
            }
        }

        LinkedList<String> open = new LinkedList<String>();

        int[][] gScore = new int[graph.length][graph[0].length];
        int[][] fScore = new int[graph.length][graph[0].length];

        for (int i = 0; i < gScore.length; i++)
        {
            for (int j = 0; j < gScore[i].length; j++) {
                gScore[i][j] = Integer.MAX_VALUE;
                fScore[i][j] = Integer.MAX_VALUE;
            }
        }

        gScore[startNodeI][startNodeJ] = 0;
        fScore[startNodeI][startNodeJ] = heuristics[startNodeI][startNodeJ];
        parent[startNodeI][startNodeJ] = "(-1,-1)";

        open.add(toString(startNodeI, startNodeJ));
        System.out.println("Odpiram vozlisce" + toString(startNodeI, startNodeJ));
        preiskanaVozlisca++;

        while(!open.isEmpty())
        {
            int minVal = Integer.MAX_VALUE;
            int minPos = 0;
            int curNodeI = 0, curNodeJ = 0;
            String curNode = "";

            for (int i = 0; i < open.size(); i++)
            {
                String node = open.get(i);
                int[] nodeCoordinates = toInt(node);
                int nodeI = nodeCoordinates[0], nodeJ = nodeCoordinates[1];
                if (fScore[nodeI][nodeJ] < minVal)
                {
                    minVal = fScore[nodeI][nodeJ];
                    minPos = i;
                    curNodeI = nodeI;
                    curNodeJ = nodeJ;
                }
            }
            curNode = toString(curNodeI, curNodeJ);

            open.remove(minPos);
            closed[curNodeI][curNodeJ] = true;
            System.out.println("Zapiram vozlisce " + curNode);

            if (endNodes.contains(toString(curNodeI, curNodeJ)))
            {
                System.out.println("Resitev A* v vozliscu " + curNode);
                System.out.print("Pot: " + curNode);

                while (true)
                {
                    int[] array = toInt(curNode);
                    if (graph[array[0]][array[1]] != -3) premiki++;
                    if (graph[array[0]][array[1]] != -2 && graph[array[0]][array[1]] != -3) cenaPoti += graph[array[0]][array[1]];
                    curNode = parent[array[0]][array[1]];
                    if (!curNode.equals("(-1,-1)"))
                        System.out.print(" <-- " + curNode);
                    else
                        break;
                }
                System.out.println("");
                System.out.println("Cena poti: " + cenaPoti);
                System.out.println("Število premikov: " + premiki);
                System.out.println("Število preiskanih vozlišč: " + preiskanaVozlisca);
                return;
            }

            String nextNode = "";
            int dist = 0;
            if (graph[curNodeI][curNodeJ - 1] != -1 && !closed[curNodeI][curNodeJ - 1]) {
                nextNode = toString(curNodeI, curNodeJ - 1);
                if(!open.contains(nextNode)) {
                    System.out.println("Odpiram vozlisce " + nextNode);
                    preiskanaVozlisca++;
                }
                open.add(nextNode);
                dist = gScore[curNodeI][curNodeJ] + graph[curNodeI][curNodeJ - 1];
                if (dist < gScore[curNodeI][curNodeJ - 1])
                {
                    parent[curNodeI][curNodeJ - 1] = curNode;
                    gScore[curNodeI][curNodeJ - 1] = dist;
                    fScore[curNodeI][curNodeJ - 1] = gScore[curNodeI][curNodeJ - 1] + heuristics[curNodeI][curNodeJ - 1];
                    System.out.println("Posodabljam vozlisce " + nextNode);
                }
            }
            if (graph[curNodeI + 1][curNodeJ] != -1 && !closed[curNodeI + 1][curNodeJ]) {
                nextNode = toString(curNodeI + 1, curNodeJ);
                if(!open.contains(nextNode)) {
                    System.out.println("Odpiram vozlisce " + nextNode);
                    preiskanaVozlisca++;
                }
                open.add(nextNode);
                dist = gScore[curNodeI][curNodeJ] + graph[curNodeI + 1][curNodeJ];
                if (dist < gScore[curNodeI + 1][curNodeJ])
                {
                    parent[curNodeI + 1][curNodeJ] = curNode;
                    gScore[curNodeI + 1][curNodeJ] = dist;
                    fScore[curNodeI + 1][curNodeJ] = gScore[curNodeI + 1][curNodeJ] + heuristics[curNodeI + 1][curNodeJ];
                    System.out.println("Posodabljam vozlisce " + nextNode);
                }
            }
            if (graph[curNodeI][curNodeJ + 1] != -1 && !closed[curNodeI][curNodeJ + 1]) {
                nextNode = toString(curNodeI, curNodeJ + 1);
                if(!open.contains(nextNode)) {
                    System.out.println("Odpiram vozlisce " + nextNode);
                    preiskanaVozlisca++;
                }
                open.add(nextNode);
                dist = gScore[curNodeI][curNodeJ] + graph[curNodeI][curNodeJ + 1];
                if (dist < gScore[curNodeI][curNodeJ + 1])
                {
                    parent[curNodeI][curNodeJ + 1] = curNode;
                    gScore[curNodeI][curNodeJ + 1] = dist;
                    fScore[curNodeI][curNodeJ + 1] = gScore[curNodeI][curNodeJ + 1] + heuristics[curNodeI][curNodeJ + 1];
                    System.out.println("Posodabljam vozlisce " + nextNode);
                }
            }
            if (graph[curNodeI - 1][curNodeJ] != -1 && !closed[curNodeI - 1][curNodeJ]) {
                nextNode = toString(curNodeI - 1, curNodeJ);
                if(!open.contains(nextNode)) {
                    System.out.println("Odpiram vozlisce " + nextNode);
                    preiskanaVozlisca++;
                }
                open.add(nextNode);
                dist = gScore[curNodeI][curNodeJ] + graph[curNodeI - 1][curNodeJ];
                if (dist < gScore[curNodeI - 1][curNodeJ])
                {
                    parent[curNodeI - 1][curNodeJ] = curNode;
                    gScore[curNodeI - 1][curNodeJ] = dist;
                    fScore[curNodeI - 1][curNodeJ] = gScore[curNodeI - 1][curNodeJ] + heuristics[curNodeI - 1][curNodeJ];
                    System.out.println("Posodabljam vozlisce " + nextNode);
                }
            }
        }
    }
}