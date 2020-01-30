import java.io.File;
import java.util.Scanner;

public class DN3 {

    public static int stPovezav = 0;
    public static int stVozlisc = 0;
    public static int[][] matrika, compMatrika;
    public static int time = 0, vstopCount = 0, izstopCount = 0, obhodCount = 0, obhodStart = 0, compCount = 0, globina = 0;
    public static int[] vstop, izstop, marked, queue = new int[0], obhod, comp;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("src/stdin.txt"));

        String[] nastavitve = sc.nextLine().split("\\s+");
        String tipGrafa = nastavitve[0];
        String ukaz = nastavitve[1];
        int parameter = 0;
        if (nastavitve.length > 2) parameter = Integer.parseInt(nastavitve[2]);
        stVozlisc = Integer.parseInt(sc.nextLine().split("\\s+")[0]);

        matrika = new int[stVozlisc][stVozlisc];
        String[] vozlisci;
        int vozlisce1, vozlisce2;

        while(sc.hasNextLine()) {
            vozlisci = sc.nextLine().split("\\s+");
            vozlisce1 = Integer.parseInt(vozlisci[0]);
            vozlisce2 = Integer.parseInt(vozlisci[1]);
            matrika[vozlisce1][vozlisce2] = 1;
            stPovezav++;
            if (tipGrafa.equals("undirected")) {
                matrika[vozlisce2][vozlisce1] = 1;
            }
        }
        sc.close();

        switch (ukaz) {
            case "info":
                info(tipGrafa);
                break;
            case "walks":
                walks(parameter);
                break;
            case "dfs":
                marked = new int[matrika.length];
                vstop = new int[matrika.length];
                izstop = new int[matrika.length];
                dfsFull();
                for (int i = 0; i < vstop.length; i++) {
                    System.out.print(vstop[i] + " ");
                }
                System.out.println();
                for (int i = 0; i < izstop.length; i++) {
                    System.out.print(izstop[i] + " ");
                }
                break;
            case "bfs":
                marked = new int[matrika.length];
                obhod = new int[matrika.length];
                bfsFull(false);
                for (int i = 0; i < obhod.length; i++) {
                    System.out.print(obhod[i] + " ");
                }
                break;
            case "sp":
                marked = new int[matrika.length];
                obhod = new int[matrika.length];
                bfsSp(parameter);
                break;
            case "comp":
                if (tipGrafa.equals("undirected")) {
                    marked = new int[matrika.length];
                    obhod = new int[matrika.length];
                    bfsFull(true);
                }
                else {
                    //Inicializacija koncne matrike
                    compMatrika = new int[matrika.length][matrika.length];
                    for (int i = 0; i < matrika.length; i++) {
                        for (int j = 0; j < matrika.length; j++) {
                            compMatrika[i][j] = Integer.MAX_VALUE;
                        }
                    }

                    marked = new int[matrika.length];
                    vstop = new int[matrika.length];
                    izstop = new int[matrika.length];
                    comp = new int[matrika.length];
                    dfsFull();
                    //Nastavim pomoznega na izstop
                    for (int i = 0; i < izstop.length; i++) {
                        comp[i] = izstop[i];
                    }
                    matrika = obrniGraf(matrika);
                    dfsFullComp();
                }
                break;
        }
    }

    public static void bfsSp(int vozlisce) {
        int[] q;
        time++;
        marked[vozlisce] = time;
        int[] distanceArray = new int[matrika.length];
        q = enqueue(vozlisce);

        for (int i = 0; i < matrika.length; i++) {
            distanceArray[i] = -1;
        }

        distanceArray[vozlisce] = 0;

        while (q.length > 0) {
            vozlisce = dequeue();
            q = queue;
            for (int i = 0; i < matrika.length; i++) {
                if (matrika[vozlisce][i] == 1 && marked[i] == 0) {
                    distanceArray[i] = distanceArray[vozlisce] + 1;
                    time++;
                    marked[i] = time;
                    q = enqueue(i);
                }
            }
        }

        for (int i = 0; i < matrika.length; i++) {
            System.out.println(i + " " + distanceArray[i]);
        }
    }

    public static void dfsFullComp() {
        boolean empty = true;
        marked = new int[matrika.length];
        for (int i = comp.length - 1; i >= 0; i--) {
            //Init
            empty = true;
            vstopCount = 0;
            izstopCount = 0;
            vstop = new int[matrika.length];
            izstop = new int[matrika.length];
            for (int j = 0; j < izstop.length; j++) {
                izstop[j] = -1;
            }

            if (marked[comp[i]] == 0) dfsComp(comp[i]);

            for (int j = 0; j < izstop.length; j++) {
                if (izstop[j] != -1) {
                    compMatrika[compCount][j] = izstop[j];
                    empty = false;
                }
            }
            if (!empty) compCount++;
        }
        urediComp();
    }

    public static void urediComp(){
        //Uredi posamezne vrstice
        for (int i = 0; i < matrika.length; i++) {
            compMatrika[i] = bubbleSort(compMatrika[i]);
        }

        //Napolnem celo helper matriko z max value
        int[][] helper = new int[matrika.length][matrika.length];
        for (int i = 0; i < matrika.length; i++) {
            for (int j = 0; j < matrika.length; j++) {
                helper[i][j] = Integer.MAX_VALUE;
            }
        }

        int min = Integer.MAX_VALUE;
        int minLine = 0;
        int count = 0;
        for (int i = 0; i < matrika.length; i++) {
            for (int j = 0; j < matrika.length; j++) {
                if (compMatrika[j][0] < min) {
                    min = compMatrika[j][0];
                    minLine = j;
                }
            }
            for (int j = 0; j < matrika.length; j++) {
                if (compMatrika[minLine][j] < Integer.MAX_VALUE) {
                    helper[count][j] = compMatrika[minLine][j];
                    compMatrika[minLine][j] = Integer.MAX_VALUE;
                }
            }
            count++;
            minLine = 0;
            min = Integer.MAX_VALUE;
        }
        boolean empty = true;
        for (int i = 0; i < matrika.length; i++) {
            for (int j = 0; j < matrika.length; j++) {
                if (helper[i][j] != Integer.MAX_VALUE) {
                    System.out.print(helper[i][j] + " ");
                    empty = false;
                }
            }
            if (!empty) System.out.println();
            empty = true;
        }
    }

    public static void dfsComp(int vozlisce) {
        vstop[vstopCount++] = vozlisce;
        time++;
        marked[vozlisce] = time;
        for (int i = 0; i < matrika.length; i++) {
            if (matrika[vozlisce][i] == 1 && marked[i] == 0) dfsComp(i);
        }
        izstop[izstopCount++] = vozlisce;
    }

    public static void info(String tipGrafa) {
        if (tipGrafa.equals("undirected")) {
            int steviloPovezav = 0;
            for (int i = 0; i < matrika.length; i++) {
                for (int j = 0; j < matrika.length; j++) {
                    if (matrika[i][j] == 1) steviloPovezav++;
                }
            }
            System.out.println(stVozlisc + " " +  steviloPovezav/2);
            int stopnjaVozlisca = 0;
            for (int i = 0; i < matrika.length; i++) {
                for (int j = 0; j < matrika.length; j++) {
                    if (matrika[i][j] == 1) stopnjaVozlisca++;
                }
                System.out.println(i + " " + stopnjaVozlisca);
                stopnjaVozlisca = 0;
            }
        }
        else {
            System.out.println(stVozlisc + " " + stPovezav);
            int[][] table = new int [2][matrika.length];
            for (int i = 0; i < matrika.length; i++) {
                for (int j = 0; j < matrika[i].length; j++) {
                    if (matrika[i][j] == 1) {
                        table[0][i]++;
                        table[1][j]++;
                    }
                }
            }
            for (int i = 0; i < matrika.length; i++) {
                System.out.println(i + " " + table[0][i] + " " + table[1][i]);
            }
        }
    }

    public static void walks(int k) {
        int[][] result = new int[matrika.length][matrika.length], vmesna = matrika;
        int vsota;

        for (int i = 0; i < k - 1; i++) {
            for (int j = 0; j < matrika.length; j++) {
                for (int l = 0; l < matrika.length; l++) {
                    vsota = 0;
                    for (int m = 0; m < matrika.length; m++) {
                        vsota += vmesna[j][m] * matrika[m][l];
                    }
                    result[j][l] = vsota;
                }
            }
            vmesna = new int[matrika.length][matrika.length];
            for (int j = 0; j < matrika.length; j++) {
                for (int l = 0; l < matrika.length; l++) {
                    vmesna[j][l] = result[j][l];
                }
            }
        }

        for (int i = 0; i < matrika.length; i++) {
            for (int j = 0; j < matrika.length; j++) {
                System.out.print(vmesna[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void dfsFull() {
        for (int i = 0; i < matrika.length; i++) {
            for (int j = 0; j < matrika.length; j++) {
                System.out.print(matrika[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < matrika.length; i++) {
            if (marked[i] == 0) dfs(i);
        }
    }

    public static void dfs(int vozlisce) {
        vstop[vstopCount++] = vozlisce;
        time++;
        marked[vozlisce] = time;
        for (int i = 0; i < matrika.length; i++) {
            if (matrika[vozlisce][i] == 1 && marked[i] == 0) dfs(i);
        }
        izstop[izstopCount++] = vozlisce;
    }

    public static void bfsFull(boolean comp) {
        int[] podgraf = new int[obhod.length];
        int count;
        for (int i = 0; i < obhod.length; i++) {
            obhod[i] = -1;
        }
        for (int i = 0; i < matrika.length; i++) {
            count = 0;
            for (int j = 0; j < obhod.length; j++) {
                podgraf[j] = Integer.MAX_VALUE;
            }
            if (marked[i] == 0) {
                bfs(i);
                for (int j = obhodStart; j < obhod.length; j++) {
                    if (obhod[j] != -1 && comp) podgraf[count++] = obhod[j];
                    else {
                        obhodStart = j;
                        break;
                    }
                }
                if (comp) {
                    podgraf = bubbleSort(podgraf);
                    for (int j = 0; j < podgraf.length; j++) {
                        if (podgraf[j] != Integer.MAX_VALUE) System.out.print(podgraf[j] + " ");
                    }
                    System.out.println();
                }
            }
        }
    }

    public static void bfs(int vozlisce) {
        int[] q;
        time++;
        marked[vozlisce] = time;
        q = enqueue(vozlisce);

        while (q.length > 0) {
            vozlisce = dequeue();
            q = queue;
            for (int i = 0; i < matrika.length; i++) {
                if (matrika[vozlisce][i] == 1 && marked[i] == 0) {
                    time++;
                    marked[i] = time;
                    q = enqueue(i);
                }
            }
        }
    }

    public static void bfsFullSp(int iskanElement) {
        for (int i = 0; i < matrika.length; i++) {
            while (queue.length > 0) dequeue();
            marked = new int[matrika.length];
            time = 0;
            globina = 0;
            obhodCount = 0;
            System.out.println("BFS SEARCH NA: " + i);
            bfsSearch(i, iskanElement);
        }
    }

    public static void bfsSearch(int vozlisce, int iskanElement) {
        boolean found = false;
        int zacetnoVozlisce = vozlisce;
        int[] q;
        time++;
        marked[vozlisce] = time;
        q = enqueue(vozlisce);

        while (q.length > 0) {
            vozlisce = dequeue();
            if (vozlisce == iskanElement) System.out.println(zacetnoVozlisce + " " + globina);
            System.out.println("Vozlisce " + vozlisce);
            q = queue;
            for (int i = 0; i < matrika.length; i++) {
                if (matrika[vozlisce][i] == 1 && marked[i] == 0) {
                    time++;
                    marked[i] = time;
                    q = enqueue(i);
                    globina++;
                }
            }
        }
    }

    public static int[] bubbleSort(int[] array) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < array.length - j; i++) {
                if (array[i] > array[i + 1]) {
                    tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
        return array;
    }

    public static int[] enqueue(int vozlisce) {
        obhod[obhodCount++] = vozlisce;
        int[] pomozniQueue = new int[queue.length + 1];
        for (int i = 0; i < queue.length; i++) {
            pomozniQueue[i] = queue[i];
        }
        pomozniQueue[pomozniQueue.length - 1] = vozlisce;
        queue = new int[pomozniQueue.length];
        for (int i = 0; i < pomozniQueue.length; i++) {
            queue[i] = pomozniQueue[i];
        }
        return pomozniQueue;
    }

    public static int dequeue() {
        if (queue.length == 0) return -1;
        int[] pomozniQueue = new int[queue.length - 1];
        int prvi;
        for (int i = 1; i < queue.length; i++) {
            pomozniQueue[i - 1] = queue[i];
        }
        prvi = queue[0];
        queue = new int[pomozniQueue.length];
        for (int i = 0; i < pomozniQueue.length; i++) {
            queue[i] = pomozniQueue[i];
        }
        return prvi;
    }

    public static int[][] obrniGraf(int[][] graf) {
        int[][] pomozni = new int[graf.length][graf.length];
        for (int i = 0; i < graf.length; i++) {
            for (int j = 0; j < graf.length; j++) {
                pomozni[i][j] = graf[j][i];
                pomozni[j][i] = graf[i][j];
            }
        }
        return pomozni;
    }
}
