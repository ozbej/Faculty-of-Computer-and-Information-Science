
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class IDDFS {

    public static void main(String[] args) throws Exception
    {
        File file = new File("src/labyrinth_9.txt");
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

        IDDFS.search(graph);

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
        ArrayList<String> endNodes = new ArrayList<String>();
        int startNodeY = 0, startNodeX = 0;
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
        int meja = 0;
        while(true) {
            boolean[][] marked = new boolean[graph.length][graph.length];
            String[][] parent = new String[graph.length][graph.length];
            int cenaPoti = 0, premiki = 0, preiskanaVozlisca = 0;

            Stack<String> stack = new Stack<String>();

            marked[startNodeY][startNodeX] = true;
            parent[startNodeY][startNodeX] = "(-1,-1)";
            preiskanaVozlisca++;
            stack.push(toString(startNodeY, startNodeX));

            System.out.println("Polagam na sklad vozlisce " + toString(startNodeY, startNodeX));

            while (!stack.isEmpty()) {
                String curNode = stack.peek();

                if (endNodes.contains(curNode)) {
                    System.out.println("Resitev DFS v vozliscu " + curNode);
                    System.out.print("Pot: " + curNode);

                    while (true) {
                        int[] array = toInt(curNode);
                        if (graph[array[0]][array[1]] != -3) premiki++;
                        if (graph[array[0]][array[1]] != -2 && graph[array[0]][array[1]] != -3)
                            cenaPoti += graph[array[0]][array[1]];
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
                    System.out.println("Meja: " + meja);
                    return;
                }

                // najdi neobiskanega naslednika
                boolean found = false;
                if (stack.size() <= meja) {
                    int[] array = toInt(curNode);
                    int newI = array[0];
                    int newJ = array[1];
                    if (graph[newI][newJ - 1] != -1 && !marked[newI][newJ - 1]) {
                        stack.push(toString(newI, newJ - 1));
                        marked[newI][newJ - 1] = true;
                        parent[newI][newJ - 1] = curNode;
                        preiskanaVozlisca++;
                        System.out.println("Polagam na sklad vozlisce " + toString(newI, newJ - 1));
                        found = true;
                    } else if (graph[newI + 1][newJ] != -1 && !marked[newI + 1][newJ]) {
                        stack.push(toString(newI + 1, newJ));
                        marked[newI + 1][newJ] = true;
                        parent[newI + 1][newJ] = curNode;
                        preiskanaVozlisca++;
                        System.out.println("Polagam na sklad vozlisce " + toString(newI + 1, newJ));
                        found = true;
                    } else if (graph[newI][newJ + 1] != -1 && !marked[newI][newJ + 1]) {
                        stack.push(toString(newI, newJ + 1));
                        marked[newI][newJ + 1] = true;
                        parent[newI][newJ + 1] = curNode;
                        preiskanaVozlisca++;
                        System.out.println("Polagam na sklad vozlisce " + toString(newI, newJ + 1));
                        found = true;
                    } else if (graph[newI - 1][newJ] != -1 && !marked[newI - 1][newJ]) {
                        stack.push(toString(newI - 1, newJ));
                        marked[newI - 1][newJ] = true;
                        parent[newI - 1][newJ] = curNode;
                        preiskanaVozlisca++;
                        System.out.println("Polagam na sklad vozlisce " + toString(newI - 1, newJ));
                        found = true;
                    }
                }

                if (!found) {
                    stack.pop();
                    System.out.println("Odstranjujem s sklada vozlisce " + curNode);
                }
            }
            System.out.println("-----------------------------------------------------------");
            meja++;
        }
    }
}
