import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;

public class BFS {

    public static void main(String[] args) throws Exception
    {
        File file = new File("src/labyrinth_8.txt");
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

        BFS.search(graph, false);

    }

    public static String toString(int i, int j) {
        return "(" + i + "," + j + ")";
    }

    public static int[] toInt(String string) {
        string = string.replaceAll("[()]", "");
        String[] split = string.split(",");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

    public static String search(int[][] graph, boolean called)
    {
        boolean[][] marked = new boolean[graph.length][graph.length];
        String[][] parent = new String[graph.length][graph.length];
        ArrayList<String> endNodes = new ArrayList<String>();
        int startNodeY = 0, startNodeX = 0;
        int cenaPoti = 0, premiki = 0, preiskanaVozlisca = 0;

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] == -2) {
                    startNodeY = i;
                    startNodeX = j;
                }
                else if (graph[i][j] == -3) {
                    endNodes.add(toString(i, j));
                }
            }
        }

        Queue<String> queue = new LinkedList<String>();

        marked[startNodeY][startNodeX] = true;
        parent[startNodeY][startNodeX] = "(-1,-1)";

        queue.add(toString(startNodeY, startNodeX));
        preiskanaVozlisca++;
        if (!called) System.out.println("Dajem v vrsto vozlisce " + toString(startNodeY, startNodeX));

        while(!queue.isEmpty())
        {
            String curNode = queue.remove();
            if (!called) System.out.println("Odstranjujem iz vrste vozlisce " + curNode);

            //End node is found
            if (endNodes.contains(curNode))
            {
                if (called) return curNode;
                System.out.println("Resitev BFS v vozliscu " + curNode);
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
                return "";
            }

            //Adding to queue
            int[] array = toInt(curNode);
            int newI = array[0];
            int newJ = array[1];
            if (graph[newI][newJ - 1] != -1 && !marked[newI][newJ - 1]) {
                queue.add(toString(newI, newJ - 1));
                marked[newI][newJ - 1] = true;
                parent[newI][newJ - 1] = curNode;
                if (!called) System.out.println("Dajem v vrsto vozlisce " + toString(newI, newJ - 1));
                preiskanaVozlisca++;
            }
            if (graph[newI + 1][newJ] != -1 && !marked[newI + 1][newJ]) {
                queue.add(toString(newI + 1, newJ));
                marked[newI + 1][newJ] = true;
                parent[newI + 1][newJ] = curNode;
                if (!called) System.out.println("Dajem v vrsto vozlisce " + toString(newI + 1, newJ));
                preiskanaVozlisca++;
            }
            if (graph[newI][newJ + 1] != -1 && !marked[newI][newJ + 1]) {
                queue.add(toString(newI, newJ + 1));
                marked[newI][newJ + 1] = true;
                parent[newI][newJ + 1] = curNode;
                if (!called) System.out.println("Dajem v vrsto vozlisce " + toString(newI, newJ + 1));
                preiskanaVozlisca++;
            }
            if (graph[newI - 1][newJ] != -1 && !marked[newI - 1][newJ]) {
                queue.add(toString(newI - 1, newJ));
                marked[newI - 1][newJ] = true;
                parent[newI - 1][newJ] = curNode;
                if (!called) System.out.println("Dajem v vrsto vozlisce " + toString(newI - 1, newJ));
                preiskanaVozlisca++;
            }
        }
        return "";
    }
}
